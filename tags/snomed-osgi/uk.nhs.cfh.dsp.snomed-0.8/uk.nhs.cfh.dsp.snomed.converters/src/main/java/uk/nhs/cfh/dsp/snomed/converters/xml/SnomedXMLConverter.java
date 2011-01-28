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
package uk.nhs.cfh.dsp.snomed.converters.xml;

import org.jdom.Element;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a converter that generates XML {@link org.jdom.Element} for a given
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedConceptImpl}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 18, 2009 at 11:32:00 AM
 */
public interface SnomedXMLConverter {


    /**
     * Gets the element for concept.
     *
     * @param concept the concept
     * @return the element for concept
     */
    Element getElementForConcept(SnomedConcept concept);

    /**
     * Gets the element for description.
     *
     * @param description the description
     * @return the element for description
     */
    Element getElementForDescription(SnomedDescription description);

    /**
     * Gets the element for relationship.
     *
     * @param relationship the relationship
     * @return the element for relationship
     */
    Element getElementForRelationship(SnomedRelationship relationship);

    /**
     * Gets the element for role group.
     *
     * @param roleGroup the role group
     * @return the element for role group
     */
    Element getElementForRoleGroup(SnomedRoleGroup roleGroup);

    /**
     * Gets the concept from xml.
     *
     * @param element the element
     * @return the concept from xml
     */
    SnomedConcept getConceptFromXML(Element element);
}
