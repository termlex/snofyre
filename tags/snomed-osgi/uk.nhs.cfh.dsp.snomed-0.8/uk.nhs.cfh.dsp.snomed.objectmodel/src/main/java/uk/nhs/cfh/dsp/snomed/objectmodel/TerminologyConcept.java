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
/*
 * 
 */
package uk.nhs.cfh.dsp.snomed.objectmodel;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * The interface specification for a concept in a terminology.
 *
 * <br>Version : @#VersionNumber#@
 * <br> Written by @author Jay Kola
 * <br> Created on December 15, 2008 at 6:40 PM
 */
public interface TerminologyConcept extends SnomedComponent{

    /**
     * Gets the concept id.
     *
     * @return the concept id
     */
    String getConceptID();

    /**
     * Sets the concept id.
     *
     * @param conceptID the new concept id
     */
    void setConceptID(String conceptID);

    /**
     * Gets the synonyms.
     *
     * @return the synonyms
     */
    Collection<String> getSynonyms();

    /**
     * Sets the synonyms.
     *
     * @param synonyms the new synonyms
     */
    void setSynonyms(Collection<String> synonyms);

    /**
     * Gets the source.
     *
     * @return the source
     */
    String getSource();

    /**
     * Sets the source.
     *
     * @param source the new source
     */
    void setSource(String source);

    /**
     * Gets the preferred label.
     *
     * @return the preferred label
     */
    String getPreferredLabel();

    /**
     * Sets the preferred label.
     *
     * @param preferredLabel the new preferred label
     */
    void setPreferredLabel(String preferredLabel);

    /**
     * Adds the synonym.
     *
     * @param synonym the synonym
     */
    void addSynonym(String synonym);

    /**
     * Removes the synonym.
     *
     * @param synonym the synonym
     */
    void removeSynonym(String synonym);

    /**
     * Checks if is active concept.
     *
     * @return true, if is active concept
     */
    boolean isActiveConcept();

}