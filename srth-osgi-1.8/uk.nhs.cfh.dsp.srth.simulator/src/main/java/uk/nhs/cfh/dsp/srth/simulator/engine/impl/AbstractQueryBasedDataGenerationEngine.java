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
/**
 *
 */
package uk.nhs.cfh.dsp.srth.simulator.engine.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.srth.demographics.PatientDAO;
import uk.nhs.cfh.dsp.srth.demographics.PatientUtilsService;
import uk.nhs.cfh.dsp.srth.demographics.person.Patient;
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO;
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO;
import uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject;
import uk.nhs.cfh.dsp.srth.expression.repository.om.impl.ExpressionMappingObjectImpl;
import uk.nhs.cfh.dsp.srth.expression.repository.om.impl.ExpressionSubsumptionRelationshipImpl;
import uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory;
import uk.nhs.cfh.dsp.srth.information.model.impl.EHRFactory;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.BoundClinicalEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.EHR;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEntry;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEventEntry;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.srth.query.service.QueryServiceListener;
import uk.nhs.cfh.dsp.srth.simulator.engine.DataGenerationEngine;
import uk.nhs.cfh.dsp.srth.simulator.utils.RandomSubtypeGenerator;

import java.util.*;


/**
 * An abstract implementation of a {@link DataGenerationEngine} that uses a query
 * to generate simulated data. The default parameters used by this implementation
 * are
 * <br> Max patient number : 100
 * <br> Min patient age : 40 yrs
 * <br> {@link DataGenerationStrategy} : ADD_IF_NOT_EXISTS
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jun 10, 2009 at 4:24:41 PM
 * <br>
 */
public abstract class AbstractQueryBasedDataGenerationEngine implements	DataGenerationEngine, QueryServiceListener{

    /** The data generation source. */
    private DataGenerationSource dataGenerationSource;

    /** The logger. */
    private static Log logger = LogFactory.getLog(AbstractQueryBasedDataGenerationEngine.class);

    /** The data generation strategy. */
    private DataGenerationStrategy dataGenerationStrategy;

    /** The min patient age in years. */
    private int minPatientAgeInYears = 40;

    /** The max patient number. */
    private long maxPatientNumber = 100;

    /** The randomise numerical values. */
    private boolean randomiseNumericalValues = true;

    /** The include excluded terms. */
    private boolean includeExcludedTerms = true;
    private boolean trackProgress = true;
    private boolean refineQualifiers = false;
    private boolean includePrecoordinatedData = true;

    /** The active query. */
    private QueryStatement activeQuery;
    private ClinicalEntryFactory clinicalEntryFactory;
    private TerminologyConceptDAO terminologyConceptDAO;
    private ExpressionMappingObjectDAO expressionMappingObjectDAO;
    private CloseToUserExpression currentParentExpression;
    private ExpressionSubsumptionRelationshipDAO expressionSubsumptionRelationshipDAO;

    /** The used ids. */
    private Set<Long> usedIds = new HashSet<Long>();
    private PatientUtilsService patientUtilsService;
    private EHRFactory ehrFactory;
    private RandomSubtypeGenerator randomSubtypeGenerator;
    private PatientDAO patientDAO;
    private boolean stopEngine = false;

    /**
     * Instantiates a new abstract query based data generation engine.
     *
     */
    protected AbstractQueryBasedDataGenerationEngine(TerminologyConceptDAO terminologyConceptDAO,
                                                     ClinicalEntryFactory clinicalEntryFactory, PatientDAO patientDAO, EHRFactory ehrFactory, RandomSubtypeGenerator randomSubtypeGenerator, PatientUtilsService patientUtilsService) {
        this(terminologyConceptDAO, clinicalEntryFactory, DataGenerationSource.QUERY,
                DataGenerationStrategy.ADD_IF_NOT_EXISTS);
        this.patientDAO = patientDAO;
        this.ehrFactory = ehrFactory;
        this.randomSubtypeGenerator = randomSubtypeGenerator;
        this.patientUtilsService = patientUtilsService;
    }

