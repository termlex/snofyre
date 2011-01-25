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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.nhs.cfh.dsp.srth.query.model.om.annotation;

import java.net.URI;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import uk.nhs.cfh.dsp.srth.demographics.person.Person;

// TODO: Auto-generated Javadoc
/**
 * An interface that defines metadata applicable to a @QueryExpression.
 * It is loosely based on the Dublin Core Inititiative.
 * Please check the following links for more information:
 * http://dublincore.org/documents/usageguide/elements.shtml
 * http://dublincore.org/documents/dcmi-type-vocabulary/
 * http://dublincore.org/documents/usageguide/qualifiers.shtml
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 12:23:54 PM
 */
public interface MetaData {

    /**
     * The Enum METADATA_TYPE.
     */
    public enum METADATA_TYPE {

        /** The COLLECTION. */
        COLLECTION,
        /** The DATASET. */
        DATASET,
        /** The EVENT. */
        EVENT,
        /** The IMAGE. */
        IMAGE,

        /** The INTERACTIV e_ resource. */
        INTERACTIVE_RESOURCE,

        /** The MOVIN g_ image. */
        MOVING_IMAGE,

        /** The PHYSICA l_ object. */
        PHYSICAL_OBJECT,

        /** The SERVICE. */
        SERVICE,

        /** The SOFTWARE. */
        SOFTWARE,

        /** The SOUND. */
        SOUND,

        /** The STIL l_ image. */
        STILL_IMAGE,

        /** The TEXT. */
        TEXT
    };

    /**
     * The Enum ACCRUAL_METHOD.
     */
    public enum ACCRUAL_METHOD {
        /** The CONSULTATION. */
        CONSULTATION,
        /** The MANDATED. */
        MANDATED,
        /** The REQUESTED. */
        REQUESTED};

    /**
     * The Enum ACCRUAL_PERIODICITY.
     */
    public enum ACCRUAL_PERIODICITY {

        /** The MONTHLY. */
        MONTHLY,

        /** The ANNUAL. */
        ANNUAL,

        /** The B i_ annual. */
        BI_ANNUAL,

        /** The WEEKLY. */
        WEEKLY,

        /** The FORTNIGHTLY. */
        FORTNIGHTLY,

        /** The IRREGULAR. */
        IRREGULAR};

    /**
     * The Enum ACCRUAL_POLICY.
     */
    public enum ACCRUAL_POLICY {

        /** The OPEN. */
        OPEN,

        /** The CLOSED. */
        CLOSED};

    /** The Constant DC_NAMESPACE. */
    public static final java.lang.String DC_NAMESPACE = "http://purl.org/dc/elements/1.1/";

    /** The Constant DCTERMS_NAMESPACE. */
    public static final java.lang.String DCTERMS_NAMESPACE = "http://purl.org/dc/terms/";

    /**
     * Adds the contributor.
     *
     * @param contributor the contributor
     */
    void addContributor(Person contributor);

    /**
     * Adds the creator.
     *
     * @param creator the creator
     */
    void addCreator(Person creator);

    /**
     * Adds the publisher.
     *
     * @param publisher the publisher
     */
    void addPublisher(Person publisher);

    /**
     * Adds the right holder.
     *
     * @param rightHolder the right holder
     */
    void addRightHolder(Person rightHolder);

    /**
     * Gets the accepted date.
     *
     * @return the accepted date
     */
    Date getAcceptedDate();

    /**
     * Gets the accrual methods.
     *
     * @return the accrual methods
     */
    Set<ACCRUAL_METHOD> getAccrualMethods();

    /**
     * Gets the accrual periodicities.
     *
     * @return the accrual periodicities
     */
    Set<ACCRUAL_PERIODICITY> getAccrualPeriodicities();

    /**
     * Gets the accrual policies.
     *
     * @return the accrual policies
     */
    Set<ACCRUAL_POLICY> getAccrualPolicies();

    /**
     * Gets the audience education levels.
     *
     * @return the audience education levels
     */
    Set<String> getAudienceEducationLevels();

    /**
     * Gets the audience mediators.
     *
     * @return the audience mediators
     */
    Set<String> getAudienceMediators();

    /**
     * Gets the available date.
     *
     * @return the available date
     */
    Date getAvailableDate();

    /**
     * Gets the contributors.
     *
     * @return the contributors
     */
    Set<Person> getContributors();

    /**
     * Gets the copyrighted date.
     *
     * @return the copyrighted date
     */
    Date getCopyrightedDate();

    /**
     * Gets the created date.
     *
     * @return the created date
     */
    Date getCreatedDate();

    /**
     * Gets the creators.
     *
     * @return the creators
     */
    Set<Person> getCreators();

    /**
     * Gets the description.
     *
     * @return the description
     */
    String getDescription();

    /**
     * Gets the format.
     *
     * @return the format
     */
    String getFormat();

    /**
     * Gets the identifier.
     *
     * @return the identifier
     */
    URI getIdentifier();

    /**
     * Gets the instructional methods.
     *
     * @return the instructional methods
     */
    Set<String> getInstructionalMethods();

    /**
     * Gets the issued date.
     *
     * @return the issued date
     */
    Date getIssuedDate();

    /**
     * Gets the language.
     *
     * @return the language
     */
    String getLanguage();

    /**
     * Gets the licenses.
     *
     * @return the licenses
     */
    Set<URI> getLicenses();

    /**
     * Gets the modified dates.
     *
     * @return the modified dates
     */
    Set<Date> getModifiedDates();

    /**
     * Gets the provenances.
     *
     * @return the provenances
     */
    Set<String> getProvenances();

    /**
     * Gets the publishers.
     *
     * @return the publishers
     */
    Set<Person> getPublishers();

