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

package uk.nhs.cfh.dsp.srth.query.model.om.annotation.impl;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.search.annotations.*;
import uk.nhs.cfh.dsp.srth.demographics.person.Person;
import uk.nhs.cfh.dsp.srth.demographics.person.impl.PersonImpl;
import uk.nhs.cfh.dsp.srth.query.model.om.annotation.MetaData;

import javax.persistence.*;
import java.net.URI;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.srth.query.model.om.annotation.MetaData}
 * The types and date variants are based on the qualifiers published on
 * http://dublincore.org/documents/usageguide/qualifiers.shtml
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 12:24:41 PM
 */

@MappedSuperclass
@Embeddable
public abstract class AbstractMetaData implements MetaData{

    /** The title. */
    private String title;
    
    /** The initial size. */
    private static int initialSize = 5;

    /** The creators. */
    private Set<Person> creators = new HashSet<Person>(initialSize);

    /** The subject. */
    private String subject;

    /** The description. */
    private String description;

    /** The publishers. */
    private Set<Person> publishers = new HashSet<Person>(initialSize);

    /** The contributors. */
    private Set<Person> contributors = new HashSet<Person>(initialSize);

    /** The created date. */
    private Date createdDate = Calendar.getInstance().getTime();

    /** The available date. */
    private Date availableDate;

    /** The valid date. */
    private Date validDate;

    /** The issued date. */
    private Date issuedDate;

    /** The modified dates. */
    private Set<Date> modifiedDates = new HashSet<Date>(initialSize);

    /** The accepted date. */
    private Date acceptedDate;

    /** The copyrighted date. */
    private Date copyrightedDate;

    /** The submitted date. */
    private Date submittedDate;

    /** The replaced date. */
    private Date replacedDate;

    /** The retired date. */
    private Date retiredDate;

    /** The type. */
    private METADATA_TYPE type;

    /** The format. */
    private String format;

    /** The identifier. */
    private URI identifier;

    /** The sources. */
    private Set<URI> sources = new HashSet<URI>(initialSize);

    /** The language. */
    private String language;

    /** The temporal coverage. */
    private Collection<Calendar> temporalCoverage = new HashSet<Calendar>(initialSize);

    /** The licenses. */
    private Set<URI> licenses = new HashSet<URI>(initialSize);

    /** The audience education levels. */
    private Set<String> audienceEducationLevels = new HashSet<String>(initialSize);

    /** The audience mediators. */
    private Set<String> audienceMediators = new HashSet<String>(initialSize);

    /** The provenances. */
    private Set<String>  provenances = new HashSet<String>(initialSize);

    /** The rights holders. */
    private Set<Person> rightsHolders = new HashSet<Person>(initialSize);

    /** The instructional methods. */
    private Set<String> instructionalMethods = new HashSet<String>(initialSize);

    /** The accrual methods. */
    private Set<ACCRUAL_METHOD> accrualMethods = new HashSet<ACCRUAL_METHOD>(initialSize);

    /** The accrual periodicities. */
    private Set<ACCRUAL_PERIODICITY> accrualPeriodicities = new HashSet<ACCRUAL_PERIODICITY>(initialSize);

    /** The accrual policies. */
    private Set<ACCRUAL_POLICY> accrualPolicies = new HashSet<ACCRUAL_POLICY>(initialSize);

    /**
     * Instantiates a new abstract meta data.
     */
    protected AbstractMetaData() {

    }

    /**
     * Adds the creator.
     *
     * @param creator the creator
     */
    @Transient
    public void addCreator(Person creator){
        if(creator != null)
        {
            getCreators().add(creator);
        }
    }

    /**
     * Removes the creator.
     *
     * @param creator the creator
     */
    @Transient
    public void removeCreator(Person creator){
        if(creator != null)
        {
            getCreators().remove(creator);
        }
    }

    /**
     * Adds the contributor.
     *
     * @param contributor the contributor
     */
    @Transient
    public void addContributor(Person contributor){
        if(contributor != null)
        {
            getContributors().add(contributor);
        }
    }

    /**
     * Removes the contributor.
     *
     * @param contributor the contributor
     */
    @Transient
    public void removeContributor(Person contributor){
        if(contributor != null)
        {
            getContributors().remove(contributor);
        }
    }

