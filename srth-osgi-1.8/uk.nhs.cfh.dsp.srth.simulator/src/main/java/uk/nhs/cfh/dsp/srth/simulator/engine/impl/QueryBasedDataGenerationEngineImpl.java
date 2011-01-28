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
import uk.nhs.cfh.dsp.srth.demographics.PatientDAO;
import uk.nhs.cfh.dsp.srth.demographics.PatientUtilsService;
import uk.nhs.cfh.dsp.srth.demographics.person.Patient;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerService;
import uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory;
import uk.nhs.cfh.dsp.srth.information.model.impl.EHRFactory;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.EHR;
import uk.nhs.cfh.dsp.srth.query.model.om.*;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.*;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.srth.query.transform.error.IllegalExpressionsNumberException;
import uk.nhs.cfh.dsp.srth.simulator.utils.RandomSubtypeGenerator;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * A class that generates random patient data based on a given {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement}.
 * The number of records generated is a cartesian product of the individual sub queries
 * that make up the {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement} after the logical operators have
 * been resolved.
 *
 * This class uses {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerService} to publish the
 * number of records created using a 'RECORDS_CREATED' property name.
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Apr 7, 2009 at 3:09:45 PM
 * <br>
 */
public class QueryBasedDataGenerationEngineImpl extends AbstractQueryBasedDataGenerationEngine{

    /** The logger. */
    private static Log logger = LogFactory.getLog(QueryBasedDataGenerationEngineImpl.class);

    /** The data map. */
    private Map<QueryExpression, Set<CloseToUserExpression>> dataMap = new HashMap<QueryExpression, Set<CloseToUserExpression>>();

    /** The data constraints map. */
    private Map<QueryComponentExpression, Constraint> dataConstraintsMap = new HashMap<QueryComponentExpression, Constraint>();

    /** The temporal constraints map. */
    private Map<QueryComponentExpression, Constraint> temporalConstraintsMap = new HashMap<QueryComponentExpression, Constraint>();

    private Map<CloseToUserExpression, QueryComponentExpression> expressionsMap = new HashMap<CloseToUserExpression, QueryComponentExpression>();
    private PropertyChangeTrackerService propertyChangeTrackerService;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");


    public QueryBasedDataGenerationEngineImpl(TerminologyConceptDAO terminologyConceptDAO,
                                              PatientDAO patientDAO,
                                              ClinicalEntryFactory clinicalEntryFactory,
                                              PatientUtilsService patientUtilsService,
                                              EHRFactory ehrFactory,
                                              PropertyChangeTrackerService propertyChangeTrackerService,
                                              RandomSubtypeGenerator randomSubtypeGenerator) {
        super(terminologyConceptDAO, clinicalEntryFactory, patientDAO, ehrFactory, randomSubtypeGenerator, patientUtilsService);
        this.propertyChangeTrackerService = propertyChangeTrackerService;
    }

    public QueryBasedDataGenerationEngineImpl(QueryService queryService){
        super(queryService);
    }

    /**
     *     No args constructor for IOC
     */
    public QueryBasedDataGenerationEngineImpl() {
        this(null);
    }

    /* (non-Javadoc)
    * @see main.java.uk.nhs.cfh.dsp.srth.simulator.engine.impl.AbstractQueryBasedDataGenerationEngine#generateDataMapForQuery(ReportingQueryStatement)
    */
    public Map<QueryExpression, Set<CloseToUserExpression>> generateDataMapForQuery(QueryStatement query){
        // clear data map
        dataMap.clear();

        // check query has only one contained element
        List<QueryExpression> expressions = new ArrayList<QueryExpression>(query.getContainedExpressions());
        if(expressions.size() == 1)
        {
            Map<QueryExpression, Set<CloseToUserExpression>> dataMap = generateDataForExpression(expressions.get(0));
            logger.debug("Value of dataMap.keySet().size() : " + dataMap.keySet().size());
            return dataMap;
        }
        else
        {
            throw new IllegalExpressionsNumberException();
        }
    }

    /* (non-Javadoc)
      * @see main.java.uk.nhs.cfh.dsp.srth.simulator.engine.impl.AbstractQueryBasedDataGenerationEngine#generateDataForQuery(ReportingQueryStatement)
      */
    public void generateDataForQuery(QueryStatement query){

        // track time for query data generation
        long startTime = Calendar.getInstance().getTimeInMillis();
        // generate datamap for query
        Map<QueryExpression, Set<CloseToUserExpression>> dataMap = generateDataMapForQuery(query);
        // generate data from datamap
        generateDataFromDataMap(dataMap);

        long dataGenTime = Calendar.getInstance().getTimeInMillis() - startTime;
        logger.info("Value of dataGenTime : " + dataGenTime);
    }

