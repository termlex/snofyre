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
package uk.nhs.cfh.dsp.snomed.objectmodel;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for an object that represents a SNOMED CT Description
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 20, 2009 at 8:15:12 PM.
 */
public interface SnomedDescription extends SnomedComponent
{

    /**
     * Gets the id.
     *
     * @return the id
     */
    String getId();

    /**
     * Sets the id.
     *
     * @param descriptionID the new id
     */
    void setId(String descriptionID);

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
     * Gets the term.
     *
     * @return the term
     */
    String getTerm();

    /**
     * Sets the term.
     *
     * @param term the new term
     */
    void setTerm(String term);

    /**
     * Checks if is initial cap status.
     *
     * @return true, if is initial cap status
     */
    boolean isInitialCapStatus();

    /**
     * Sets the initial cap status.
     *
     * @param initialCapStatus the new initial cap status
     */
    void setInitialCapStatus(boolean initialCapStatus);

    /**
     * Gets the type.
     *
     * @return the type
     */
    String getType();

    /**
     * Sets the type.
     *
     * @param descriptionType the new type
     */
    void setType(String descriptionType);

    /**
     * Gets the language.
     *
     * @return the language
     */
    String getLanguage();

    /**
     * Sets the language.
     *
     * @param language the new language
     */
    void setLanguage(String language);

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
}
