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

import org.hibernate.criterion.DetachedCriteria;
import uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.entry.ClinicalFeatureEntry;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a DAO that manages.
 *
 * {@link uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry} objects.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 15, 2010 at 2:27:46 PM
 */
public interface ClinicalEntryDAO {

    /**
     * Save entry.
     *
     * @param entry the entry
     */
    void saveEntry(ClinicalEntry entry);

    /**
     * Delete entry.
     *
     * @param entry the entry
     */
    void deleteEntry(ClinicalEntry entry);

    /**
     * Delete all entries.
     */
    void deleteAllEntries();

    /**
     * Find all entries.
     *
     * @return the list
     */
    List<ClinicalEntry> findAllEntries();

    /**
     * Find entry with close to user uu id.
     *
     * @param ctuId the ctu id
     * @return the clinical entry
     */
    ClinicalEntry findEntryWithCloseToUserUuId(String ctuId);

    /**
     * Find entries with ct uuuids.
     *
     * @param nfUuids the nf uuids
     * @return the list
     */
    List<ClinicalEntry> findEntriesWithCTUuuids(Collection<UUID> nfUuids);

    /**
     * Find features with value less than exclusive.
     *
     * @param uuids the uuids
     * @param value the value
     * @return the list
     */
    List<ClinicalFeatureEntry> findFeaturesWithValueLessThanExclusive(String[] uuids, double value);

    /**
     * Find features with value greater than exclusive.
     *
     * @param uuids the uuids
     * @param value the value
     * @return the list
     */
    List<ClinicalFeatureEntry> findFeaturesWithValueGreaterThanExclusive(String[] uuids, double value);

    /**
     * Find features with value less than inclusive.
     *
     * @param entryType the entry type
     * @param uuids the uuids
     * @param value the value
     * @return the list
     */
    List<ClinicalFeatureEntry> findFeaturesWithValueLessThanInclusive(String entryType, String[] uuids, double value);

    /**
     * Find features with value greater than inclusive.
     *
     * @param uuids the uuids
     * @param value the value
     * @return the list
     */
    List<ClinicalFeatureEntry> findFeaturesWithValueGreaterThanInclusive(String[] uuids, double value);

    /**
     * Find features with values between.
     *
     * @param uuids the uuids
     * @param upperBound the upper bound
     * @param lowerBound the lower bound
     * @return the list
     */
    List<ClinicalFeatureEntry> findFeaturesWithValuesBetween(String[] uuids, double upperBound, double lowerBound);

    /**
     * Find entries using criteria.
     *
     * @param detachedCriteria the detached criteria
     * @return the list
     */
    List<ClinicalEntry> findEntriesUsingCriteria(DetachedCriteria detachedCriteria);

    ClinicalEntry findEntryWithCloseToUserUuId(UUID uuid);
}
