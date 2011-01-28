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
package uk.nhs.cfh.dsp.snomed.expression.model.impl;

import uk.nhs.cfh.dsp.snomed.expression.model.Expression;

import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of an {@link uk.nhs.cfh.dsp.snomed.expression.model.Expression}. This class
 * is used to generate {@link uk.nhs.cfh.dsp.snomed.expression.model.Expression}s containing
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedConceptImpl}s.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 16, 2009 at 3:57:08 PM
 */
public class TerminologyExpression extends AbstractExpressionImpl implements Expression {

    /**
     * Instantiates a new terminology expression.
     */
    public TerminologyExpression() {
        this(UUID.randomUUID());
    }

    /**
     * Instantiates a new terminology expression.
     *
     * @param uuid the uuid
     */
    public TerminologyExpression(UUID uuid) {
        super(uuid);
    }
}