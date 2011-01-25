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
package uk.nhs.cfh.dsp.srth.query.transform;

import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;
import uk.nhs.cfh.dsp.srth.query.transform.utils.QueryStatisticsCollection;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * A listener that listens to events from a {@link uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService}.
 * It is notified when a {@link java.sql.ResultSet} becomes available or the query time changes.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 30, 2009 at 2:25:44 PM
 */
public interface QueryEngineServiceListener {

    /**
     * Result set changed.
     * 
     * @param resultSet the result set
     */
    void resultSetChanged(ResultSet resultSet);

    /**
     * Query time changed.
     * 
     * @param queryTime the query time
     */
    void queryTimeChanged(long queryTime);

    /**
     * Query statistics collection changed.
     * 
     * @param statisticsCollectionMap the statistics collection map
     */
    void queryStatisticsCollectionChanged(Map<QueryExpression, QueryStatisticsCollection> statisticsCollectionMap);

    void queryResultsAvailable(Collection<ClinicalEntry> entries);
}
