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
package uk.nhs.cfh.dsp.srth.simulator.utils.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.CloseToUserExpressionImpl;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.NormalFormExpressionImpl;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRoleGroupExpression;
import uk.nhs.cfh.dsp.snomed.hierarchyprovider.RandomSubtypeHierarchyProvider;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.srth.simulator.utils.RandomSubtypeGenerator;

import java.util.*;

/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.simulator.utils.RandomSubtypeGenerator}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 8, 2010 at 3:27:20 PM
 */
public class RandomSubtypeGeneratorImpl implements RandomSubtypeGenerator {

    private NormalFormGenerator normalFormGenerator;
    private RandomSubtypeHierarchyProvider hierarchyProvider;
    private TerminologyConceptDAO terminologyConceptDAO;
    private static Log logger = LogFactory.getLog(RandomSubtypeGeneratorImpl.class);
    private boolean useNormalFormForRendering = false;

    public RandomSubtypeGeneratorImpl(NormalFormGenerator normalFormGenerator, RandomSubtypeHierarchyProvider hierarchyProvider,
                                      TerminologyConceptDAO terminologyConceptDAO) {
        this.normalFormGenerator = normalFormGenerator;
        this.hierarchyProvider = hierarchyProvider;
        this.terminologyConceptDAO = terminologyConceptDAO;
    }

    /**
     * no args constructor for IOC
     */
    public RandomSubtypeGeneratorImpl() {
    }

