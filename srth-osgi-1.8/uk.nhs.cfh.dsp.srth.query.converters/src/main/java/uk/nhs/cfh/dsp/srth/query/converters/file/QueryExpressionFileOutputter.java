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
package uk.nhs.cfh.dsp.srth.query.converters.file;

import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;

import java.net.URI;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a service that saves a {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement}
 * to a given {@link java.net.URI}.
 * <br> Note: this class should not exist under the converters bundle. It belongs in an IO or repo bundle with other
 * DAO objects.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 21, 2010 at 12:19:17 PM
 */
public interface QueryExpressionFileOutputter {

    /**
     * Save.
     * 
     * @param queryStatement the query statement
     */
    void save(QueryStatement queryStatement);

    /**
     * Save.
     * 
     * @param queryStatement the query statement
     * @param physicalURI the physical uri
     */
    void save(QueryStatement queryStatement, URI physicalURI);
}