    /**
     * Gets the replaced date.
     *
     * @return the replaced date
     */
    Date getReplacedDate();

    /**
     * Gets the retired date.
     *
     * @return the retired date
     */
    Date getRetiredDate();

    /**
     * Gets the rights holders.
     *
     * @return the rights holders
     */
    Set<Person> getRightsHolders();

    /**
     * Gets the sources.
     *
     * @return the sources
     */
    Set<URI> getSources();

    /**
     * Gets the subject.
     *
     * @return the subject
     */
    String getSubject();

    /**
     * Gets the submitted date.
     *
     * @return the submitted date
     */
    Date getSubmittedDate();

    /**
     * Gets the temporal coverage.
     *
     * @return the temporal coverage
     */
    Collection<Calendar> getTemporalCoverage();

    /**
     * Gets the title.
     *
     * @return the title
     */
    String getTitle();

    /**
     * Gets the type.
     *
     * @return the type
     */
    METADATA_TYPE getType();

    /**
     * Gets the valid date.
     *
     * @return the valid date
     */
    Date getValidDate();

    /**
     * Removes the contributor.
     *
     * @param contributor the contributor
     */
    void removeContributor(Person contributor);

    /**
     * Removes the creator.
     *
     * @param creator the creator
     */
    void removeCreator(Person creator);

    /**
     * Removes the publisher.
     *
     * @param publisher the publisher
     */
    void removePublisher(Person publisher);

    /**
     * Sets the accepted date.
     *
     * @param acceptedDate the new accepted date
     */
    void setAcceptedDate(Date acceptedDate);

    /**
     * Sets the accrual methods.
     *
     * @param accrualMethods the new accrual methods
     */
    void setAccrualMethods(Set<ACCRUAL_METHOD> accrualMethods);

    /**
     * Sets the accrual periodicities.
     *
     * @param accrualPeriodicities the new accrual periodicities
     */
    void setAccrualPeriodicities(Set<ACCRUAL_PERIODICITY> accrualPeriodicities);

    /**
     * Sets the accrual policies.
     *
     * @param accrualPolicies the new accrual policies
     */
    void setAccrualPolicies(Set<ACCRUAL_POLICY> accrualPolicies);

    /**
     * Sets the audience education levels.
     *
     * @param audienceEducationLevels the new audience education levels
     */
    void setAudienceEducationLevels(Set<String> audienceEducationLevels);

    /**
     * Sets the audience mediators.
     *
     * @param audienceMediators the new audience mediators
     */
    void setAudienceMediators(Set<String> audienceMediators);

    /**
     * Sets the available date.
     *
     * @param availableDate the new available date
     */
    void setAvailableDate(Date availableDate);

    /**
     * Sets the contributors.
     *
     * @param contributors the new contributors
     */
    void setContributors(Set<Person> contributors);

    /**
     * Sets the copyrighted date.
     *
     * @param copyrightedDate the new copyrighted date
     */
    void setCopyrightedDate(Date copyrightedDate);

    /**
     * Sets the created date.
     *
     * @param createdDate the new created date
     */
    void setCreatedDate(Date createdDate);

    /**
     * Sets the creators.
     *
     * @param creators the new creators
     */
    void setCreators(Set<Person> creators);

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    void setDescription(String description);

    /**
     * Sets the format.
     *
     * @param format the new format
     */
    void setFormat(String format);

    /**
     * Sets the identifier.
     *
     * @param identifier the new identifier
     */
    void setIdentifier(URI identifier);

    /**
     * Sets the instructional methods.
     *
     * @param instructionalMethods the new instructional methods
     */
    void setInstructionalMethods(Set<String> instructionalMethods);

    /**
     * Sets the issued date.
     *
     * @param issuedDate the new issued date
     */
    void setIssuedDate(Date issuedDate);

    /**
     * Sets the language.
     *
     * @param language the new language
     */
    void setLanguage(String language);

    /**
     * Sets the licenses.
     *
     * @param licenses the new licenses
     */
    void setLicenses(Set<URI> licenses);

    /**
     * Sets the modified dates.
     *
     * @param modifiedDates the new modified dates
     */
    void setModifiedDates(Set<Date> modifiedDates);

    /**
     * Sets the provenances.
     *
     * @param provenances the new provenances
     */
    void setProvenances(Set<String> provenances);

    /**
     * Sets the publishers.
     *
     * @param publishers the new publishers
     */
    void setPublishers(Set<Person> publishers);

    /**
     * Sets the replaced date.
     *
     * @param replacedDate the new replaced date
     */
    void setReplacedDate(Date replacedDate);

    /**
     * Sets the retired date.
     *
     * @param retiredDate the new retired date
     */
    void setRetiredDate(Date retiredDate);

    /**
     * Sets the rights holders.
     *
     * @param rightsHolders the new rights holders
     */
    void setRightsHolders(Set<Person> rightsHolders);

    /**
     * Sets the sources.
     *
     * @param sources the new sources
     */
    void setSources(Set<URI> sources);

    /**
     * Sets the subject.
     *
     * @param subject the new subject
     */
    void setSubject(String subject);

    /**
     * Sets the submitted date.
     *
     * @param submittedDate the new submitted date
     */
    void setSubmittedDate(Date submittedDate);

    /**
     * Sets the temporal coverage.
     *
     * @param temporalCoverage the new temporal coverage
     */
    void setTemporalCoverage(Collection<Calendar> temporalCoverage);

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    void setTitle(String title);

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    void setType(METADATA_TYPE type);

    /**
     * Sets the valid date.
     *
     * @param validDate the new valid date
     */
    void setValidDate(Date validDate);
}