    /* (non-Javadoc)
      * @see main.java.uk.nhs.cfh.dsp.srth.simulator.engine.impl.AbstractQueryBasedDataGenerationEngine#generateDataFromDataMap(Map)
      */
    public void generateDataFromDataMap(Map<QueryExpression, Set<CloseToUserExpression>> dataMap){

        logger.info("Starting data generation from data map");
        // now loop through union sets and create entities to be added to patient record
        long counter=0;

        // save all expressions as list for use during during data generation
        List<Set<CloseToUserExpression>> dataList = new ArrayList<Set<CloseToUserExpression>>();
        for(QueryExpression exp : dataMap.keySet())
        {
            Set<CloseToUserExpression> includedExpressions = dataMap.get(exp);
            dataList.add(includedExpressions);
        }

        // loop through data map and create patients and persist them
        while(counter < getMaxPatientNumber() && ! isStopEngine())
        {
            Patient p = getNextPatient();
            EHR ehr = p.getEhr();
            logger.debug("ehr.getId() = " + ehr.getPatientId());

            Calendar c1Time;
            if (dataList.size() < 1)
            {
                throw new IllegalArgumentException("No expressions in the query passed");
            }
            else
            {
                logger.debug("Starting data creation for C1");
                CloseToUserExpression sp1 = getRandomExpression(dataList.get(0));
                CloseToUserExpression sb1 = getRandomSubTypeExpression(sp1);
                logger.debug("sb1.getCompositionalGrammarForm() = " + sb1.getCompositionalGrammarForm());
                c1Time = getPatientUtilsService().getSensibleRandomDateInTimeRangeRelativeToPatient(p, 90, 35);
                logger.debug("c1Time = " + sdf.format(c1Time.getTime()));
                checkAndAddEntryToEHR(sp1, sb1, ehr, c1Time, expressionsMap.get(sp1));
                logger.debug("Added C1 to ehr");

                if (dataList.size() >1)
                {
                    logger.debug("Starting data creation for C2");
                    CloseToUserExpression sp2 = getRandomExpression(dataList.get(1));
                    CloseToUserExpression sb2 = getRandomSubTypeExpression(sp2);
                    logger.debug("sb2.getCompositionalGrammarForm() = " + sb2.getCompositionalGrammarForm());
                    Calendar c2Time = getPatientUtilsService().getRandomDateInTimeRangeInFuture(c1Time, 40, 0, 11, 0, 30, 0);
                    logger.debug("c2Time = " + sdf.format(c2Time.getTime()));
                    checkAndAddEntryToEHR(sp2, sb2, ehr, c2Time, expressionsMap.get(sp2));
                    logger.debug("Added C2 to ehr");
                    if (dataList.size() >2)
                    {
                        logger.debug("Starting data creation for C3");
                        CloseToUserExpression sp3 = getRandomExpression(dataList.get(2));
                        CloseToUserExpression sb3 = getRandomSubTypeExpression(sp3);
                        Calendar c3Time = getPatientUtilsService().getRandomDateInTimeRangeInFuture(c2Time, 40,	0, 11, 0, 30, 0);
                        logger.debug("sdf.format(c3Time.getTime()) = " + sdf.format(c3Time.getTime()));
                        checkAndAddEntryToEHR(sp3, sb3, ehr, c3Time, expressionsMap.get(sp3));
                        logger.debug("Added C3 to ehr");
                        if (dataList.size() > 3)
                        {
                            logger.debug("Starting data creation for C5");
                            CloseToUserExpression sp4 = getRandomExpression(dataList.get(3));
                            CloseToUserExpression sb4 = getRandomSubTypeExpression(sp4);
                            Calendar c4Time = getPatientUtilsService().getRandomDateInTimeRangeInFuture(c3Time,	40, 0, 11, 0, 30, 0);
                            logger.debug("sdf.format(c4Time.getTime()) = " + sdf.format(c4Time.getTime()));
                            checkAndAddEntryToEHR(sp4, sb4, ehr, c4Time, expressionsMap.get(sp4));
                            logger.debug("Added C4 to ehr");
                            if (dataList.size() > 4)
                            {
                                logger.debug("Starting data creation for C5");
                                CloseToUserExpression sp5 = getRandomExpression(dataList.get(4));
                                CloseToUserExpression sb5 = getRandomSubTypeExpression(sp5);
                                Calendar c5Time = getPatientUtilsService().getRandomDateInTimeRangeInFuture(c4Time, 40, 0, 11, 0, 30, 0);
                                logger.debug("sdf.format(c5Time.getTime()) = " + sdf.format(c5Time.getTime()));
                                checkAndAddEntryToEHR(sp5, sb5, ehr, c5Time, expressionsMap.get(sp5));
                                logger.debug("Added C5 to ehr");
                                if (dataList.size() > 5)
                                {
                                    logger.debug("Starting data creation for C6");
                                    CloseToUserExpression sp6 = getRandomExpression(dataList.get(5));
                                    CloseToUserExpression sb6 = getRandomSubTypeExpression(sp1);
                                    Calendar c6Time = getPatientUtilsService().getRandomDateInTimeRangeInFuture(c5Time, 40, 0, 11, 0, 30, 0);
                                    logger.debug("sdf.format(c6Time.getTime()) = " + sdf.format(c6Time.getTime()));
                                    checkAndAddEntryToEHR(sp6, sb6, ehr, c6Time, expressionsMap.get(sp6));
                                    logger.debug("Added C6 to ehr");
                                }
                            }
                        }
                    }
                }
            }

            // persist patient
            getPatientDAO().savePatient(p);
            
            if (logger.isDebugEnabled()) {
                logger.debug("Saved patient with ID : " + p.getId());
            }
            // flush cache
			if (counter % 50 == 0) {
                getPatientDAO().flushCache();
			}
            
            // increment counter
            if (logger.isDebugEnabled()) {
                // calculate the frequency for displaying count based on max patient count
                long frequency = getMaxPatientNumber()/10;
                if(frequency == 0)
                {
                    frequency++;
                }

                if ((counter % frequency) == 0) {
                    logger.debug("Saved "+ counter+ " patients");
                }
            }

            if (isTrackProgress() && propertyChangeTrackerService != null) {
                // publish property change event
                propertyChangeTrackerService.firePropertyChanged("RECORDS_CREATED", counter, counter+1, this);
            }

            logger.info("Saved patient with ID : " + p.getId());
            // increment counter
            counter++;
        }
    }

