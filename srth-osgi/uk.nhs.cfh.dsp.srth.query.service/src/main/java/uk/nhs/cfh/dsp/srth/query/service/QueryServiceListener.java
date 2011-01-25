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
/**
 *
 */
package uk.nhs.cfh.dsp.srth.query.service;

import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a listener for a {@link QueryService}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Nov 16, 2009 at 5:32:26 PM
 * <br> Modified on Wednesday; December 2, 2009 at 4:09 PM to include the source of the event
 *
 * @see QueryServiceEvent
 */

public interface QueryServiceListener {

    /**
     * Query changed.
     *
     * @param service the service
     * @param query the query
     * @param source the source
     */
    void queryChanged(QueryService service, QueryStatement query, Object source);

}
