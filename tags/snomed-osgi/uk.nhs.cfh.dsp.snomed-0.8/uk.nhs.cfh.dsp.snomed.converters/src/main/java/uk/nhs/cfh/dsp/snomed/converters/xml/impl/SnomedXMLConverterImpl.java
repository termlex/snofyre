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
package uk.nhs.cfh.dsp.snomed.converters.xml.impl;

import org.jdom.Element;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter}.
 *
 * @deprecated Use {@link uk.nhs.cfh.dsp.snomed.converters.xml.impl.XSBasedSnomedXMLConverter} instead
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 18, 2009 at 11:33:30 AM
 */
public class SnomedXMLConverterImpl extends AbstractSnomedXMLCoverter{

    /**
     * Instantiates a new snomed xml converter impl.
     */
    public SnomedXMLConverterImpl() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.impl.AbstractSnomedXMLCoverter#getElementForConcept(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    @Override
    public Element getElementForConcept(SnomedConcept concept) {
        return super.getElementForConcept(concept); 
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.impl.AbstractSnomedXMLCoverter#getElementForDescription(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription)
     */
    @Override
    public Element getElementForDescription(SnomedDescription description) {
        return super.getElementForDescription(description);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.impl.AbstractSnomedXMLCoverter#getElementForRelationship(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship)
     */
    @Override
    public Element getElementForRelationship(SnomedRelationship relationship) {
        return super.getElementForRelationship(relationship);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.impl.AbstractSnomedXMLCoverter#getElementForRoleGroup(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup)
     */
    @Override
    public Element getElementForRoleGroup(SnomedRoleGroup roleGroup) {
        return super.getElementForRoleGroup(roleGroup);
    }
}