    /**
     * Check and add entry to ehr.
     *
     * @param expression the close to user expression
     * @param ehr the ehr
     * @param time the time
     * @param expression the expression
     */
    private void checkAndAddEntryToEHR(CloseToUserExpression superTypeExpression, CloseToUserExpression subTypeExpression, EHR ehr, Calendar time,
                                       QueryComponentExpression expression){

        // check if there is a data constraint specified for the expression
        if(dataConstraintsMap.containsKey(expression))
        {
            Constraint constraint = dataConstraintsMap.get(expression);
            if(constraint instanceof DataConstantConstraint)
            {
                DataConstantConstraint dataConstantConstraint = (DataConstantConstraint) constraint;
                double value = dataConstantConstraint.asDouble();
                // add entry to ehr
                addObservationEntryToEHR(subTypeExpression, ehr, time, value);
            }
            else if(constraint instanceof DataRangeConstraint)
            {
                DataRangeConstraint rangeConstraint = (DataRangeConstraint) constraint;
                // get lower and upper facets
                double upperBound = rangeConstraint.getUpperBound();
                double lowerBound = rangeConstraint.getLowerBound();
                if(logger.isDebugEnabled()){
                    logger.debug("Value of upperBound : "+ upperBound);
                    logger.debug("Value of lowerBound : "+ lowerBound);
                }

                // get a random value between these bounds
                double range = upperBound - lowerBound;
                double randomValue = lowerBound + (Math.random() * range);
                if(logger.isDebugEnabled()){
                    logger.debug("Value of randomValue : "+ randomValue);
                }

                super.addObservationEntryToEHR(subTypeExpression, ehr, time, randomValue);
            }
            else if(constraint instanceof DataRangeFacetConstraint)
            {
                /*
                     *  warn user that this is currently not supported because all facet
                     *  constraints can be expressed as range constraints
                     */
                logger.warn("Data generation based on data facet constraints is not implemented");
            }
        }
        else if(temporalConstraintsMap.containsKey(expression))
        {
//			Constraint constraint = temporalConstraintsMap.get(expression);
            // warn that this feature is not implemented yet!
            logger.warn("Data generation based on temporal constraints is not implemented");
        }
        else
        {
            super.addEntryToEHR(superTypeExpression, subTypeExpression, ehr, time);
        }
    }