    /**
     * Instantiates a new abstract query based data generation engine.
     *
     * @param clinicalEntryFactory
     * @param dataGenerationSource the data generation source
     * @param dataGenerationStrategy the data generation strategy
     */
    protected AbstractQueryBasedDataGenerationEngine(TerminologyConceptDAO terminologyConceptDAO,
                                                     ClinicalEntryFactory clinicalEntryFactory,
                                                     DataGenerationSource dataGenerationSource,
                                                     DataGenerationStrategy dataGenerationStrategy) {
        this.terminologyConceptDAO = terminologyConceptDAO;
        this.clinicalEntryFactory = clinicalEntryFactory;
        this.dataGenerationSource = dataGenerationSource;
        this.dataGenerationStrategy = dataGenerationStrategy;
    }

    protected AbstractQueryBasedDataGenerationEngine(QueryService queryService) {
        this.dataGenerationSource = DataGenerationSource.QUERY;
        this.dataGenerationStrategy = DataGenerationStrategy.ADD_IF_NOT_EXISTS;
        // register self with queryService
        if(queryService != null)
        {
            queryService.addListener(this);
        }
    }

    /**
     * Generate data for query.
     *
     * @param query the query
     */
    public abstract void generateDataForQuery(QueryStatement query);

    /**
     * Gets the data generation source.
     *
     * @return the data generation source
     */
    public DataGenerationSource getDataGenerationSource() {
        return dataGenerationSource;
    }

    /**
     * Sets the data generation source.
     *
     * @param dataGenerationSource the new data generation source
     */
    public void setDataGenerationSource(DataGenerationSource dataGenerationSource) {
        this.dataGenerationSource = dataGenerationSource;
    }

    /**
     * Adds the entry to ehr.
     *
     * @param subtypeExpression the expression used to create the {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity}
     * @param ehr the ehr
     * @param entryTime the entry time
     *
     */
    protected void addEntryToEHR(CloseToUserExpression supertypeExpression, CloseToUserExpression subtypeExpression, EHR ehr, Calendar entryTime) {

        if (logger.isDebugEnabled()) {
            logger.debug("Adding entry to EHR");
        }
        // Create entry object from expression and add to ehr based on entry type
        ConceptType entryType = subtypeExpression.getSingletonFocusConceptType();

        if(ConceptType.DISEASE == entryType || ConceptType.FINDING == entryType ||
                ConceptType.FINDING_WEC == entryType || ConceptType.EVENT == entryType)
        {
            // create clinical finding entity
            BoundClinicalEntry entry = clinicalEntryFactory.getClinicalFindingEntry(ehr.getPatientId(), subtypeExpression, entryTime);
            BoundClinicalEntity entity = clinicalEntryFactory.getEntityFactory().getClinicalFindingEntity(supertypeExpression);
            // add to expression repository
            verifyIdsAndAddToExpressionRepo(entity, entry);
            ehr.addFinding(entry);
        }
        else if(ConceptType.PHARMACEUTICAL_OR_BIOLOGICAL_PRODUCT == entryType ||
                ConceptType.DRUG_DEVICE_COMBO_PRODUCT == entryType)
        {
            // create a medication entity and add to EHR
            BoundClinicalEventEntry entry = clinicalEntryFactory.getMedicationEntry(ehr.getPatientId(), subtypeExpression, entryTime, 0);
            BoundClinicalEntity entity = clinicalEntryFactory.getEntityFactory().getMedicationEntity(supertypeExpression, 0);
            // add to expression repository
            verifyIdsAndAddToExpressionRepo(entity, entry);
            ehr.addMedication(entry);
        }
        else if (ConceptType.PROCEDURE == entryType || ConceptType.SURGICAL_PROCEDURE == entryType ||
                ConceptType.PROCEDURE_WEC == entryType)
        {
            // create procedure entity and add to ehr
            BoundClinicalEventEntry entry = clinicalEntryFactory.getInterventionEntry(ehr.getPatientId(), subtypeExpression, entryTime);
            BoundClinicalEntity entity = clinicalEntryFactory.getEntityFactory().getInterventionEntity(supertypeExpression);
            // add to expression repository
            verifyIdsAndAddToExpressionRepo(entity, entry);
            ehr.addIntervention(entry);
        }
        else if (ConceptType.EVALUATION_PROCEDURE == entryType )
        {
            // create procedure entity and add to ehr
            BoundClinicalEventEntry entry = clinicalEntryFactory.getInvestigationEntry(ehr.getPatientId(), subtypeExpression, entryTime);
            BoundClinicalEntity entity = clinicalEntryFactory.getEntityFactory().getInvestigationEntity(supertypeExpression);
            // add to expression repository
            verifyIdsAndAddToExpressionRepo(entity, entry);
            ehr.addInvestigation(entry);
        }
        else
        {
            logger.warn("Unknown entity type "+entryType+" \n" +
                    "Expression passed : "+subtypeExpression.getCompositionalGrammarForm());
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Added entry to EHR");
        }
    }

