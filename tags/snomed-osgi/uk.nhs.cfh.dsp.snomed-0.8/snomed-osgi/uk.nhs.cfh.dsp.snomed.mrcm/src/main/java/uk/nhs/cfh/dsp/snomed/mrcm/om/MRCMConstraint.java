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
package uk.nhs.cfh.dsp.snomed.mrcm.om;

// TODO: Auto-generated Javadoc
/**
 * An interface representation of a constraint in the MRCM model.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 16, 2010 at 10:27:55 AM
 */
public interface MRCMConstraint {

    /**
     * Gets the source concept type.
     *
     * @return the source concept type
     */
    String getSourceConceptType();

    /**
     * Sets the source concept type.
     *
     * @param sourceConceptType the new source concept type
     */
    void setSourceConceptType(String sourceConceptType);

    /**
     * Gets the min cardinality.
     *
     * @return the min cardinality
     */
    int getMinCardinality();

    /**
     * Sets the min cardinality.
     *
     * @param minCardinality the new min cardinality
     */
    void setMinCardinality(int minCardinality);

    /**
     * Gets the max cardinality.
     *
     * @return the max cardinality
     */
    int getMaxCardinality();

    /**
     * Sets the max cardinality.
     *
     * @param maxCardinality the new max cardinality
     */
    void setMaxCardinality(int maxCardinality);

    /**
     * Gets the source id.
     *
     * @return the source id
     */
    String getSourceId();

    /**
     * Sets the source id.
     *
     * @param sourceId the new source id
     */
    void setSourceId(String sourceId);

    /**
     * Gets the source name.
     *
     * @return the source name
     */
    String getSourceName();

    /**
     * Sets the source name.
     *
     * @param sourceName the new source name
     */
    void setSourceName(String sourceName);

    /**
     * Gets the attribute id.
     *
     * @return the attribute id
     */
    String getAttributeId();

    /**
     * Sets the attribute id.
     *
     * @param attributeId the new attribute id
     */
    void setAttributeId(String attributeId);

    /**
     * Gets the attribute name.
     *
     * @return the attribute name
     */
    String getAttributeName();

    /**
     * Sets the attribute name.
     *
     * @param attributeName the new attribute name
     */
    void setAttributeName(String attributeName);

    /**
     * Gets the value id.
     *
     * @return the value id
     */
    String getValueId();

    /**
     * Sets the value id.
     *
     * @param valueId the new value id
     */
    void setValueId(String valueId);

    /**
     * Gets the value name.
     *
     * @return the value name
     */
    String getValueName();

    /**
     * Sets the value name.
     *
     * @param valueName the new value name
     */
    void setValueName(String valueName);
}
