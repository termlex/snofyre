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
package uk.nhs.cfh.dsp.yasb.searchpanel.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocCollector;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.ListModel} that handles results returned as {@link org.apache.lucene.document.Document}s.
 * The execution and population of the model are carried out by {@link uk.nhs.cfh.dsp.yasb.searchpanel.actions.PopulateListModelTask}
 * 
 * <br> Written by @author jay
 * <br> Created on Dec 23, 2008 at 4:14:41 PM
 * <br> Modified on Friday; December 4, 2009 at 11:42 AM to include modular services.
 */
@SuppressWarnings("serial")
public class SearchResultsListModel extends AbstractListModel {

	/** The logger. */
	private static Log logger = LogFactory.getLog(SearchResultsListModel.class);
	
	/** The documents. */
	private List<Document> documents = new ArrayList<Document>();
	
	/** The searcher. */
	private IndexSearcher searcher;

	/**
	 * Instantiates a new search results list model.
	 *
	 * @param searcher the searcher
	 * @param collector the collector
	 */
	public SearchResultsListModel(IndexSearcher searcher, TopDocCollector collector) {
		this.searcher = searcher;
		// populate model
		populateModel(collector);
	}

	/**
	 * Instantiates a new search results list model.
	 *
	 * @param searcher the searcher
	 */
	public SearchResultsListModel(IndexSearcher searcher) {
		this.searcher = searcher;
	}

    /**
     * Instantiates a new search results list model.
     */
    public SearchResultsListModel() {
    }

    /**
     * the method that populates the list model from results.
     *
     * @param collector the collector that holds the search results
     */
    public void populateModel(TopDocCollector collector){

		// clear existing contents
		documents.clear();
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		logger.debug("Value of hits : " + hits.length);
		// get all documents from hits
		for (int i = 0; i < hits.length; i++)
		{
			try
			{
				int docId = hits[i].doc;
				Document doc = searcher.doc(docId);
                
//                uncomment the lines below for debuggin
//				for(Object o: doc.getFields())
//				{
//					Field f = (Field) o;
//					logger.debug("Value of f.name() : " + f.name()+"\t Value : "+f.stringValue());
//				}
//				logger.debug("Value of doc.get(CONCEPTTYPE) : " + doc.get("CONCEPTTYPE"));
				documents.add(doc);

			} catch (CorruptIndexException e) {
				logger.warn(e.fillInStackTrace());
			} catch (IOException e) {
				logger.warn(e.fillInStackTrace());
			}
		}

		// fire model update event
		fireContentsChanged(this, 0, documents.size());
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	public Object getElementAt(int index) {
		return documents.get(index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return documents.size();
	}

    /**
     * utility method that clears existing model. This method is used by the
     * {@link uk.nhs.cfh.dsp.yasb.searchpanel.actions.PopulateListModelTask} to display results
     */
	public void clearContents() {
		documents.clear();
		fireContentsChanged(this, 0, 0);
	}

    /**
     * utility method that populates the list model with all the search results.
     * This method is used by {@link uk.nhs.cfh.dsp.yasb.searchpanel.actions.PopulateListModelTask} to
     * display search results.
     * @param list the list object that contains the search results.
     */
	public void addAll(List<Document> list){

		if(list != null && list.size() > 0)
		{
			documents.addAll(list);
			// notify listeners
			int lastIndex = documents.size();
			int firstIndex = lastIndex - list.size();
			fireIntervalAdded(this, firstIndex, lastIndex);
		}
	}
}