    /**
     * Adds the observation entry to ehr.
     *
     * @param expression
     * @param ehr the ehr
     * @param entryTime the entry time
     * @param value the value
     */
    protected void addObservationEntryToEHR(CloseToUserExpression expression, EHR ehr, Calendar entryTime, double value){

        // add feature entity to ehr
        BoundClinicalEntry entry = clinicalEntryFactory.getClinicalFeatureEntry(ehr.getPatientId(), expression, entryTime, value);
        verifyNormalFormsAndSetIds(entry);
        ehr.addFeature(entry);
    }

    protected void verifyIdsAndAddToExpressionRepo(BoundClinicalEntity entity, BoundClinicalEntry entry){
        ExpressionMappingObject subtypeEmo = verifyNormalFormsAndSetIds(entry);
        ExpressionMappingObject supertypeEmo = verifyNormalFormsAndSetIds(entity.getExpression());

        // add emos to tc table using the expressionSubsumptionRelationshipDAO
        expressionSubsumptionRelationshipDAO.saveRelationship(
                new ExpressionSubsumptionRelationshipImpl(supertypeEmo.getNormalFormExpressionUuid(), subtypeEmo.getNormalFormExpressionUuid()));
        if(logger.isDebugEnabled()){
            logger.debug("Saved tc relationship in TC table");
        }
    }

    protected ExpressionMappingObject verifyNormalFormsAndSetIds(BoundClinicalEntry entry){

        ClinicalEntity entity = entry.getEntity();
        if(entity instanceof BoundClinicalEntity)
        {
           return verifyNormalFormsAndSetIds(((BoundClinicalEntity) entity).getExpression());
        }
        else
        {
            String msg = "Entry passed does not have a BoundClinicalEntity. Ignoring entry passed";
            logger.warn(msg);
            throw new IllegalArgumentException(msg);
        }
    }

    protected ExpressionMappingObject verifyNormalFormsAndSetIds(ClinicalExpression containedExpression){

        if (logger.isDebugEnabled()) {
            logger.debug("Verifying normal forms and setting ids");
        }
        String nfeString;
        String situationNFEString;
        UUID nfeUuid;
        UUID situationNFEUuid;

        /*
        get normal form for entry and verify if there are matches in the Unique expressions table
         */
        String normalForm = containedExpression.getCompositionalGrammarNormalForm();
        ExpressionMappingObject expressionMappingObject = expressionMappingObjectDAO.findUsingNFECompositionalGrammarForm(normalForm);
        if(expressionMappingObject != null)
        {
            // replace values of situation and normal form and the corresponding ids in this entry and linked expression
            nfeString = expressionMappingObject.getNormalFormCGForm();
            situationNFEString = expressionMappingObject.getSituationNormalFormCGForm();
            nfeUuid = expressionMappingObject.getNormalFormExpressionUuid();
            situationNFEUuid = expressionMappingObject.getSituationNormalFormExpressionUuid();
            
            // replace values in contained expression
            containedExpression.setCompositionalGrammarNormalForm(nfeString);
            containedExpression.setCompositionalSituationNormalForm(situationNFEString);
            containedExpression.setNormalFormUuid(nfeUuid);
            containedExpression.setSituationNormalFormUuid(situationNFEUuid);
        }
        else
        {
            // populate fields using contained expression
            nfeString = containedExpression.getCompositionalGrammarNormalForm();
            situationNFEString = containedExpression.getCompositionalSituationNormalForm();
            nfeUuid = containedExpression.getNormalFormUuid();
            situationNFEUuid = containedExpression.getSituationNormalFormUuid();
        }

        // add a new expressionMappingObject with current values and add values for close to user form
        ExpressionMappingObject newExpressionMappingObject = new ExpressionMappingObjectImpl();
        newExpressionMappingObject.setCloseToUserCGForm(containedExpression.getCompositionalGrammarForm());
        newExpressionMappingObject.setCloseToUserExpressionUuid(containedExpression.getUuid());
        newExpressionMappingObject.setNormalFormCGForm(nfeString);
        newExpressionMappingObject.setSituationNormalFormCGForm(situationNFEString);
        newExpressionMappingObject.setNormalFormExpressionUuid(nfeUuid);
        newExpressionMappingObject.setSituationNormalFormExpressionUuid(situationNFEUuid);

        // persist newExpressionMappingObject
        expressionMappingObjectDAO.save(newExpressionMappingObject);

        if (logger.isDebugEnabled()) {
            logger.debug("Finished verifying normal forms and setting ids");
            logger.debug("Added entry in unique expressions table");
        }

        return newExpressionMappingObject;
    }

