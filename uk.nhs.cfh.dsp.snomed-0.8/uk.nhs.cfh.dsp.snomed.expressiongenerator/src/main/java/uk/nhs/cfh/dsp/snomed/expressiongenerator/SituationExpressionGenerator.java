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
package uk.nhs.cfh.dsp.snomed.expressiongenerator;

import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.FindingSituationExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.ProcedureSituationExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for an object that returns a {@link uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression}
 * from a given {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 10, 2010 at 11:04:54 AM
 */
public interface SituationExpressionGenerator {

    /**
     * Gets the default context situation expression.
     *
     * @param concept the concept
     * @return the default context situation expression
     */
    SituationExpression getDefaultContextSituationExpression(SnomedConcept concept);

    /**
     * Gets the assertable situation expression.
     *
     * @param concept the concept
     * @return the assertable situation expression
     */
    SituationExpression getAssertableSituationExpression(SnomedConcept concept);

    /**
     * Gets the assertable planned procedure expression.
     *
     * @param concept the concept
     * @return the assertable planned procedure expression
     */
    ProcedureSituationExpression getAssertablePlannedProcedureExpression(SnomedConcept concept);

    /**
     * Gets the assertable procedure expression.
     *
     * @param concept the concept
     * @return the assertable procedure expression
     */
    ProcedureSituationExpression getAssertableProcedureExpression(SnomedConcept concept);

    /**
     * Gets the assertable finding expression.
     *
     * @param concept the concept
     * @return the assertable finding expression
     */
    FindingSituationExpression getAssertableFindingExpression(SnomedConcept concept);

    /**
     * Gets the assertable goal finding expression.
     *
     * @param concept the concept
     * @return the assertable goal finding expression
     */
    FindingSituationExpression getAssertableGoalFindingExpression(SnomedConcept concept);

    /**
     * Gets the assertable past finding expression.
     *
     * @param concept the concept
     * @return the assertable past finding expression
     */
    FindingSituationExpression getAssertablePastFindingExpression(SnomedConcept concept);

    /**
     * Gets the assertable family history finding expression.
     *
     * @param concept the concept
     * @return the assertable family history finding expression
     */
    FindingSituationExpression getAssertableFamilyHistoryFindingExpression(SnomedConcept concept);

    /**
     * Gets the procedure with default context.
     *
     * @param concept the concept
     * @return the procedure with default context
     */
    ProcedureSituationExpression getProcedureWithDefaultContext(SnomedConcept concept);

    /**
     * Gets the finding with default context.
     *
     * @param concept the concept
     * @return the finding with default context
     */
    FindingSituationExpression getFindingWithDefaultContext(SnomedConcept concept);

    /**
     * Gets the situation with default context.
     *
     * @param closeToUserExpression the close to user expression
     * @return the situation with default context
     */
    CloseToUserExpression getSituationWithDefaultContext(CloseToUserExpression closeToUserExpression);

    /**
     * Gets the situation with assertable context.
     *
     * @param closeToUserExpression the close to user expression
     * @return the situation with assertable context
     */
    CloseToUserExpression getSituationWithAssertableContext(CloseToUserExpression closeToUserExpression);
}
