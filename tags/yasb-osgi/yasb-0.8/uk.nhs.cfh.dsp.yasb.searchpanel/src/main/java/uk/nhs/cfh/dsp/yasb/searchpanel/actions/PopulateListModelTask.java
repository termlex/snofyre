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

import org.apache.lucene.document.Document;
import org.jdesktop.application.Application;
import uk.nhs.cfh.dsp.snomed.objectmodel.ComponentStatus;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchService;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider;
import uk.nhs.cfh.dsp.yasb.searchpanel.model.SearchResultsListModel;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A custom subclass of {@link PublishSearchResultsTask} that processes
 * the intermediate results and populates {@link SearchResultsListModel}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 27, 2009 at 2:27:00 PM
 * <br> Modified on Friday; December 4, 2009 at 11:40 AM to include module services
 */
public class PopulateListModelTask extends PublishSearchResultsTask {

	/** The list model. */
	private SearchResultsListModel listModel;

	/**
	 * Instantiates a new populate list model task.
	 *
	 * @param app the app
	 * @param terminologySearchService the terminology search service
	 * @param listModel the list model
	 * @param algorithm the algorithm
	 * @param queryTerm the query term
	 * @param conceptStatus the concept status
	 * @param conceptType the concept type
	 * @param searchConceptId the search concept id
	 */
	public PopulateListModelTask(Application app, TerminologySearchService terminologySearchService,
                                 SearchResultsListModel listModel,
                                 TerminologySearchServiceProvider.SearchAlgorithm algorithm,
                                 String queryTerm, ComponentStatus conceptStatus, ConceptType conceptType,
                                 boolean searchConceptId) {
		super(app, terminologySearchService, algorithm, queryTerm, conceptStatus, conceptType, searchConceptId);
		this.listModel = listModel;
		// clear existing list contents
		this.listModel.clearContents();
	}

	/* (non-Javadoc)
	 * @see org.jdesktop.application.Task#process(java.util.List)
	 */
	@Override
	protected void process(List<Document> results) {

		if(!isCancelled() && results.size() > 0)
		{
			listModel.addAll(results);
		}
	}
}