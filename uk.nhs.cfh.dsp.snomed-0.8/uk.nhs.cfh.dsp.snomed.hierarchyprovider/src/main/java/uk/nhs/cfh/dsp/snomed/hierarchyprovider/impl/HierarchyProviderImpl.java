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
package uk.nhs.cfh.dsp.snomed.hierarchyprovider.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.hierarchyprovider.HierarchyProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * An concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.hierarchyprovider.HierarchyProvider}.
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 9, 2009 at 11:17:11 AM
 */
public class HierarchyProviderImpl extends AbstractHierarchyProvider implements HierarchyProvider {
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(HierarchyProviderImpl.class);

    /**
     * Instantiates a new hierarchy provider impl.
     *
     * @param dataSource the data source
     */
    public HierarchyProviderImpl(DataSource dataSource){
        super(dataSource);
    }

    /**
     * Instantiates a new hierarchy provider impl.
     */
    public HierarchyProviderImpl() {

    }

    /**
     * Instantiates a new hierarchy provider impl.
     *
     * @param connection the connection
     */
    public HierarchyProviderImpl(Connection connection) {
        super(connection);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.hierarchyprovider.HierarchyProvider#getDescendants(java.lang.String)
     */
    public Set<String> getDescendants(String conceptId) {

        Set<String> descendantIds = new HashSet<String>();
        if(conceptId != null && ! "".equalsIgnoreCase(conceptId))
        {
            // get children of concept
            Set<String> childrenIds = getChildren(conceptId);
            descendantIds.addAll(childrenIds);

            if(childrenIds.size() >0)
            {
                // recursive call on each child
                for(String childId : childrenIds)
                {
                    descendantIds.addAll(getDescendants(childId));
                }
            }

            return descendantIds;
        }
        else
        {
            logger.warn(NULL_ARG_MSG);
            throw new IllegalArgumentException(NULL_ARG_MSG+conceptId);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.hierarchyprovider.HierarchyProvider#getAncestors(java.lang.String)
     */
    public Set<String> getAncestors(String conceptId) {

        Set<String> ancestorIds = new HashSet<String>();
        if(conceptId != null && ! "".equalsIgnoreCase(conceptId))
        {
            // get children of concept
            Set<String> parentIds = getParents(conceptId);
            ancestorIds.addAll(parentIds);

            if(parentIds.size() > 0)
            {
                for(String parentId : parentIds)
                {
                    // recursive call
                    ancestorIds.addAll(getAncestors(parentId));
                }
            }

            return ancestorIds;
        }
        else
        {
            logger.warn(NULL_ARG_MSG);
            throw new IllegalArgumentException(NULL_ARG_MSG+conceptId);
        }
    }
}
