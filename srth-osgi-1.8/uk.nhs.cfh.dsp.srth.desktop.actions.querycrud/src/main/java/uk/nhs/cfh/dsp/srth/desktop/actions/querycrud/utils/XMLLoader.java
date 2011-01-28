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
package uk.nhs.cfh.dsp.srth.desktop.actions.querycrud.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;

// TODO: Auto-generated Javadoc
/**
 * A class that generates a {@link org.jdom.Document} from a given {@link java.io.File}
 * or other resource.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 28, 2009 at 1:14:37 PM
 * <br> Modified and cleaned up on Saturday; November 28, 2009 at 1:22 PM.
 */
public class XMLLoader {

	/** The parser. */
	private SAXBuilder parser ;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(XMLLoader.class);
    private static final String ERR_MESSAGE = "Nested exception message is : ";


    /**
     * Instantiates a new xML loader.
     */
    public XMLLoader(){
        parser = new SAXBuilder();
	}

	/**
	 * Gets the xML doc.
	 * 
	 * @param localFile the local file
	 * 
	 * @return the xML doc
	 */
	public synchronized Document getXMLDoc(File localFile){

        logger.info("localFile.getAbsolutePath() = " + localFile.getAbsolutePath());
		Document xDoc = new Document();
		try
		{
			xDoc = parser.build(localFile);

		} catch (JDOMException e) {
			logger.warn("Error parsing xml file : " +localFile.getAbsolutePath()+
					"Check the xml document is well formed. " +
                    ERR_MESSAGE+e.getMessage());
		} catch (IOException e) {
            logger.warn("Check file exists and is readable. " +
                    ERR_MESSAGE+e.getMessage());
		}

		return xDoc;
	}

	/**
	 * Gets the xML doc.
	 * 
	 * @param xmlString the xml string
	 * 
	 * @return the xML doc
	 */
	public synchronized Document getXMLDoc(String xmlString){

		Document xDoc = new Document();
		try
		{
			xDoc = parser.build(new StringReader(xmlString));

		} catch (JDOMException e) {
			logger.warn("Error parsing xml file : " +
					ERR_MESSAGE+e.getMessage());
		} catch (IOException e) {
            logger.warn("Check file exists and is readable. " +
                    ERR_MESSAGE+e.getMessage());
		}

		return xDoc;
	}

   /**
    * Gets the xML doc.
    * 
    * @param url the url
    * 
    * @return the xML doc
    */
   public synchronized Document getXMLDoc(URL url){

		Document xDoc = new Document();
		try
		{
			xDoc = parser.build(url);

		} catch (JDOMException e) {
			logger.warn("Check the xml document is well formed. " +
                    ERR_MESSAGE+e.getMessage());
		} catch (IOException e) {
            logger.warn("Could not resolve the url passed : "+url+"" +
                    ERR_MESSAGE+e.getMessage());
		}

		return xDoc;
	}

    /**
     * Gets the xML doc.
     * 
     * @param inputStream the input stream
     * 
     * @return the xML doc
     */
    public synchronized Document getXMLDoc(InputStream inputStream) {

        Document xDoc = new Document();
        try
		{
			xDoc = parser.build(inputStream);

		} catch (JDOMException e) {
			logger.warn("Check the xml document is well formed. " +
                    ERR_MESSAGE+e.getMessage());
		} catch (IOException e) {
            logger.warn("Errors reading input stream." +
                    ERR_MESSAGE+e.getMessage());
		}

		return xDoc;
    }
}
