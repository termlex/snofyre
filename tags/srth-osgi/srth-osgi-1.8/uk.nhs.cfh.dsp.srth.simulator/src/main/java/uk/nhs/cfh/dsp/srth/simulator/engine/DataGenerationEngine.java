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
package uk.nhs.cfh.dsp.srth.simulator.engine;

import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;


/**
 * An interface for generating random patient data based on a given {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement}.
 * The number of records generated is a cartesian product of the individual sub queries
 * that make up the {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement} after the logical operators have
 * been resolved.
 * <br>Note that the source for creating data can be a {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement}.
 * <br>Rules based data generation has not been implemented yet but this should be
 * possible using a rules engine like Drools.
 *
 * <br> See also {@link DataGenerationSource}.
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on June 10, 2009 at 4:16:45 PM
 * <br>
 */

public interface DataGenerationEngine {

    boolean isTrackProgress();

    void setTrackProgress(boolean trackProgress);

    boolean isRefineQualifiers();

    void setRefineQualifiers(boolean refineQualifiers);

    boolean isIncludePrecoordinatedData();

    void setIncludePrecoordinatedData(boolean includePrecoordinatedData);

    boolean isStopEngine();

    void setStopEngine(boolean stopEngine);

    /**
     * The Enum DataGenerationSource.
     */
    public enum DataGenerationSource {
    QUERY,
    RULES};

    /**
     * The Enum DataGenerationStrategy.
     */
    public enum DataGenerationStrategy {
        ADD_NEW_ALWAYS,
        ALWAYS_APPEND,
        ADD_IF_NOT_EXISTS};

    /**
     * Generate data for query.
     *
     * @param query the query
     */
    void generateDataForQuery(QueryStatement query);

    /**
     * Gets the data generation source.
     *
     * @return the data generation source
     */
    DataGenerationSource getDataGenerationSource();

    /**
     * Sets the data generation source.
     *
     * @param source the new data generation source
     */
    void setDataGenerationSource(DataGenerationSource source);

    /**
     * Gets the data generation strategy.
     *
     * @return the data generation strategy
     */
    DataGenerationStrategy getDataGenerationStrategy();

    /**
     * Sets the data generation strategy.
     *
     * @param strategy the new data generation strategy
     */
    void setDataGenerationStrategy(DataGenerationStrategy strategy);

    /**
     * Gets the min patient age in years.
     *
     * @return the min patient age in years
     */
    int getMinPatientAgeInYears();

    /**
     * Sets the min patient age in years.
     *
     * @param minPatientAgeInYears the new min patient age in years
     */
    void setMinPatientAgeInYears(int minPatientAgeInYears);

    /**
     * Gets the max patient number.
     *
     * @return the max patient number
     */
    long getMaxPatientNumber();

    /**
     * Sets the max patient number.
     *
     * @param maxPatientNumber the new max patient number
     */
    void setMaxPatientNumber(long maxPatientNumber);

    /**
     * Checks if is randomise numerical values.
     *
     * @return true, if is randomise numerical values
     */
    boolean isRandomiseNumericalValues();

    /**
     * Sets the randomise numerical values.
     *
     * @param randomiseNumericalValues the new randomise numerical values
     */
    void setRandomiseNumericalValues(boolean randomiseNumericalValues);

    /**
     * Checks if is include excluded terms.
     *
     * @return true, if is include excluded terms
     */
    boolean isIncludeExcludedTerms();

    /**
     * Sets the include excluded terms.
     *
     * @param includeExcludedTerms the new include excluded terms
     */
    void setIncludeExcludedTerms(boolean includeExcludedTerms);
}