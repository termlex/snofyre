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
package uk.nhs.cfh.dsp.snomed.mrcm.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.mrcm.MRCMGenerator;
import uk.nhs.cfh.dsp.snomed.mrcm.dao.MRCMDao;
import uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint;
import uk.nhs.cfh.dsp.snomed.mrcm.om.impl.MRCMConstraintImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.mrcm.MRCMGenerator}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 16, 2010 at 10:57:18 AM
 */
public class MRCMGeneratorImpl implements MRCMGenerator {

    /** The logger. */
    private static Log logger = LogFactory.getLog(MRCMGeneratorImpl.class);
    
    /** The mrcm dao. */
    private MRCMDao mrcmDao;

    /**
     * Instantiates a new mRCM generator impl.
     */
    public MRCMGeneratorImpl() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.MRCMGenerator#generateMRCMTable(java.io.File)
     */
    public synchronized void generateMRCMTable(File file){

        try
        {
            Scanner scanner = new Scanner(file);
            // delete all entries in exising mrcm repo
            mrcmDao.deleteAllConstraints();
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine().trim();
                // remove all cg operators in line
                line = line.replaceAll("<<", "");
                line = line.replaceAll("=", "");
                String[] parts = line.split("\\t");
                if(parts.length == 6)
                {
                    MRCMConstraint constraint = new MRCMConstraintImpl();
                    constraint.setSourceId(parts[0]);
                    constraint.setSourceName(parts[3]);
                    constraint.setAttributeId(parts[1]);
                    constraint.setValueId(parts[2]);
                    constraint.setValueName(parts[5]);

                    // process attribute name column to extract cardinality values
                    String attributeName = parts[4];
                    int minCardinality =0;
                    int maxCardinality = -1;
                    int colonIndex = attributeName.indexOf(':');
                    if (colonIndex > 0)
                    {
                        String minString = attributeName.substring(colonIndex -1, colonIndex);
                        String maxString = attributeName.substring(colonIndex+1, colonIndex+2);
                        // replace * in maxString with -1 if present, to indicate unbounded cardinality
                        if(maxString.indexOf('*') >-1){
                            maxString = "-1";
                        }
                        
                        try
                        {
                            minCardinality = Integer.parseInt(minString);
                            maxCardinality = Integer.parseInt(maxString);
                        }
                        catch (NumberFormatException e) {
                            logger.warn("Error generating cardinality from line.\n" +
                                    "Nested exception is : " + e.fillInStackTrace()); 
                        }
                    }

                    constraint.setMinCardinality(minCardinality);
                    constraint.setMaxCardinality(maxCardinality);
                    // now strip out All cardinality and related info from attributeName
                    attributeName = attributeName.substring(colonIndex+8).trim();
                    constraint.setAttributeName(attributeName);
                    mrcmDao.saveConstraint(constraint);
                }
            }
        }
        catch (FileNotFoundException e) {
            logger.warn("Error reading from file. Check file exists and is readable.\n" +
                    " Nested exception is : " + e.fillInStackTrace());
        }

    }

    /**
     * Sets the mrcm dao.
     *
     * @param mrcmDao the new mrcm dao
     */
    public synchronized void setMrcmDao(MRCMDao mrcmDao) {
        this.mrcmDao = mrcmDao;
    }
}
