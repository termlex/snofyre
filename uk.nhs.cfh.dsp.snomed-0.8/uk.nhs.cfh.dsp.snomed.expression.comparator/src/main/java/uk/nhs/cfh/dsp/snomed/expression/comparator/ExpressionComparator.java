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
package uk.nhs.cfh.dsp.snomed.expression.comparator;

import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a service that compares.
 *
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.Expression}s or
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept}s
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 20, 2009 at 8:53:38 PM
 */
public interface ExpressionComparator {

    /**
     * Gets the subsumption relation.
     *
     * @param predicateExpression the predicate expression
     * @param candidateExpression the candidate expression
     * @return the subsumption relation
     */
    Subsumption_Relation getSubsumptionRelation(Expression predicateExpression, Expression candidateExpression);

    /**
     * Gets the subsumption relation.
     *
     * @param lhsConcept the lhs concept
     * @param rhsConcept the rhs concept
     * @return the subsumption relation
     */
    Subsumption_Relation getSubsumptionRelation(SnomedConcept lhsConcept, SnomedConcept rhsConcept);

    /**
     * Gets the subsumption relation.
     *
     * @param rhsConceptId the rhs concept id
     * @param lhsConceptId the lhs concept id
     * @return the subsumption relation
     */
    Subsumption_Relation getSubsumptionRelation(String rhsConceptId, String lhsConceptId);

    /**
     * Checks if is subsumed by.
     *
     * @param lhsConcept the lhs concept
     * @param rhsConcept the rhs concept
     * @return true, if is subsumed by
     */
    boolean isSubsumedBy(SnomedConcept lhsConcept, SnomedConcept rhsConcept);

    /**
     * Checks if is subsumed by.
     *
     * @param lhsConceptId the lhs concept id
     * @param rhsConceptId the rhs concept id
     * @return true, if is subsumed by
     */
    boolean isSubsumedBy(String lhsConceptId, String rhsConceptId);

    /**
     * Checks if is subsumed by.
     *
     * @param candidateExpression the candidate expression
     * @param predicateExpression the predicate expression
     * @return true, if is subsumed by
     */
    boolean isSubsumedBy(Expression candidateExpression, Expression predicateExpression);

    /** The Subsumption_ relation. */
    public enum Subsumption_Relation{SAME, SUBSUMED_BY, SUBSUMES, NO_RELATION}
}