    /**
     * Gets the data generation strategy.
     *
     * @return the data generation strategy
     */
    public DataGenerationStrategy getDataGenerationStrategy() {
        return dataGenerationStrategy;
    }

    /**
     * Sets the data generation strategy.
     *
     * @param dataGenerationStrategy the new data generation strategy
     */
    public void setDataGenerationStrategy(
            DataGenerationStrategy dataGenerationStrategy) {
        this.dataGenerationStrategy = dataGenerationStrategy;
    }

    /**
     * Gets the min patient age in years.
     *
     * @return the min patient age in years
     */
    public int getMinPatientAgeInYears() {
        return minPatientAgeInYears;
    }

    /**
     * Sets the min patient age in years.
     *
     * @param minPatientAgeInYears the new min patient age in years
     */
    public void setMinPatientAgeInYears(int minPatientAgeInYears) {
        this.minPatientAgeInYears = minPatientAgeInYears;
    }

    /**
     * Gets the max patient number.
     *
     * @return the max patient number
     */
    public long getMaxPatientNumber() {
        return maxPatientNumber;
    }

    /**
     * Sets the max patient number.
     *
     * @param maxPatientNumber the new max patient number
     */
    public void setMaxPatientNumber(long maxPatientNumber) {
        this.maxPatientNumber = maxPatientNumber;
    }

    /**
     * Checks if is randomise numerical values.
     *
     * @return true, if is randomise numerical values
     */
    public boolean isRandomiseNumericalValues() {
        return randomiseNumericalValues;
    }

    /**
     * Sets the randomise numerical values.
     *
     * @param randomiseNumericalValues the new randomise numerical values
     */
    public void setRandomiseNumericalValues(boolean randomiseNumericalValues) {
        this.randomiseNumericalValues = randomiseNumericalValues;
    }

    /**
     * Checks if is include excluded terms.
     *
     * @return true, if is include excluded terms
     */
    public boolean isIncludeExcludedTerms() {
        return includeExcludedTerms;
    }

    /**
     * Sets the include excluded terms.
     *
     * @param includeExcludedTerms the new include excluded terms
     */
    public void setIncludeExcludedTerms(boolean includeExcludedTerms) {
        this.includeExcludedTerms = includeExcludedTerms;
    }

    public boolean isRefineQualifiers() {
        return refineQualifiers;
    }

    public void setRefineQualifiers(boolean refineQualifiers) {
        this.refineQualifiers = refineQualifiers;
        if(randomSubtypeGenerator != null)
        {
            randomSubtypeGenerator.setUseNormalFormForRendering(refineQualifiers);
        }
    }

    public boolean isIncludePrecoordinatedData() {
        return includePrecoordinatedData;
    }

    public void setIncludePrecoordinatedData(boolean includePrecoordinatedData) {
        this.includePrecoordinatedData = includePrecoordinatedData;
    }

    /**
     * Gets the active query.
     *
     * @return the active query
     */
    public synchronized QueryStatement getActiveQuery() {
        return activeQuery;
    }

    /**
     * Sets the active query.
     *
     * @param activeQuery the new active query
     */
    public synchronized void setActiveQuery(QueryStatement activeQuery) {
        this.activeQuery = activeQuery;
    }

    public synchronized void setClinicalEntryFactory(ClinicalEntryFactory clinicalEntryFactory) {
        this.clinicalEntryFactory = clinicalEntryFactory;
    }

    public synchronized void setTerminologyConceptDAO(TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologyConceptDAO = terminologyConceptDAO;
    }

    public synchronized void setExpressionMappingObjectDAO(ExpressionMappingObjectDAO expressionMappingObjectDAO) {
        this.expressionMappingObjectDAO = expressionMappingObjectDAO;
    }

