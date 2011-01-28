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
package uk.nhs.cfh.dsp.snomed.converters.human.readable.impl;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRoleGroupExpression;
import uk.nhs.cfh.dsp.snomed.mrcm.MRCMService;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedRelationshipImpl;

import java.io.File;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender}
 * that uses some naive heuristics to render a {@link uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 27, 2010 at 10:38:03 PM
 */
public class HeuristicBasedHumanReadableRenderImpl extends AbstractHumanReadableRenderer
        implements HumanReadableRender, ConfigurationListener {

    /** The logger. */
    private static Log logger = LogFactory.getLog(HeuristicBasedHumanReadableRenderImpl.class);
    
    /** The italicise attributes. */
    private boolean italiciseAttributes = true;
    
    /** The mrcm service. */
    private MRCMService mrcmService;
    
    /** The Constant FOCUS_CONCEPT. */
    private static final String FOCUS_CONCEPT = "FOCUS_CONCEPT";
    
    /** The situation ordering list. */
    private List<String> situationOrderingList = new ArrayList<String>();
    
    /** The finding ordering list. */
    private List<String> findingOrderingList = new ArrayList<String>();
    
    /** The procedure ordering list. */
    private List<String> procedureOrderingList = new ArrayList<String>();
    
    /** The human rendering map. */
    private Map<String, String> humanRenderingMap = new HashMap<String, String>();
    private String renderingFileLocation;
    private XMLConfiguration configuration;

    /**
     * Instantiates a new heuristic based human readable render impl.
     *
     * @param mrcmService the mrcm service
     */
    public HeuristicBasedHumanReadableRenderImpl(MRCMService mrcmService) {
        this.mrcmService = mrcmService;
        configuration = new XMLConfiguration();
        configuration.setExpressionEngine(new XPathExpressionEngine());
        // this reload strategy wont work because we don't use the config object directly        
        configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
    }

    /**
     * Empty constructor for IOC.
     */
    public HeuristicBasedHumanReadableRenderImpl() {
        configuration = new XMLConfiguration();
        configuration.setExpressionEngine(new XPathExpressionEngine());
        // this reload strategy wont work because we don't use the config object directly
        configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
    }

    /*
     * this method initialises the parent concepts used by the contexts. This method needs to be
     * called before this object is used.
     */
    /**
     * Intialise collections.
     */
    public synchronized void intialiseCollections(){

        // populate list with standard set of Attributes
        situationOrderingList.add(ConceptType.ATTRIBUTE_FINDING_CONTEXT.getID());  // finding context
        situationOrderingList.add(ConceptType.ATTRIBUTE_TEMPORAL_CONTEXT.getID());  // temporal context
        situationOrderingList.add(ConceptType.ATTRIBUTE_PROCEDURE_CONTEXT.getID());  //  procedure context
        situationOrderingList.add(FOCUS_CONCEPT);  //  FOCUS CONCEPT
        situationOrderingList.add(ConceptType.ATTRIBUTE_SUBJECT_RELATIONSHIP_CONTEXT.getID());  // subject relationship context
//        situationOrderingList.add(ConceptType.ATTRIBUTE_ASSOCIATED_FINDING.getID());  // associated finding
//        situationOrderingList.add(ConceptType.ATTRIBUTE_ASSOCIATED_PROCEDURE.getID());  // associated procedure

        findingOrderingList.add(ConceptType.ATTRIBUTE_EPISODICITY.getID());     // episodicity
        findingOrderingList.add(ConceptType.ATTRIBUTE_COURSE.getID());    // course
        findingOrderingList.add(ConceptType.ATTRIBUTE_SEVERITIES.getID());    // severities
        findingOrderingList.add(FOCUS_CONCEPT);    // FOCUS_CONCEPT
        findingOrderingList.add(ConceptType.ATTRIBUTE_ASSOCIATED_MORPHOLOGY.getID());    // associated morphology
        findingOrderingList.add(ConceptType.ATTRIBUTE_LATERALITY.getID());    // laterality
        findingOrderingList.add(ConceptType.ATTRIBUTE_FINDING_SITE.getID());    // finding site

        procedureOrderingList.add(ConceptType.ATTRIBUTE_PRIORITY.getID());    // priority
        procedureOrderingList.add(ConceptType.ATTRIBUTE_METHOD.getID());    // method
        procedureOrderingList.add(FOCUS_CONCEPT);    // FOCUS_CONCEPT
        procedureOrderingList.add(ConceptType.ATTRIBUTE_LATERALITY.getID());    // laterality
        procedureOrderingList.add(ConceptType.ATTRIBUTE_DIRECT_PROCEDURE_SITE.getID());    //direct procedure site
        procedureOrderingList.add(ConceptType.ATTRIBUTE_INDIRECT_PROCEDURE_SITE.getID());    //indirect procedure site
        procedureOrderingList.add(ConceptType.ATTRIBUTE_PROCEDURE_SITE.getID());    //procedure site
        procedureOrderingList.add(ConceptType.ATTRIBUTE_ACCESS.getID());    //access
        procedureOrderingList.add(ConceptType.ATTRIBUTE_SURGICAL_APPROACH.getID());    //surgical approach
        procedureOrderingList.add(ConceptType.ATTRIBUTE_USING_DEVICE.getID());    //using device
        procedureOrderingList.add(ConceptType.ATTRIBUTE_USING_ACCESS_DEVICE.getID());    //using access device
        procedureOrderingList.add(ConceptType.ATTRIBUTE_USING_ENERGY.getID());    //using energy
        procedureOrderingList.add(ConceptType.ATTRIBUTE_USING_SUBSTANCE.getID());    //using substance
    }

    /**
     * update human readable map.
     *
     */
    private void updateHumanRenderingMap(){

        File file = new File(renderingFileLocation);
        logger.info("renderingFileLocation = " + renderingFileLocation);
        if (file.exists() && file.canRead())
        {
            try
            {
                configuration.load(file);
                List fields = configuration.configurationsAt("//rendered_attribute");
                // clear existing map
                humanRenderingMap.clear();
                
                for (Object field : fields)
                {
                    HierarchicalConfiguration sub = (HierarchicalConfiguration) field;
                    // sub contains now all data about a single field
                    String attributeId = sub.getString("id");
                    String attributeText = sub.getString("text");

                    // put values in map
                    humanRenderingMap.put(attributeId, attributeText);
                }
                logger.debug("humanRenderingMap = " + humanRenderingMap);
            }
            catch (ConfigurationException e) {
                logger.warn("Error reading configuration file. Nested exception is : " + e.fillInStackTrace().getMessage());
            }
        }
        else
        {
            logger.warn("Error reading file from provided URL.");
            throw new IllegalArgumentException("Error reading file from provided URL.");
        }
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.converters.human.readable.impl.AbstractHumanReadableRenderer#getHumanReadableLabel(uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression)
    */
    @Override
    protected String getHumanReadableLabel(CloseToUserExpression closeToUserExpression) {
        // get refinements and process them based on rules and priority
        Collection<PropertyExpression> propertyExpressions = closeToUserExpression.getRefinementExpressions();
        SnomedConcept focusConcept = closeToUserExpression.getSingletonFocusConcept();
        ConceptType conceptType = focusConcept.getType();
        boolean associatedConceptPresent = false;
        // create a temporary list object to store all components for ordering and rendering
        List<SnomedRelationshipPropertyExpression> tempList =
                    new ArrayList<SnomedRelationshipPropertyExpression>();

        Set<SnomedRelationshipPropertyExpression> refiningRelationships = new HashSet<SnomedRelationshipPropertyExpression>();
        // get list of sanctioned attributes on focus concept
        Set<String> sanctionedAttributes = mrcmService.getSanctionedAttributes(conceptType);
        // get relationships on all property expressions
        for(PropertyExpression propertyExpression : propertyExpressions)
        {
            if(propertyExpression instanceof SnomedRelationshipPropertyExpression)
            {
                SnomedRelationshipPropertyExpression relationshipPropertyExpression = (SnomedRelationshipPropertyExpression) propertyExpression;
                String relationshipType = getRelationshipType(relationshipPropertyExpression);
                // check if relationshipType is associated procedure or finding in a situation
                if(ConceptType.ATTRIBUTE_ASSOCIATED_FINDING.getID().equals(relationshipType) ||
                        "".equals(relationshipType))
                {
                    associatedConceptPresent = true;
                }

                // check for unsanctioned attributes - but consider laterality
                if(sanctionedAttributes.contains(relationshipType) ||
                        ConceptType.ATTRIBUTE_LATERALITY.getID().equals(relationshipType))
                {
                    refiningRelationships.add(relationshipPropertyExpression);
                }
                else
                {
                    logger.warn("Found unsanctioned relationship type... ignoring");
                }
            }
            else if(propertyExpression instanceof SnomedRoleGroupExpression)
            {
                logger.warn("Encountered role group expression... Ignoring the expression");
                getHumanReadableLabel((SnomedRoleGroupExpression) propertyExpression);
            }
        }

        // check if we have processed all refinements
        if(refiningRelationships.size() == propertyExpressions.size())
        {
            // add refining relationships to tempList
            tempList.addAll(refiningRelationships);

            // order relationships using comparator and focus concept type
            if(ConceptType.SITUATION_WEC == conceptType || ConceptType.FINDING_WEC == conceptType
                    || ConceptType.PROCEDURE_WEC == conceptType)
            {
                if (!associatedConceptPresent) {
                    // add focus concept as a relationship
                    addFocusConceptToList(tempList, focusConcept);
                }
                Collections.sort(tempList, new HumanReadablePropertyExpressionComparator(situationOrderingList));
            }
            else if(ConceptType.FINDING == conceptType || ConceptType.DISEASE == conceptType)
            {
                // add focus concept as a relationship
                addFocusConceptToList(tempList, focusConcept);
                Collections.sort(tempList, new HumanReadablePropertyExpressionComparator(findingOrderingList));
            }
            else if(ConceptType.PROCEDURE == conceptType || ConceptType.SURGICAL_PROCEDURE == conceptType
                    || ConceptType.EVALUATION_PROCEDURE == conceptType)
            {
                // add focus concept as a relationship
                addFocusConceptToList(tempList, focusConcept);
                Collections.sort(tempList, new HumanReadablePropertyExpressionComparator(procedureOrderingList));
            }
            else
            {
                addFocusConceptToList(tempList, focusConcept);
            }
        }
        else
        {
            logger.warn("Found some property expressions that do not belong to focus concept");
        }

        return getHumanReadableLabel(tempList);
    }

    /**
     * Adds the focus concept to list.
     *
     * @param tempList the temp list
     * @param focusConcept the focus concept
     */
    private void addFocusConceptToList(List<SnomedRelationshipPropertyExpression> tempList, SnomedConcept focusConcept){
        SnomedRelationship r = new SnomedRelationshipImpl();
        r.setRelationshipType("FOCUS_CONCEPT");
        r.setTargetConcept(focusConcept);
        SnomedRelationshipPropertyExpression pe = new SnomedRelationshipPropertyExpression(r);
        // add pe to refiningRelationships
        tempList.add(pe);
    }

    /**
     * Gets the human readable label.
     *
     * @param relationshipPropertyExpressions the relationship property expressions
     * @return the human readable label
     */
    protected String getHumanReadableLabel(Collection<SnomedRelationshipPropertyExpression> relationshipPropertyExpressions){
        String humanReadableString = "";
        int counter = 0;
        for(SnomedRelationshipPropertyExpression p : relationshipPropertyExpressions)
        {
            if(counter == 0)
            {
                humanReadableString = getHumanReadableLabel(p);
            }
            else
            {
                humanReadableString = humanReadableString+" , "+getHumanReadableLabel(p);
            }

            // increment counter
            counter++;
        }

        return humanReadableString;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.human.readable.impl.AbstractHumanReadableRenderer#getHumanReadableLabel(uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression)
     */
    @Override
    protected String getHumanReadableLabel(SnomedRelationshipPropertyExpression propertyExpression){
        // get relationship name and use look up map for rendering text
        String relationshipType = getRelationshipType(propertyExpression);
        SnomedConcept targetConcept = propertyExpression.getRelationship().getTargetConcept();
        if(humanRenderingMap.containsKey(relationshipType))
        {
            return humanRenderingMap.get(relationshipType)+" "+getHumanReadableLabel(targetConcept);
        }
        else if(situationOrderingList.contains(relationshipType) || findingOrderingList.contains(relationshipType)
                || procedureOrderingList.contains(relationshipType))
        {
            return getHumanReadableLabel(targetConcept);
        }
        else
        {
            return getHumanReadableLabel(relationshipType)+" of "+getHumanReadableLabel(targetConcept);
        }
    }

    /**
     * Gets the relationship type.
     *
     * @param propertyExpression the property expression
     * @return the relationship type
     */
    private String getRelationshipType(SnomedRelationshipPropertyExpression propertyExpression){
        if(propertyExpression != null)
        {
            SnomedRelationship relationship = propertyExpression.getRelationship();
            if(relationship != null)
            {
                String relationshipType = relationship.getRelationshipType();
                if(relationshipType != null)
                {
                    return relationshipType;
                }
                else
                {
                    throw new IllegalArgumentException("Relationship does not contain a type.");
                }
            }
            else
            {
                throw new IllegalArgumentException("Relationship Expression passed does not contain a relationship");
            }
        }
        else
        {
            throw new IllegalArgumentException("Relationship expression passed can not be null.");
        }
    }

    /**
     * Sets the mrcm service.
     *
     * @param mrcmService the new mrcm service
     */
    public synchronized void setMrcmService(MRCMService mrcmService) {
        this.mrcmService = mrcmService;
    }

    /**
     * Checks if is italicise attributes.
     *
     * @return true, if is italicise attributes
     */
    public boolean isItaliciseAttributes() {
        return italiciseAttributes;
    }

    /**
     * Sets the italicise attributes.
     *
     * @param italiciseAttributes the new italicise attributes
     */
    public void setItaliciseAttributes(boolean italiciseAttributes) {
        this.italiciseAttributes = italiciseAttributes;
    }

    public void configurationChanged(ConfigurationEvent event) {
        if(!event.isBeforeUpdate())
        {
           // we know that config file has changed, so we update humanRenderingMap
            updateHumanRenderingMap();
        }
    }

    /**
     * The Class HumanReadablePropertyExpressionComparator.
     */
    class HumanReadablePropertyExpressionComparator implements Comparator<SnomedRelationshipPropertyExpression>{

        /** The attributes list. */
        private List<String> attributesList;

        /**
         * Instantiates a new human readable property expression comparator.
         *
         * @param attributesList the attributes list
         */
        HumanReadablePropertyExpressionComparator(List<String> attributesList) {
            this.attributesList = attributesList;
        }

        /* (non-Javadoc)
         * @see java.util.Comparator#compare(T, T)
         */
        public int compare(SnomedRelationshipPropertyExpression r1, SnomedRelationshipPropertyExpression r2) {
            String r1Type = getRelationshipType(r1);
            String r2Type = getRelationshipType(r2);

            if(attributesList.indexOf(r1Type) < attributesList.indexOf(r2Type))
            {
                return -1;
            }
            else if(attributesList.indexOf(r1Type) > attributesList.indexOf(r2Type))
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }

    public void setRenderingFileLocation(String renderingFileLocation) {
        this.renderingFileLocation = renderingFileLocation;
        // update humanRenderingMap
        updateHumanRenderingMap();
    }
}
