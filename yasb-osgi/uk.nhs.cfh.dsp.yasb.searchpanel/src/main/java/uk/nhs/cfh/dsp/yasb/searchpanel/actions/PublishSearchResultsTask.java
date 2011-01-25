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
package uk.nhs.cfh.dsp.yasb.searchpanel.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import uk.nhs.cfh.dsp.snomed.objectmodel.ComponentStatus;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchService;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A custom class that publishes the results of search operation as a {@link java.util.List}
 * of {@link org.apache.lucene.document.Document}s.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 27, 2009 at 2:19:06 PM
 * <br> Modified on Friday; December 4, 2009 at 11:44 AM to include modular services
 */
public class PublishSearchResultsTask extends Task<List<Document>, Document> {

    /** The terminology search service. */
    private TerminologySearchService terminologySearchService;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(PublishSearchResultsTask.class);
    
    /** The query term. */
    private String queryTerm;
    
    /** The concept status. */
    private ComponentStatus conceptStatus;
    
    /** The concept type. */
    private ConceptType conceptType;
    
    /** The search concept id. */
    private boolean searchConceptId;
    private TerminologySearchServiceProvider.SearchAlgorithm algorithm;

    /**
     * Instantiates a new publish search results task.
     *
     * @param app the app
     * @param searchService the search service
     * @param algorithm the algorithm
     * @param queryTerm the query term
     * @param conceptStatus the concept status
     * @param conceptType the concept type
     * @param searchConceptId the search concept id
     */
    public PublishSearchResultsTask(Application app, TerminologySearchService searchService,
                                    TerminologySearchServiceProvider.SearchAlgorithm algorithm,
                                    String queryTerm, ComponentStatus conceptStatus, ConceptType conceptType,
                                    boolean searchConceptId) {
        super(app);
        this.terminologySearchService = searchService;
        this.queryTerm = queryTerm;
        this.conceptStatus = conceptStatus;
        this.conceptType = conceptType;
        this.searchConceptId = searchConceptId;
        this.algorithm = algorithm;
        
        if (logger.isDebugEnabled())
        {
            logger.debug("conceptStatus = " + conceptStatus);
            logger.debug("conceptType = " + conceptType);
            logger.debug("queryTerm = " + queryTerm);
            logger.debug("queryTerm = " + queryTerm);
            logger.debug("algorithm = " + algorithm);
        }
    }

    /* (non-Javadoc)
     * @see org.jdesktop.swingworker.SwingWorker#doInBackground()
     */
    @Override
    protected List<Document> doInBackground() throws Exception {

        if(!isCancelled())
        {
            // reset algorithm to WILD CARDS
            if(algorithm == null)
            {
                algorithm = TerminologySearchServiceProvider.SearchAlgorithm.WILD_CARDS;
            }
            if (! searchConceptId)
            {
                List<Document> searchResult =
                        (List<Document>) terminologySearchService.getMatchesForTerm(
                                algorithm,
                                queryTerm, conceptStatus, conceptType);

                for(Document doc : searchResult)
                {
                    publish(doc);
                }
            }
            else
            {
                List<Document> searchResult =
                        (List<Document>) terminologySearchService.getMatchesForID(
                                algorithm,
                                queryTerm, conceptStatus, conceptType);

                for(Document doc : searchResult)
                {
                    publish(doc);
                }
            }
        }

        return null;
    }

    /* (non-Javadoc)
     * @see org.jdesktop.application.Task#succeeded(T)
     */
    @Override
    protected void succeeded(List<Document> arg0) {
        setMessage("Done processing reults");
    }
}