    /**
     * Checks if is display progress.
     *
     * @return true, if is display progress
     */
    public boolean isTrackProgress() {
        return trackProgress;
    }

    /**
     * Sets the display progress.
     *
     * @param trackProgress the new display progress
     */
    public void setTrackProgress(boolean trackProgress) {
        this.trackProgress = trackProgress;
    }

    public void queryChanged(QueryService service, QueryStatement query, Object source) {
        if(query != null)
        {
            this.activeQuery = query;
        }
    }

    protected CloseToUserExpression getCurrentParentExpression() {
        return currentParentExpression;
    }

    protected void setCurrentParentExpression(CloseToUserExpression currentParentExpression) {
        this.currentParentExpression = currentParentExpression;
    }

//    private long getRandomPositiveLongLessThan(long upperBound){
//
//        if(upperBound == 1)
//        {
//            return 1;
//        }
//        else if (upperBound > 1)
//        {
//            int ran = 0;
//            // get random concept
//            while (ran <= 0)
//            {
//                Random random = new Random();
//                ran = random.nextInt((int) upperBound);
//                if (logger.isDebugEnabled()) {
//                    logger.debug("ran = " + ran);
//                }
//            }
//
//            return ran;
//        } else {
//            throw new IllegalArgumentException("Upper bound passed can not be 0 or less than 0. Upper bound : "+upperBound);
//        }
//    }

    private long getRandomPositiveLonginRange(int min, int max){

        Random random = new Random();
        int ran=   random.nextInt(max - min + 1) + min;
        if (logger.isDebugEnabled()) {
            logger.debug("ran = " + ran);
        }

        return (long) ran;
    }
    /**
     * Gets the next random patient id.
     *
     * @return the next random patient id
     */
    private long getNextRandomPatientId(){

        long totalPatients = patientDAO.getTotalPatientCountInDatabase();
        if (logger.isDebugEnabled()) {
            logger.debug("totalPatients = " + totalPatients);
        }
        // check that we haven't used up all available ids
        if(totalPatients == 0)
        {
            return patientDAO.getNextPatientId();
        }
        else if (totalPatients > 0 && usedIds.size() < totalPatients)
        {
//            long randomId = getRandomPositiveLongLessThan(totalPatients);
            long randomId = getRandomPositiveLonginRange(1, (int)totalPatients);
            if (logger.isDebugEnabled()) {
                logger.debug("randomId = " + randomId);
            }
            // check if randomId has already been used in this iteration
            while (usedIds.contains(randomId))
            {
//                randomId = getRandomPositiveLongLessThan(totalPatients);
                randomId = getRandomPositiveLonginRange(1, (int)totalPatients);
                if (logger.isDebugEnabled()) {
                    logger.debug("Value of randomId : " + randomId);
                }
            }
            return randomId;
        }
        else
        {
            return patientDAO.getNextPatientId();
        }
    }

    /**
     * Gets the next patient.
     *
     * @return the next patient
     */
    protected Patient getNextPatient(){

        DataGenerationStrategy strategy = getDataGenerationStrategy();
        if (logger.isDebugEnabled()) {
            logger.debug("Value of strategy : " + strategy);
        }
        Patient p = null;

        if(strategy == DataGenerationStrategy.ADD_NEW_ALWAYS)
        {
            p = createAndReturnNewPatient(patientDAO.getNextPatientId());
        }
        else if(strategy == DataGenerationStrategy.ALWAYS_APPEND)
        {
            p = patientDAO.findPatient(getNextRandomPatientId());
        }
        else
        {
            long nextRandomId = getNextRandomPatientId();
            p = patientDAO.findPatient(nextRandomId);
            if(p == null)
            {
                p = createAndReturnNewPatient(nextRandomId);
            }
        }

        // add counter to used patient ids
        usedIds.add(p.getId());
        // add ehr to patient record if it does not exist
        verifyAndSetEHR(p);
        if (logger.isDebugEnabled()) {
            logger.debug("Returning next patient with ID : " + p.getId());
            logger.debug("p.getEhr().getPatientId() = " + p.getEhr().getPatientId());
        }
        return p;
    }

