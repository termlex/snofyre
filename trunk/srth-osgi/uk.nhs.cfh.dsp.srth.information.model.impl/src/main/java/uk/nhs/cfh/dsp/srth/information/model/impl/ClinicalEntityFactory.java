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
package uk.nhs.cfh.dsp.srth.information.model.impl;

import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.*;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a factory implementation that generates
 * clinical entities.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 27, 2009 at 11:52:49 AM
 */
public interface ClinicalEntityFactory {

    /**
     * Gets the clinical feature entity.
     *
     * @return the clinical feature entity
     */
    ClinicalFeatureEntity getClinicalFeatureEntity();

    /**
     * Gets the clinical finding entity.
     *
     * @return the clinical finding entity
     */
    ClinicalFindingEntity getClinicalFindingEntity();

    /**
     * Gets the investigation entity.
     *
     * @return the investigation entity
     */
    InvestigationEntity getInvestigationEntity();

    /**
     * Gets the intervention entity.
     *
     * @return the intervention entity
     */
    InterventionEntity getInterventionEntity();

    /**
     * Gets the medication entity.
     *
     * @return the medication entity
     */
    MedicationEntity getMedicationEntity();

    /**
     * Gets the anatomical location entity.
     *
     * @return the anatomical location entity
     */
    AnatomicalLocationEntity getAnatomicalLocationEntity();

    /**
     * Gets the entity.
     *
     * @param type the type
     *
     * @return the entity
     */
    ClinicalEntity getEntity(ClinicalEntity.Type type);

    /**
     * Gets the clinical feature entity.
     * 
     * @param expression the expression
     * @param value the value
     *
     * @return the clinical feature entity
     */
    ClinicalFeatureEntity getClinicalFeatureEntity(Expression expression, double value);

    /**
     * Gets the clinical finding entity.
     *
     * @param expression the expression
     * @return the clinical finding entity
     */
    ClinicalFindingEntity getClinicalFindingEntity(Expression expression);

    /**
     * Gets the investigation entity.
     *
     * @param expression the expression
     * @return the investigation entity
     */
    InvestigationEntity getInvestigationEntity(Expression expression);

    /**
     * Gets the intervention entity.
     *
     * @param expression the expression
     * @return the intervention entity
     */
    InterventionEntity getInterventionEntity(Expression expression);

    /**
     * Gets the medication entity.
     *
     * @param expression the expression
     * @param dose the dose
     * @return the medication entity
     */
    MedicationEntity getMedicationEntity(Expression expression, double dose);

    /**
     * Gets the anatomical location entity.
     *
     * @param expression the expression
     * @return the anatomical location entity
     */
    AnatomicalLocationEntity getAnatomicalLocationEntity(Expression expression);

    /**
     * Gets the expression.
     *
     * @param expression the expression
     * @return the expression
     */
    ClinicalExpression getExpression(Expression expression);
}