    /**
     * Generate data for expression.
     *
     * @param expression the expression
     *
     * @return the map< reporting query expression, set< string>>
     */
    private Map<QueryExpression, Set<CloseToUserExpression>> generateDataForExpression(QueryExpression expression) {

        Map<QueryExpression, Set<CloseToUserExpression>> dataMap = new HashMap<QueryExpression, Set<CloseToUserExpression>>();
        // handle differently based on expression type
        if(expression instanceof QueryIntersectionExpression)
        {
            Map<QueryExpression, Set<CloseToUserExpression>> map = getDataMapForIntersectionObject((QueryIntersectionExpression) expression);
            // add all to dataMap
            dataMap.putAll(map);
        }
        else if (expression instanceof QueryUnionExpression)
        {
            Map<QueryExpression, Set<CloseToUserExpression>> map = getDataMapForUnionObject((QueryUnionExpression) expression);
            // add all to dataMap
            dataMap.putAll(map);
        }
        else if (expression instanceof QueryComponentExpression)
        {
            Map<QueryExpression, Set<CloseToUserExpression>> map = getDataMapForQueryComponentExpression((QueryComponentExpression) expression);
            // add map to data map
            dataMap.putAll(map);
        }

        return dataMap;
    }

    /**
     * Gets the data map for union object.
     *
     * @param unionObject the union object
     *
     * @return the data map for union object
     */
    private Map<QueryExpression, Set<CloseToUserExpression>> getDataMapForUnionObject(QueryUnionExpression unionObject) {

        Map<QueryExpression, Set<CloseToUserExpression>> dataMap = new HashMap<QueryExpression, Set<CloseToUserExpression>>();
        List<Map<QueryExpression, Set<CloseToUserExpression>>> mapsList = new ArrayList<Map<QueryExpression,Set<CloseToUserExpression>>>();
        Set<CloseToUserExpression> includedIds = new HashSet<CloseToUserExpression>();

        // get contained expressions and loop through them
        for(QueryExpression expression : unionObject.getContainedExpressions())
        {
            Map<QueryExpression, Set<CloseToUserExpression>> map = generateDataForExpression(expression);
            // add to map list
            mapsList.add(map);
        }

        // process map list by adding contents of map list into a single key value pair
        for(Map<QueryExpression, Set<CloseToUserExpression>> map : mapsList)
        {
            // get value set of each map and add to data map
            for(QueryExpression exp : map.keySet())
            {
                Set<CloseToUserExpression> idSet = map.get(exp);
                // add idSet to includedIds
                includedIds.addAll(idSet);
            }
        }

        // put included concept ids in map
        dataMap.put(unionObject, includedIds);

        return dataMap;
    }

    /**
     * Gets the data map for intersection object.
     *
     * @param intersectionObject the intersection object
     *
     * @return the data map for intersection object
     */
    private Map<QueryExpression, Set<CloseToUserExpression>> getDataMapForIntersectionObject(QueryIntersectionExpression intersectionObject) {

        Map<QueryExpression, Set<CloseToUserExpression>> dataMap = new HashMap<QueryExpression, Set<CloseToUserExpression>>();
        // get contained expressions and loop through them
        for(QueryExpression expression : intersectionObject.getContainedExpressions())
        {
            Map<QueryExpression, Set<CloseToUserExpression>> map = generateDataForExpression(expression);
            // add map to data map
            dataMap.putAll(map);
        }

        return dataMap;
    }

    private Map<QueryExpression, Set<CloseToUserExpression>> getDataMapForQueryComponentExpression(QueryComponentExpression componentExpression){

        Map<QueryExpression, Set<CloseToUserExpression>> dataMap =  new HashMap<QueryExpression, Set<CloseToUserExpression>>();
        // get expression in included constraint
        CloseToUserExpression includedExpression = (CloseToUserExpression) componentExpression.getIncludedConstraint().getExpression();
        // create constraint map for componentExpression
        createConstraintsMap(componentExpression);

        // add included constraint to conceptsSubQueryMap
        expressionsMap.put(includedExpression, componentExpression);
        // add includedExpression  to datamap
        dataMap.put(componentExpression, Collections.singleton(includedExpression));

        return dataMap;
    }

    /**
     * Creates the constraints map.
     *
     * @param componentExpression
     */
    private void createConstraintsMap(QueryComponentExpression componentExpression) {
        // get constraints specified on sub query and save them if they are data constraints
        for(Constraint constraint : componentExpression.getAdditionalConstraints())
        {
            if(constraint instanceof DataConstraint)
            {
                dataConstraintsMap.put(componentExpression, constraint);
            }
            else if (constraint instanceof TemporalConstraint)
            {
                temporalConstraintsMap.put(componentExpression, constraint);
            }
            else
            {
                logger.warn("Unknown constraint type passed : "+constraint.getClass());
            }
        }
    }

    public synchronized void setPropertyChangeTrackerService(PropertyChangeTrackerService propertyChangeTrackerService) {
        this.propertyChangeTrackerService = propertyChangeTrackerService;
    }
}
