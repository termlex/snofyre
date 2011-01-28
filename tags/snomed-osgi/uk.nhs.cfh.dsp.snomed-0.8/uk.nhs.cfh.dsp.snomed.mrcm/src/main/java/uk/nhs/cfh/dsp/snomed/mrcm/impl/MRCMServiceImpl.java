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
import org.springframework.core.io.Resource;
import uk.nhs.cfh.dsp.snomed.mrcm.MRCMGenerator;
import uk.nhs.cfh.dsp.snomed.mrcm.MRCMService;
import uk.nhs.cfh.dsp.snomed.mrcm.dao.MRCMDao;
import uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.mrcm.MRCMService}.
 *
 * @scr.component immediate=“true”
 * @scr.service
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 16, 2010 at 4:05:43 PM
 */
public class MRCMServiceImpl implements MRCMService {

    /** The mrcm dao. */
    private MRCMDao mrcmDao;
    
    /** The mrcm generator. */
    private MRCMGenerator mrcmGenerator;
    
    /** The create database. */
    private boolean createDatabase = false;
    
    /** The input file location. */
    private String inputFileLocation;
    
    /** The default input file. */
    private Resource defaultInputFile;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(MRCMServiceImpl.class);
    
    /** The concept type map. */
    private Map<ConceptType, String> conceptTypeMap = new HashMap<ConceptType, String>(20);

    /**
     * Instantiates a new mRCM service impl.
     *
     * @param mrcmDao the mrcm dao
     */
    public MRCMServiceImpl(MRCMDao mrcmDao) {
        this.mrcmDao = mrcmDao;
    }

    /**
     * Instantiates a new mRCM service impl.
     */
    public MRCMServiceImpl() {
    }

    /**
     * Initalise lookup map.
     */
    public synchronized void initaliseLookupMap(){

        for(ConceptType t : ConceptType.values())
        {
            if (ConceptType.UNKNOWN != t) {
                conceptTypeMap.put(t, t.getID());
            }
        }
    }

    /**
     * this method checks if the mrcm repo exits by verifying there are
     * entries in the repository. If there are none, then it generates the mrcm repo
     * by first using the file specified in the mrcm-db.properties file. It defaults to
     * the bundled MRCM file if the path can not be resolved or the file is not found.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public synchronized void checkAndIntialiseDatabase() throws IOException {
        // create concept type look up map
        initaliseLookupMap();
        if(createDatabase)
        {
            // check a file exists in input path of mrcm generator
            File inputFile = new File(inputFileLocation);
            if(! inputFile.exists() || ! inputFile.canRead() || ! inputFile.isFile())
            {
                logger.warn("Input file specified does not exist or can be be read.\n" +
                        "Using the bundled MRCM file to populate the repo");
                inputFile = defaultInputFile.getFile();
            }

            logger.info("Starting population of the MRCM Repo");
            mrcmGenerator.generateMRCMTable(inputFile);
            logger.info("Finished populating the MRCM Repo");
        }
    }

    /**
     * Sets the mRCM dao.
     *
     * @param mrcmDao the new mRCM dao
     */
    public synchronized void setMRCMDao(MRCMDao mrcmDao) {
        this.mrcmDao = mrcmDao;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.MRCMService#saveConstraint(uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint)
     */
    public synchronized void saveConstraint(MRCMConstraint constraint){
        mrcmDao.saveConstraint(constraint);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.MRCMService#deleteConstraint(uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint)
     */
    public synchronized void deleteConstraint(MRCMConstraint constraint){
        mrcmDao.deleteConstraint(constraint);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.MRCMService#deleteAllConstraints()
     */
    public synchronized void deleteAllConstraints(){
        mrcmDao.deleteAllConstraints();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.MRCMService#findAllConstraints()
     */
    public synchronized List<MRCMConstraint> findAllConstraints(){
        return mrcmDao.findAllConstraints();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.MRCMService#findConstraint(java.lang.String, java.lang.String)
     */
    public synchronized List<MRCMConstraint> findConstraint(String sourceId, String attributeId){
        return mrcmDao.findConstraint(sourceId, attributeId);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.MRCMService#getSanctionedValues(java.lang.String, java.lang.String)
     */
    public synchronized Set<String> getSanctionedValues(String sourceId, String attributeId){
        return mrcmDao.getSanctionedValues(sourceId, attributeId);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.MRCMService#getSanctionedValues(ConceptType, String)
     */
    public synchronized Set<String> getSanctionedValues(ConceptType type, String attributeId){
        return mrcmDao.getSanctionedValues(conceptTypeMap.get(type), attributeId);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.MRCMService#getSanctionedAttributes(java.lang.String)
     */
    public synchronized Set<String> getSanctionedAttributes(String conceptId){
        return mrcmDao.getSanctionedAttributesOnConcept(conceptId);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.MRCMService#getSanctionedValues(java.lang.String)
     */
    public synchronized Set<String> getSanctionedValues(String conceptId){
        return mrcmDao.getSanctionedValuesOnConcept(conceptId);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.MRCMService#getSanctionedValues(ConceptType)
     */
    public synchronized Set<String> getSanctionedValues(ConceptType type){
        if (conceptTypeMap.containsKey(type))
        {
            return mrcmDao.getSanctionedValuesOnConcept(conceptTypeMap.get(type));
        }
        else {
            throw new IllegalArgumentException("Concept type passed not recognised : "+type);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.MRCMService#getSanctionedAttributes(ConceptType)
     */
    public synchronized Set<String> getSanctionedAttributes(ConceptType type){
        if (conceptTypeMap.containsKey(type))
        {
            Set<String> attributes = mrcmDao.getSanctionedAttributesOnConcept(conceptTypeMap.get(type));
            if(ConceptType.DISEASE == type)
            {
                // combine attributes of both disease and finding
                attributes.addAll(mrcmDao.getSanctionedAttributesOnConcept(conceptTypeMap.get(ConceptType.FINDING)));
            }
            else if(ConceptType.FINDING_WEC == type || ConceptType.PROCEDURE_WEC == type)
            {
                attributes.addAll(mrcmDao.getSanctionedAttributesOnConcept(conceptTypeMap.get(ConceptType.SITUATION_WEC)));
            }
            else if (ConceptType.SURGICAL_PROCEDURE == type)
            {
                attributes.addAll(mrcmDao.getSanctionedAttributesOnConcept(conceptTypeMap.get(ConceptType.PROCEDURE)));
            }

            return attributes;
        }
        else {
            throw new IllegalArgumentException("Concept type passed not recognised : "+type);
        }
    }

    /**
 * Sets the mrcm generator.
 *
 * @param mrcmGenerator the new mrcm generator
 */
public synchronized void setMrcmGenerator(MRCMGenerator mrcmGenerator) {
        this.mrcmGenerator = mrcmGenerator;
    }

    /**
     * Sets the creates the database.
     *
     * @param createDatabase the new creates the database
     */
    public synchronized void setCreateDatabase(boolean createDatabase) {
        this.createDatabase = createDatabase;
    }

    /**
     * Sets the input file location.
     *
     * @param inputFileLocation the new input file location
     */
    public synchronized void setInputFileLocation(String inputFileLocation) {
        this.inputFileLocation = inputFileLocation;
    }

    /**
     * Sets the default input file.
     *
     * @param defaultInputFile the new default input file
     */
    public synchronized void setDefaultInputFile(Resource defaultInputFile) {
        this.defaultInputFile = defaultInputFile;
    }
}