    public SnomedConcept getSubTypeConcept(SnomedConcept parentConcept) {

        if(parentConcept != null)
        {
//            // use hierarchy provider to get list of all sub types
//            Collection<String> descendants = hierarchyProvider.getDescendants(parentConcept.getConceptID());
//            int ranNum = 0;
//            List<String> list = new ArrayList<String>(descendants);
//
//            if(descendants.size() == 0)
//            {
//                logger.info("Found no descendants for concept : "+parentConcept.getConceptID()+"\n" +
//                        "Adding concept to list of descendants.");
//                // add parent concept to descendants list!
//                list.add(parentConcept.getConceptID());
//            }
//            if (descendants.size() > 1)
//            {
//                // get random number
//                Random random = new Random();
//                ranNum = random.nextInt(descendants.size() -1);
//            }

            return TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, hierarchyProvider.getRandomSubtype(parentConcept.getConceptID()));
        }
        else
        {
            throw new IllegalArgumentException("Parent concept passed can not be null.");
        }
    }

    public CloseToUserExpression getSubTypeExpression(CloseToUserExpression expression) {
        if(expression != null)
        {
            /*
                Create a loose clone of the existing expression
             */
            CloseToUserExpression clonedExpression = new CloseToUserExpressionImpl();
            clonedExpression.setFocusConcepts(returnDeepCopyOfConcepts(expression.getFocusConcepts()));
            clonedExpression.setChildExpressions(expression.getChildExpressions());
            clonedExpression.setParentExpressions(expression.getParentExpressions());

            CloseToUserExpression modifiedExpression = new CloseToUserExpressionImpl();

            /*
            get a normal form with rendering for close to user expression and then randomly
            set the values of the filler expressions to their sub types
             */
            NormalFormExpression normalFormExpression;
            if (isUseNormalFormForRendering()) 
            {
                normalFormExpression = normalFormGenerator.getLongNormalFormExpressionForRendering(clonedExpression);
            }
            else
            {
                normalFormExpression = normalFormGenerator.getLongNormalFormExpression(clonedExpression);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("normalFormExpression.getCompositionalGrammarForm() = " + normalFormExpression.getCompositionalGrammarForm());
            }
            
            // get map with modifications for this normalFormExpression
            Map<NormalFormExpression, Set<Object>> modifiedNormalFormExpressionMap = getModifiedExpression(normalFormExpression);
            if (logger.isDebugEnabled()) {
                logger.debug("modifiedNormalFormExpressionMap.size() = " + modifiedNormalFormExpressionMap.size());
                logger.debug("normalFormGenerator.getLongNormalFormExpression(expression).getCompositionalGrammarForm() = " + normalFormGenerator.getLongNormalFormExpression(expression).getCompositionalGrammarForm());
            }

            /*
             check if the original normal form had any child expressions. If it did, then none of the focus concepts
             would have been modified. So we can just add the refinements to the close to user form and return it.
             However, if there are no child expressions, then the focus concepts would have been changed. In this case,
             we replace focus concepts in close to user expression with focus concepts from the modifiedNormalFormExpression
             */

            Collection<Expression> children = normalFormExpression.getChildExpressions();
            if(children.size() >0)
            {
                modifiedExpression.setFocusConcepts(returnDeepCopyOfConcepts(clonedExpression.getFocusConcepts()));
                for (NormalFormExpression nfe : modifiedNormalFormExpressionMap.keySet())
                {
                    for(Object o : getModifications(modifiedNormalFormExpressionMap.get(nfe)))
                    {
                        if(o instanceof PropertyExpression)
                        {
                            modifiedExpression.addRefinement((PropertyExpression) o);
                            logger.debug("Added refinement");
                        }
                    }
                }
            }
            else
            {
                List<NormalFormExpression> listInMap = new ArrayList<NormalFormExpression>(modifiedNormalFormExpressionMap.keySet());
                NormalFormExpression modifiedNormalFormExpression = listInMap.get(0);
                // set focus concepts of modifiedNormalFormExpression as focus concepts in modifiedExpression
                modifiedExpression.setFocusConcepts(returnDeepCopyOfConcepts(modifiedNormalFormExpression.getFocusConcepts()));
            }

            if (logger.isDebugEnabled()) {
                logger.debug("modifiedNormalFormExpression.getCompositionalGrammarForm() = " + modifiedExpression.getCompositionalGrammarForm());
            }
            
            return modifiedExpression;
        }
        else
        {
            throw new IllegalArgumentException("Expression passed can not be null.");
        }
    }

    public CloseToUserExpression getSubTypeExpression(SnomedConcept parentConcept) {
        return new CloseToUserExpressionImpl(getSubTypeConcept(parentConcept));
    }

    private Map<NormalFormExpression, Set<Object>> getModifiedExpression(NormalFormExpression normalFormExpression){

        // we create a new expression and copy values from the original expression
        NormalFormExpression modifiedExpression = new NormalFormExpressionImpl();
        for(Expression e: normalFormExpression.getChildExpressions())
        {
            modifiedExpression.addChildExpression(e);
        }
        modifiedExpression.setParentExpressions(normalFormExpression.getParentExpressions());
        // do a deep copy of concepts to avoid unexpected changes via references
        modifiedExpression.setFocusConcepts(returnDeepCopyOfConcepts(normalFormExpression.getFocusConcepts()));
        modifiedExpression.setProximalPrimitives(returnDeepCopyOfConcepts(normalFormExpression.getProximalPrimitives()));

        
        Set<Object> modifications = new HashSet<Object>();
        List<Expression> childExpressions = new ArrayList<Expression>(modifiedExpression.getChildExpressions());
        if(childExpressions.size() >0)
        {
            // set focus concepts of modifiedExpression as the same the ones in normalFormExpression
            if (childExpressions.size() >1)
            {
                // get a random collection of an arbitrary/random number of child expression.
                int arbitraryNumber = getRandomPositiveIntLessThan(childExpressions.size() -1);
                // store usedRandomNumbers
                Collection<Integer> usedIds = new HashSet<Integer>();
                while(usedIds.size() < arbitraryNumber)
                {
                    usedIds.add(getRandomPositiveIntLessThan(childExpressions.size() - 1));
                }
                List<Expression> oldChildren = new ArrayList<Expression>(arbitraryNumber);
                List<Expression> newChildren = new ArrayList<Expression>(arbitraryNumber);
                for(Integer j : usedIds)
                {
//                    int randomNumber = getRandomPositiveIntLessThan(childExpressions.size() -1);
                    Expression childExpression = childExpressions.get(j);
                    if(childExpression instanceof SnomedRelationshipPropertyExpression)
                    {
                        SnomedRelationshipPropertyExpression relationshipPropertyExpression =
                                (SnomedRelationshipPropertyExpression) childExpression;
                        oldChildren.add(relationshipPropertyExpression);
                        // get a sub type of relationshipPropertyExpression
                        SnomedRelationshipPropertyExpression subtypePropertyExpression =
                                getSubtypeRelationshipExpression(relationshipPropertyExpression, modifications);
                        // add subtypePropertyExpression to newChildren
                        newChildren.add(subtypePropertyExpression);
                    }
                    else if(childExpression instanceof SnomedRoleGroupExpression)
                    {
                        SnomedRoleGroupExpression roleGroupExpression = (SnomedRoleGroupExpression) childExpression;
                        oldChildren.add(roleGroupExpression);
                        // get subtype of roleGroupExpression
                        SnomedRoleGroupExpression subtypeRoleGroupExpression =
                                getSubTypeRoleGroupExpression(roleGroupExpression, modifications);
                        newChildren.add(subtypeRoleGroupExpression);
                    }
                }

                // replace old children with new children
                childExpressions.removeAll(oldChildren);
                childExpressions.addAll(newChildren);

                // add child expressions to modifiedExpression
                modifiedExpression.setChildExpressions(childExpressions);
            }
            else
            {
                Expression childExpression = childExpressions.get(0);
                Expression modifiedChildExpression = null;
                if(childExpression instanceof SnomedRelationshipPropertyExpression)
                {
                    SnomedRelationshipPropertyExpression propertyExpression =
                            (SnomedRelationshipPropertyExpression) childExpression;
                    modifiedChildExpression = getSubtypeRelationshipExpression(propertyExpression, modifications);
                }
                else if(childExpression instanceof SnomedRoleGroupExpression)
                {
                    SnomedRoleGroupExpression roleGroupExpression = (SnomedRoleGroupExpression) childExpression;
                    modifiedChildExpression = getSubTypeRoleGroupExpression(roleGroupExpression, modifications);
                }

                if(modifiedChildExpression != null)
                {
                    // replace childExpression with modifiedChildExpression  in childExpressions
                    childExpressions.remove(childExpression);
                    childExpressions.add(modifiedChildExpression);
                }

                // add child expressions to modifiedExpression
                modifiedExpression.setChildExpressions(childExpressions);
            }
        }
        else
        {
            List<SnomedConcept> focusConcepts = new ArrayList<SnomedConcept>(modifiedExpression.getFocusConcepts());
            if(focusConcepts.size() >1)
            {
                // get arbitrary number of focus concepts to replace
                int arbitraryNumber = getRandomPositiveIntLessThan(focusConcepts.size() -1);
                List<SnomedConcept> arbitraryChildren = new ArrayList<SnomedConcept>(arbitraryNumber);
                for(int i=0; i<arbitraryNumber; i++)
                {
                    arbitraryChildren.add(focusConcepts.get(i));
                }

                // loop through arbitraryChildren and replace them in focusConcepts with a random subtype
                for(SnomedConcept arbitraryConcept: arbitraryChildren)
                {
                    focusConcepts.remove(arbitraryConcept);
                    // add random subtype
                    focusConcepts.add(getSubTypeConcept(arbitraryConcept));
                }

                // set modified focusConcepts in normalFormExpression
                modifiedExpression.setFocusConcepts(focusConcepts);
            }
            else
            {
                // get the lone focus concept and get a random subtype
                SnomedConcept randomSubtype = getSubTypeConcept(focusConcepts.get(0));
                modifiedExpression.setFocusConcepts(Collections.singleton(randomSubtype));
            }
        }

        Map<NormalFormExpression, Set<Object>> map = new HashMap<NormalFormExpression, Set<Object>>();
        map.put(modifiedExpression, modifications);
        if (logger.isDebugEnabled()) {
            logger.debug("normalFormGenerator.getLongNormalFormExpression(normalFormExpression.getSingletonFocusConcept()).getCompositionalGrammarForm() = " + normalFormGenerator.getLongNormalFormExpression(normalFormExpression.getSingletonFocusConcept()).getCompositionalGrammarForm());
            logger.debug("normalFormExpression.getCompositionalGrammarForm() = " + normalFormExpression.getCompositionalGrammarForm());
            logger.debug("modifiedExpression.getCompositionalGrammarForm() = " + modifiedExpression.getCompositionalGrammarForm());
        }

        return map;
    }

    private Collection<SnomedConcept> returnDeepCopyOfConcepts(Collection<SnomedConcept> concepts){
        Collection<SnomedConcept> copy = new HashSet<SnomedConcept>(concepts.size());
        for(SnomedConcept c: concepts)
        {
            copy.add(TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, c.getConceptID()));
        }

        return copy;
    }

    private SnomedRoleGroupExpression getSubTypeRoleGroupExpression(SnomedRoleGroupExpression roleGroupExpression,
                                                                    Set<Object> modifications) {

        Set<SnomedRelationshipPropertyExpression> oldRelations = new HashSet<SnomedRelationshipPropertyExpression>();
        Set<SnomedRelationshipPropertyExpression> newRelations = new HashSet<SnomedRelationshipPropertyExpression>();
        SnomedRoleGroupExpression srge = new SnomedRoleGroupExpression(roleGroupExpression.getRoleGroup());
        for(Expression e: roleGroupExpression.getChildExpressions())
        {
            srge.addChildExpression(e);
        }
        srge.setParentExpressions(roleGroupExpression.getParentExpressions());
        List<Expression> relationshipExpressions = new ArrayList<Expression>(srge.getChildExpressions());

        // get random number of contained relationship expressions
        int arbitraryNumber = 0;
        if (relationshipExpressions.size() > 1) {
            arbitraryNumber = getRandomPositiveIntLessThan(relationshipExpressions.size() -1);
        }
        // get list of uniqueIds to randomly change
        Collection<Integer> ids = new HashSet<Integer>();
        while(ids.size() < arbitraryNumber)
        {
            ids.add(getRandomPositiveIntLessThan(relationshipExpressions.size()) -1);
        }

        for(Integer i : ids)
        {
            Expression childExpression = relationshipExpressions.get(i);
            if(childExpression instanceof SnomedRelationshipPropertyExpression)
            {
                SnomedRelationshipPropertyExpression propertyExpression = (SnomedRelationshipPropertyExpression) childExpression;
                // add to oldRelations
                oldRelations.add(propertyExpression);
                // get subtype of this propertyExpression
                SnomedRelationshipPropertyExpression modifiedPropertyExpression =
                        getSubtypeRelationshipExpression(propertyExpression, new HashSet<Object>());
                if (logger.isDebugEnabled()) {
                    logger.debug("modifiedPropertyExpression.getCanonicalStringForm() = " + modifiedPropertyExpression.getCanonicalStringForm());
                }
                // add to newRelations
                newRelations.add(modifiedPropertyExpression);
                // add to modifications
                modifications.add(modifiedPropertyExpression);
            }
            else if(childExpression instanceof SnomedRoleGroupExpression)
            {
                // recursive call
                getSubTypeRoleGroupExpression((SnomedRoleGroupExpression) childExpression, modifications);
            }
        }

        // now replace old relationshis in roleGroupExpression with newRelations
        srge.getChildExpressions().removeAll(oldRelations);
        srge.getChildExpressions().addAll(newRelations);

        if (logger.isDebugEnabled()) {
            logger.debug("roleGroupExpression.getCanonicalStringForm() = " + roleGroupExpression.getCanonicalStringForm());
            logger.debug("srge.getCanonicalStringForm() = " + srge.getCanonicalStringForm());
        }
        return srge;
    }

    private SnomedRelationshipPropertyExpression getSubtypeRelationshipExpression(
            SnomedRelationshipPropertyExpression propertyExpression, Set<Object> modifications) {

        SnomedRelationshipPropertyExpression srpe = new SnomedRelationshipPropertyExpression(propertyExpression.getRelationship());
        srpe.setExpression(propertyExpression.getExpression());
        // get value of expression and make a recursive call to the contained expression
        Expression valueExpression = srpe.getExpression();
        if(valueExpression instanceof NormalFormExpression)
        {
            // recursive call
            Map<NormalFormExpression, Set<Object>> innerMap = getModifiedExpression((NormalFormExpression) valueExpression);
            // add set of modified expressions to this object so we can tract them
            List<NormalFormExpression> innerList = new ArrayList<NormalFormExpression>(innerMap.keySet());
            NormalFormExpression innerModifiedExpression = innerList.get(0);
            if (logger.isDebugEnabled()) {
                logger.debug("innerModifiedExpression.getCanonicalStringForm() = " + innerModifiedExpression.getCanonicalStringForm());
            }
            modifications.add(srpe);

            // replace value in contained relationship with modified value
            srpe.getRelationship().setTargetConcept(TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, innerModifiedExpression.getSingletonFocusConcept().getConceptID()));
            // replace value in propertyExpression with modified value
            srpe.setExpression(innerModifiedExpression);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("propertyExpression.getCanonicalStringForm() = " + propertyExpression.getCanonicalStringForm());
            logger.debug("srpe.getCanonicalStringForm() = " + srpe.getCanonicalStringForm());
        }
        return srpe;
    }

    private Set<Object> getModifications(Set<Object> mods){

        Set<Object> modifications = new HashSet<Object>();
        for (Object modification : mods)
        {
            if (modification instanceof Set)
            {
                // recursive call
                modifications.addAll(getModifications((Set) modification));
            }
            else
            {
                modifications.add(modification);
            }
        }

        return modifications;
    }

    private int getRandomPositiveIntLessThan(int upperBound){

        if(upperBound ==1)
        {
            return 1;
        }
        else if (upperBound >1) 
        {
            int ran = 0;
            // get random concept
            while (ran <= 0)
            {
                Random random = new Random();
                ran = random.nextInt(upperBound);
                logger.debug("ran = " + ran);
            }

            return ran;
        } else {
            throw new IllegalArgumentException("Upper bound passed can not be <= 0. Upperbound : "+upperBound);
        }
    }

    public synchronized void setNormalFormGenerator(NormalFormGenerator normalFormGenerator) {
        this.normalFormGenerator = normalFormGenerator;
    }

    public synchronized void setHierarchyProvider(RandomSubtypeHierarchyProvider hierarchyProvider) {
        this.hierarchyProvider = hierarchyProvider;
    }

    public synchronized void setTerminologyConceptDAO(TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologyConceptDAO = terminologyConceptDAO;
    }

    public synchronized boolean isUseNormalFormForRendering() {
        return useNormalFormForRendering;
    }

    public synchronized void setUseNormalFormForRendering(boolean useNormalFormForRendering) {
        this.useNormalFormForRendering = useNormalFormForRendering;
    }
}
