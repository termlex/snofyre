/**
 * Crown Copyright (C) 2008 - 2011
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.nhs.cfh.dsp.srth.distribution;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that parses the URL of the installation folder and replaces spaces with legal characters.
 *
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Nov 07, 2010 at 10:25:11 PM
 */
public class AppPathFormatter {

    private static Logger logger = Logger.getLogger(AppPathFormatter.class.getName());
    private Map<String, String> varMap = new HashMap<String, String>();

    public AppPathFormatter() {
        try
        {
            logger.addHandler(new FileHandler(System.getProperty("java.io.tmpdir")+
                    System.getProperty("file.separator")+"configurator.log", true));
        }
        catch (IOException e) {
            logger.warning("Nested exception is : " + e.fillInStackTrace().getMessage());
        }
    }

    public String replaceIllegalCharacters(String urlString){

        String s = new StringBuffer(urlString).toString();
        logger.log(Level.FINEST, "s = " + s);
        s = s.replaceAll(" ", "%20");
        logger.log(Level.FINEST, "s = " + s);
        s = s.replace('\\', '/');
        logger.log(Level.FINEST, "s = " + s);
        s = s.replaceAll("/%20", "%20");
        logger.log(Level.FINEST, "s = " + s);

        return s;
    }

    public void processFile(File file){
        try
        {
            logger.info("Processing file : " + file.getAbsolutePath());
            List<String> list = new ArrayList<String>();
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if(line.indexOf("file:") > -1)
                {
                    line = replaceIllegalCharacters(line);
                }

                // substitute variables if present in line
                for(String key : varMap.keySet())
                {
                    if(line.indexOf(key) > -1)
                    {
                        line = line.replaceAll(key, varMap.get(key));
                    }
                }

                // save line for later use
                list.add(line);
                logger.log(Level.FINEST, "Processed line = " + line);
            }

            FileWriter fw = new FileWriter(file);
            fw.flush();
            for(String l : list)
            {
                fw.write(l+"\n");
                logger.log(Level.FINEST,"Writing line to file = " + l);
            }
            // close file writer
            fw.flush();
            fw.close();
            logger.info("Finished processing file : " + file.getAbsolutePath());

        }
        catch (FileNotFoundException e) {
            logger.warning("Nested exception is : " + e.fillInStackTrace().getMessage());
        }
        catch (IOException e) {
            logger.warning("Nested exception is : " + e.fillInStackTrace().getMessage());
        }
    }

    public void setVarMap(Map<String, String> varMap) {
        this.varMap = varMap;
    }
}