    /**
     * Adds the publisher.
     *
     * @param publisher the publisher
     */
    @Transient
    public void addPublisher(Person publisher){
        if(publisher != null)
        {
            getPublishers().add(publisher);
        }
    }

    /**
     * Removes the publisher.
     *
     * @param publisher the publisher
     */
    @Transient
    public void removePublisher(Person publisher){
        if(publisher != null)
        {
            getPublishers().remove(publisher);
        }
    }

    /**
     * Adds the right holder.
     *
     * @param rightHolder the right holder
     */
    @Transient
    public void addRightHolder(Person rightHolder){
        if(rightHolder != null)
        {
            getRightsHolders().add(rightHolder);
        }
    }

    /**
     * Gets the accepted date.
     *
     * @return the accepted date
     */
    @Column(name = "ACCEPTED_DATE")
    @Field(index = Index.UN_TOKENIZED, store = Store.COMPRESS)
    @DateBridge(resolution = Resolution.DAY)
    public Date getAcceptedDate() {
        return acceptedDate;
    }

    /**
     * Sets the accepted date.
     *
     * @param acceptedDate the new accepted date
     */
    public void setAcceptedDate(Date acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    /**
     * Gets the accrual methods.
     *
     * @return the accrual methods
     */
    @Transient
    public Set<ACCRUAL_METHOD> getAccrualMethods() {
        return accrualMethods;
    }

    /**
     * Sets the accrual methods.
     *
     * @param accrualMethods the new accrual methods
     */
    public void setAccrualMethods(Set<ACCRUAL_METHOD> accrualMethods) {
        this.accrualMethods = accrualMethods;
    }

    /**
     * Gets the accrual periodicities.
     *
     * @return the accrual periodicities
     */
    @Transient
    public Set<ACCRUAL_PERIODICITY> getAccrualPeriodicities() {
        return accrualPeriodicities;
    }

    /**
     * Sets the accrual periodicities.
     *
     * @param accrualPeriodicities the new accrual periodicities
     */
    public void setAccrualPeriodicities(Set<ACCRUAL_PERIODICITY> accrualPeriodicities) {
        this.accrualPeriodicities = accrualPeriodicities;
    }

    /**
     * Gets the accrual policies.
     *
     * @return the accrual policies
     */
    @Transient
    public Set<ACCRUAL_POLICY> getAccrualPolicies() {
        return accrualPolicies;
    }

    /**
     * Sets the accrual policies.
     *
     * @param accrualPolicies the new accrual policies
     */
    public void setAccrualPolicies(Set<ACCRUAL_POLICY> accrualPolicies) {
        this.accrualPolicies = accrualPolicies;
    }

    /**
     * Gets the audience education levels.
     *
     * @return the audience education levels
     */
    @CollectionOfElements(fetch = FetchType.EAGER, targetElement = String.class)
    public Set<String> getAudienceEducationLevels() {
        return audienceEducationLevels;
    }

    /**
     * Sets the audience education levels.
     *
     * @param audienceEducationLevels the new audience education levels
     */
    public void setAudienceEducationLevels(Set<String> audienceEducationLevels) {
        this.audienceEducationLevels = audienceEducationLevels;
    }

    /**
     * Gets the audience mediators.
     *
     * @return the audience mediators
     */
    @CollectionOfElements(fetch = FetchType.EAGER, targetElement = String.class)
    public Set<String> getAudienceMediators() {
        return audienceMediators;
    }

    /**
     * Sets the audience mediators.
     *
     * @param audienceMediators the new audience mediators
     */
    public void setAudienceMediators(Set<String> audienceMediators) {
        this.audienceMediators = audienceMediators;
    }

    /**
     * Gets the available date.
     *
     * @return the available date
     */
    @Column(name = "AVAILABLE_DATE")
    @Field(index = Index.UN_TOKENIZED, store = Store.COMPRESS)
    @DateBridge(resolution = Resolution.DAY)
    public Date getAvailableDate() {
        return availableDate;
    }

    /**
     * Sets the available date.
     *
     * @param availableDate the new available date
     */
    public void setAvailableDate(Date availableDate) {
        this.availableDate = availableDate;
    }


    /**
     * Gets the copyrighted date.
     *
     * @return the copyrighted date
     */
    @Column(name = "COPYRIGHTED_DATE")
    @Field(index = Index.UN_TOKENIZED, store = Store.COMPRESS)
    @DateBridge(resolution = Resolution.DAY)
    public Date getCopyrightedDate() {
        return copyrightedDate;
    }

    /**
     * Sets the copyrighted date.
     *
     * @param copyrightedDate the new copyrighted date
     */
    public void setCopyrightedDate(Date copyrightedDate) {
        this.copyrightedDate = copyrightedDate;
    }

    /**
     * Gets the created date.
     *
     * @return the created date
     */
    @Column(name = "CREATED_DATE")
    @Field(index = Index.UN_TOKENIZED, store = Store.COMPRESS)
    @DateBridge(resolution = Resolution.DAY)
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the created date.
     *
     * @param createdDate the new created date
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    @Column(name = "DESCRIPTION")
    @Field(index = Index.TOKENIZED, store = Store.NO)
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    @Column(name = "DESCRIPTION")
    @Field(index = Index.TOKENIZED, store = Store.NO)
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the format.
     *
     * @return the format
     */
    @Column(name = "FORMAT")
    @Field(index = Index.TOKENIZED, store = Store.NO)
    public String getFormat() {
        return format;
    }

    /**
     * Sets the format.
     *
     * @param format the new format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Gets the identifier.
     *
     * @return the identifier
     */
    @Transient
    public URI getIdentifier() {
        return identifier;
    }

    /**
     * Sets the identifier.
     *
     * @param identifier the new identifier
     */
    public void setIdentifier(URI identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets the instructional methods.
     *
     * @return the instructional methods
     */
    @CollectionOfElements(fetch = FetchType.EAGER, targetElement = String.class)
    public Set<String> getInstructionalMethods() {
        return instructionalMethods;
    }

    /**
     * Sets the instructional methods.
     *
     * @param instructionalMethods the new instructional methods
     */
    public void setInstructionalMethods(Set<String> instructionalMethods) {
        this.instructionalMethods = instructionalMethods;
    }

    /**
     * Gets the issued date.
     *
     * @return the issued date
     */
    @Column(name = "ISSUED_DATE")
    @Field(index = Index.UN_TOKENIZED, store = Store.COMPRESS)
    @DateBridge(resolution = Resolution.DAY)
    public Date getIssuedDate() {
        return issuedDate;
    }

    /**
     * Sets the issued date.
     *
     * @param issuedDate the new issued date
     */
    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    /**
     * Gets the language.
     *
     * @return the language
     */
    @Column(name = "LANGUAGE")
    @Field(index = Index.TOKENIZED, store = Store.NO)
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the language.
     *
     * @param language the new language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gets the licenses.
     *
     * @return the licenses
     */
    @Transient
    public Set<URI> getLicenses() {
        return licenses;
    }

    /**
     * Sets the licenses.
     *
     * @param licenses the new licenses
     */
    public void setLicenses(Set<URI> licenses) {
        this.licenses = licenses;
    }

    /**
     * Gets the modified dates.
     *
     * @return the modified dates
     */
    @CollectionOfElements(fetch = FetchType.EAGER, targetElement = Date.class)
    public Set<Date> getModifiedDates() {
        return modifiedDates;
    }

    /**
     * Sets the modified dates.
     *
     * @param modifiedDates the new modified dates
     */
    public void setModifiedDates(Set<Date> modifiedDates) {
        this.modifiedDates = modifiedDates;
    }

    /**
     * Gets the provenances.
     *
     * @return the provenances
     */
    @CollectionOfElements(fetch = FetchType.EAGER, targetElement = String.class)
    public Set<String> getProvenances() {
        return provenances;
    }

    /**
     * Sets the provenances.
     *
     * @param provenances the new provenances
     */
    public void setProvenances(Set<String> provenances) {
        this.provenances = provenances;
    }

    /**
     * Gets the replaced date.
     *
     * @return the replaced date
     */
    @Column(name = "REPLACED_DATE")
    @Field(index = Index.UN_TOKENIZED, store = Store.COMPRESS)
    @DateBridge(resolution = Resolution.DAY)
    public Date getReplacedDate() {
        return replacedDate;
    }

    /**
     * Sets the replaced date.
     *
     * @param replacedDate the new replaced date
     */
    public void setReplacedDate(Date replacedDate) {
        this.replacedDate = replacedDate;
    }

    /**
     * Gets the retired date.
     *
     * @return the retired date
     */
    @Column(name = "RETIRED_DATE")
    @Field(index = Index.UN_TOKENIZED, store = Store.COMPRESS)
    @DateBridge(resolution = Resolution.DAY)
    public Date getRetiredDate() {
        return retiredDate;
    }

    /**
     * Sets the retired date.
     *
     * @param retiredDate the new retired date
     */
    public void setRetiredDate(Date retiredDate) {
        this.retiredDate = retiredDate;
    }

    /**
     * Gets the sources.
     *
     * @return the sources
     */
    @Transient
    public Set<URI> getSources() {
        return sources;
    }

    /**
     * Sets the sources.
     *
     * @param sources the new sources
     */
    public void setSources(Set<URI> sources) {
        this.sources = sources;
    }

    /**
     * Gets the subject.
     *
     * @return the subject
     */
    @Column(name = "SUBJECT")
    @Field(index = Index.TOKENIZED, store = Store.NO)
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject.
     *
     * @param subject the new subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets the submitted date.
     *
     * @return the submitted date
     */
    @Column(name = "SUBMITTED_DATE")
    @Field(index = Index.UN_TOKENIZED, store = Store.COMPRESS)
    @DateBridge(resolution = Resolution.DAY)
    public Date getSubmittedDate() {
        return submittedDate;
    }

    /**
     * Sets the submitted date.
     *
     * @param submittedDate the new submitted date
     */
    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    /**
     * Gets the temporal coverage.
     *
     * @return the temporal coverage
     */
    @CollectionOfElements(fetch = FetchType.EAGER, targetElement = Calendar.class)
    public Collection<Calendar> getTemporalCoverage() {
        return temporalCoverage;
    }

    /**
     * Sets the temporal coverage.
     *
     * @param temporalCoverage the new temporal coverage
     */
    public void setTemporalCoverage(Collection<Calendar> temporalCoverage) {
        this.temporalCoverage = temporalCoverage;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    @Column(name = "TITLE")
    @Field(index = Index.TOKENIZED, store = Store.NO)
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Transient
    public METADATA_TYPE getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(METADATA_TYPE type) {
        this.type = type;
    }

    /**
     * Gets the valid date.
     *
     * @return the valid date
     */
    @Column(name = "VALIED_DATE")
    @Field(index = Index.UN_TOKENIZED, store = Store.COMPRESS)
    @DateBridge(resolution = Resolution.DAY)
    public Date getValidDate() {
        return validDate;
    }

    /**
     * Sets the valid date.
     *
     * @param validDate the new valid date
     */
    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    /**
     * Gets the contributors.
     *
     * @return the contributors
     */
    @CollectionOfElements(fetch = FetchType.EAGER, targetElement = PersonImpl.class)
    public Set<Person> getContributors() {
        return contributors;
    }

    /**
     * Sets the contributors.
     *
     * @param contributors the new contributors
     */
    public void setContributors(Set<Person> contributors) {
        this.contributors = contributors;
    }

    /**
     * Gets the creators.
     *
     * @return the creators
     */
    @CollectionOfElements(fetch = FetchType.EAGER, targetElement = PersonImpl.class)
    public Set<Person> getCreators() {
        return creators;
    }

    /**
     * Sets the creators.
     *
     * @param creators the new creators
     */
    public void setCreators(Set<Person> creators) {
        this.creators = creators;
    }

    /**
     * Gets the publishers.
     *
     * @return the publishers
     */
    @CollectionOfElements(fetch = FetchType.EAGER, targetElement = PersonImpl.class)
    public Set<Person> getPublishers() {
        return publishers;
    }

    /**
     * Sets the publishers.
     *
     * @param publishers the new publishers
     */
    public void setPublishers(Set<Person> publishers) {
        this.publishers = publishers;
    }

    /**
     * Gets the rights holders.
     *
     * @return the rights holders
     */
    @CollectionOfElements(fetch = FetchType.EAGER, targetElement = PersonImpl.class)
    public Set<Person> getRightsHolders() {
        return rightsHolders;
    }

    /**
     * Sets the rights holders.
     *
     * @param rightsHolders the new rights holders
     */
    public void setRightsHolders(Set<Person> rightsHolders) {
        this.rightsHolders = rightsHolders;
    }
}
