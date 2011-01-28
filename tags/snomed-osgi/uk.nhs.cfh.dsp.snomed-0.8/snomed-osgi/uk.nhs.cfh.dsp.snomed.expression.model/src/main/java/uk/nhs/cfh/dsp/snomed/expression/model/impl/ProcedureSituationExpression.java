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

import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;

import java.util.ArrayList;
import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 10, 2010 at 10:33:12 AM.
 */
public class ProcedureSituationExpression extends AbstractSituationExpression {

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.impl.AbstractSituationExpression#getPermittedTypes()
     */
    @Override
    public Collection<ConceptType> getPermittedTypes(){
        Collection<ConceptType> types = new ArrayList<ConceptType>();
        types.add(ConceptType.EVALUATION_PROCEDURE);
        types.add(ConceptType.PROCEDURE);
        types.add(ConceptType.SURGICAL_PROCEDURE);
        types.add(ConceptType.PHARMACEUTICAL_OR_BIOLOGICAL_PRODUCT);

        return types;
    }
}