    private Patient createAndReturnNewPatient(long patientId){
        if (logger.isDebugEnabled()) {
            logger.debug("Creating new patient with id : "+patientId);
        }
        // create a new random patient and return as patient
        Patient p = patientUtilsService.createRandomPatient(getMinPatientAgeInYears());
        logger.debug("p.getId() = " + p.getId());
//        logger.debug("p.getEhr().getPatientId() = " + p.getEhr().getPatientId());
////        verifyAndSetEHR(p);
//        logger.debug("p.getEhr().getPatientId() = " + p.getEhr().getPatientId());
        patientDAO.savePatient(p);

        if (logger.isDebugEnabled()) {
            logger.debug("Finished creating new patient with id : "+patientId);
//            logger.debug("p.getEhr().getPatientId() = " + p.getEhr().getPatientId());
        }

        return p;
    }

    private void verifyAndSetEHR(Patient p){
        if(p.getEhr() == null)
        {
            EHR ehr = ehrFactory.getEHR();
            ehr.setPatientId(p.getId());
            p.setEhr(ehr);
        }
        else if(p.getEhr().getPatientId() == 0)
        {
            p.getEhr().setPatientId(p.getId());
        }
        logger.debug("p.getEhr().getPatientId() = " + p.getEhr().getPatientId());
    }

    /**
     * Gets the random concept.
     *
     * @param set the set
     *
     * @return the random concept
     */
    private String getRandomConcept(Set<String> set) {
        // get random concept
        double ran = Math.random()*set.size();
        int ranNum = (int) Math.floor(ran);
        List<String> list = new ArrayList<String>(set);

        return list.get(ranNum);
    }

    protected CloseToUserExpression getRandomExpression(Set<CloseToUserExpression> set) {

        if(logger.isDebugEnabled()){
            logger.debug("Returning a random expression from set of size : "+set.size());
        }
        Random random = new Random();
        int ranNum = 0;
        List<CloseToUserExpression> list = new ArrayList<CloseToUserExpression>(set);

        if(set.size() == 1)
        {
            ranNum = 0;
        }
        else if (set.size() > 1)
        {
            // get random concept
            ranNum = random.nextInt(set.size());
            if (logger.isDebugEnabled()) {
                logger.debug("ranNum = " + ranNum);
            }
        }
        else
        {
            throw new IllegalArgumentException("No expressions exist in collection passed.");
        }

        return list.get(ranNum);
    }

    protected CloseToUserExpression getRandomSubTypeExpression(CloseToUserExpression expression){
        /*
            if set to include pre-coordinated data, we generate both pre and post-coordinate data
            in equal parts. We use modulo 2 for ranNum to get such binary values
         */

        Random random = new Random();
        int binaryRan = random.nextInt(10);

        if (logger.isDebugEnabled()) {
            logger.debug("binaryRan = " + binaryRan);
        }
        if(isIncludePrecoordinatedData() && (binaryRan % 2 == 0))
        {
            // get focus concept of randomExpression
            SnomedConcept focusConcept = expression.getSingletonFocusConcept();
            // return a random descendant of focusConcept as close to user expression
            return randomSubtypeGenerator.getSubTypeExpression(focusConcept);
        }
        else
        {
            return randomSubtypeGenerator.getSubTypeExpression(expression);
        }
    }

    public synchronized void setPatientUtilsService(PatientUtilsService patientUtilsService) {
        this.patientUtilsService = patientUtilsService;
    }

    public synchronized void setEhrFactory(EHRFactory ehrFactory) {
        this.ehrFactory = ehrFactory;
    }

    public synchronized void setRandomSubtypeGenerator(RandomSubtypeGenerator randomSubtypeGenerator) {
        this.randomSubtypeGenerator = randomSubtypeGenerator;
    }

    public synchronized void setPatientDAO(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    public boolean isStopEngine() {
        return stopEngine;
    }

    public void setStopEngine(boolean stopEngine) {
        this.stopEngine = stopEngine;
    }

    public synchronized void setExpressionSubsumptionRelationshipDAO(ExpressionSubsumptionRelationshipDAO expressionSubsumptionRelationshipDAO) {
        this.expressionSubsumptionRelationshipDAO = expressionSubsumptionRelationshipDAO;
    }

    public PatientUtilsService getPatientUtilsService() {
        return patientUtilsService;
    }

    public EHRFactory getEhrFactory() {
        return ehrFactory;
    }

    public RandomSubtypeGenerator getRandomSubtypeGenerator() {
        return randomSubtypeGenerator;
    }

    public PatientDAO getPatientDAO() {
        return patientDAO;
    }
}
