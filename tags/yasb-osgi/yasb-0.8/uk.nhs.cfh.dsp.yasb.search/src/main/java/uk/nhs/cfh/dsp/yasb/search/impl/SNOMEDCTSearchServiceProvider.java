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
package uk.nhs.cfh.dsp.yasb.search.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import uk.nhs.cfh.dsp.snomed.objectmodel.ComponentStatus;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchService;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider},
 * that provides search for SNOMED CT.
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 23, 2009 at 10:25:11 PM
 */
public class SNOMEDCTSearchServiceProvider extends AbstractTerminologySearchServiceProvider{

    /** The logger. */
    private static Log logger = LogFactory.getLog(SNOMEDCTSearchServiceProvider.class);
    
    /** The index searcher. */
    private IndexSearcher indexSearcher;
    
    /** The selected analyzer. */
    private Analyzer selectedAnalyzer = new StandardAnalyzer();
    
    /** The term search field. */
    private String termSearchField = "TERM";
    
    /** The concept type field. */
    private String conceptTypeField = "CONCEPTTYPE";
    
    /** The component status field. */
    private String componentStatusField = "STATUS";
    
    /** The concept id field. */
    private String conceptIdField = "CONCEPTID";
    
    /** The terminology search service. */
    private TerminologySearchService terminologySearchService;

    /**
     * Instantiates a new sNOMEDCT search service provider.
     *
     * @param serviceName the service name
     * @param serviceVersion the service version
     */
    public SNOMEDCTSearchServiceProvider(String serviceName, String serviceVersion) {
        super(serviceName, serviceVersion);
        // set default analyser
        selectedAnalyzer = new StandardAnalyzer();
        // set up index location
        setupIndex();
    }

    /**
     * A constructor that takes an URL for index.
     *
     * @param indexLocation the location of the Lucence Index
     * @param terminologySearchService the terminology search service
     */
    public SNOMEDCTSearchServiceProvider(URL indexLocation, TerminologySearchService terminologySearchService) {
        super(SNOMEDCTSearchServiceProvider.class.getName(), "1.0");
        setIndexLocation(indexLocation.toString());
        this.terminologySearchService = terminologySearchService;
    }

    /**
     * Empty constructor for IOC. Always run the init method when instantiating this class
     */
    public SNOMEDCTSearchServiceProvider() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.impl.AbstractTerminologySearchServiceProvider#init()
     */
    public synchronized void init(){
        // set as default provider
        setDefaultServiceProvider(true);
        // register self with terminologySearchService
        terminologySearchService.registerProvider(this);
        // set up index location
        setupIndex();
    }

    /**
     * This method returns a {@link java.util.List} of {@link org.apache.lucene.document.Document} objects
     * generally. This might be over ridden when the entire API is moved to use the SNOMED CT object model.
     * Then a collection of {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} will be returned.
     *
     * @param algorithm the search algorithm to use
     * @param searchTerm the search term
     * @param componentStatus the status of the {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedComponent}
     * @param conceptType the type of the concept
     * @return a list of {@link org.apache.lucene.document.Document} objects. See note above
     */
    @Override
    public List<? extends Object> getMatchesForTerm(SearchAlgorithm algorithm,
                                                          String searchTerm, ComponentStatus componentStatus, ConceptType conceptType) {
        List<Document> ids = new ArrayList<Document>();
        // get query from criteria
        try
        {
            Query query = getQueryFromSearchCriteria(algorithm, searchTerm, componentStatus, conceptType, false);
            TopDocCollector collector = new TopDocCollector(getMaxResultsSize());
            logger.debug("query = " + query);
            logger.debug("indexSearcher = " + indexSearcher);
            indexSearcher.search(query, collector);

            ScoreDoc[] hits = collector.topDocs().scoreDocs;
            // get all documents from hits
            for (int i = 0; i < hits.length; i++)
            {
                try
                {
                    int docId = hits[i].doc;
                    Document doc = indexSearcher.doc(docId);
                    ids.add(doc);
                    
                } catch (CorruptIndexException e) {
                    logger.warn(e.getMessage());
                } catch (IOException e) {
                    logger.warn(e.getMessage());
                }
            }

        } catch (ParseException e) {
            logger.warn("Error parsing search query. Nested exception is : " + e.fillInStackTrace());
        } catch (IOException e) {
            logger.warn("Error locating index to search over. Nested exception is : " + e.fillInStackTrace());
        }

        return ids;
    }

    /**
     * This method returns a {@link java.util.List} of {@link org.apache.lucene.document.Document} objects
     * generally. This might be over ridden when the entire API is moved to use the SNOMED CT object model.
     * Then a collection of {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} will be returned.
     *
     * @param algorithm the search algorithm to use
     * @param conceptId the concept id to search for
     * @param conceptStatus the status of the {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedComponent}
     * @param conceptType the type of the concept
     * @return a list of {@link org.apache.lucene.document.Document} objects. See note above
     */
    @Override
    public List<? extends Object> getMatchesForID(SearchAlgorithm algorithm,
                                              String conceptId, ComponentStatus conceptStatus, ConceptType conceptType) {

        List<Document> ids = new ArrayList<Document>();
        // get query from criteria
        try
        {
            Query query = getQueryFromSearchCriteria(algorithm, conceptId, conceptStatus, conceptType, true);
            TopDocCollector collector = new TopDocCollector(getMaxResultsSize());
            indexSearcher.search(query, collector);

            ScoreDoc[] hits = collector.topDocs().scoreDocs;
            // get all documents from hits
            for (int i = 0; i < hits.length; i++)
            {
                try
                {
                    int docId = hits[i].doc;
                    Document doc = indexSearcher.doc(docId);
                    ids.add(doc);

                } catch (CorruptIndexException e) {
                    logger.warn(e.getMessage());
                } catch (IOException e) {
                    logger.warn(e.getMessage());
                }
            }
            
        } catch (ParseException e) {
            logger.warn("Error parsing search query. Nested exception is : " + e.fillInStackTrace());
        } catch (IOException e) {
            logger.warn("Error locating index to search over. Nested exception is : " + e.fillInStackTrace());
        }

        return ids;
    }

    /**
     * Generates an appropriate {@link org.apache.lucene.search.Query} for the given search term based on
     * other parameters. It uses the isConceptId flag to check against concept ids
     *
     * @param algorithm the search algorithm to use
     * @param term the term to search for
     * @param conceptStatus the status of the {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedComponent}
     * @param conceptType the type of the concept
     * @param isConceptId flag to use term string to check against concept ids
     * @return a {@link org.apache.lucene.search.Query} object for use against the given index.
     * @throws ParseException the parse exception
     */
    private Query getQueryFromSearchCriteria(TerminologySearchServiceProvider.SearchAlgorithm algorithm, String term,
                                             ComponentStatus conceptStatus, ConceptType conceptType, boolean isConceptId)
            throws ParseException{
        Query query = null;
        // get current value in search field
        String queryString = term.trim();
        String descriptionSearchField = null;

        if(queryString != null && queryString.length() >1)
        {
            if(TerminologySearchServiceProvider.SearchAlgorithm.WILD_CARDS == algorithm)
            {
                String newQueryString = "";
                // tokenise string and add * to every token
                StringTokenizer tokenizer = new StringTokenizer(queryString);
                while(tokenizer.hasMoreTokens())
                {
                    String token = tokenizer.nextToken();
                    token = token+"*";
                    // add token to new string expression
                    newQueryString = newQueryString+" "+token;
                }

                // reset queryString as newQueryString
                queryString = newQueryString.trim();
            }

            // check if space is to be treated as conjunction or disjunction
            if(TerminologySearchServiceProvider.SearchAlgorithm.PHRASE_UNORDERED != algorithm)
            {
                queryString = queryString.replaceAll(" ", " AND ");
            }

            logger.info("Value of queryString after setting up search preferences : " + queryString);

            // handle case where just code id is selected, we can ignore every other setting here
            if(isConceptId)
            {
                descriptionSearchField = conceptIdField;
            }
            else
            {
                descriptionSearchField = termSearchField;
            }

            if(ComponentStatus.UNKNOWN == conceptStatus)
            {
                if(ConceptType.UNKNOWN == conceptType)
                {
                    query = MultiFieldQueryParser.parse(
                            new String[] {queryString}
                            , new String[] { descriptionSearchField },
                            selectedAnalyzer);
                }
                else // selected concept type is not ALL
                {
                    query = MultiFieldQueryParser.parse(new String[] {
                            queryString, conceptType.name()}
                            , new String[] { descriptionSearchField, conceptTypeField },
                            new BooleanClause.Occur[] {BooleanClause.Occur.MUST, BooleanClause.Occur.MUST},
                            selectedAnalyzer);
                }
            }
            else  // selected concept type is not ALL and selected concept status is not ALL
            {
                if(ConceptType.UNKNOWN == conceptType)
                {
                    query = MultiFieldQueryParser.parse(new String[] {
                            queryString, conceptStatus.toString()}
                            , new String[] { descriptionSearchField, componentStatusField },
                            new BooleanClause.Occur[] {BooleanClause.Occur.MUST, BooleanClause.Occur.MUST},
                            selectedAnalyzer);
                }
                else // selected concept type is not ALL
                {
                    query = MultiFieldQueryParser.parse(new String[] {
                            queryString, conceptType.toString(), conceptStatus.toString()}
                            , new String[] { descriptionSearchField, conceptTypeField, componentStatusField },
                            new BooleanClause.Occur[] {BooleanClause.Occur.MUST, BooleanClause.Occur.MUST,
                                    BooleanClause.Occur.MUST},
                            selectedAnalyzer);
                }
            }
        }

        logger.debug("query = " + query);
        return query;
    }

    /**
     * A synchronised method that sets up the index!.
     */
    private synchronized void setupIndex() {
        try
        {
            Directory directory = FSDirectory.getDirectory(new File(getIndexLocation()));
            // set up index searcher
            indexSearcher = new IndexSearcher(directory);
        } catch (IOException e) {
            logger.warn("Error locating search index. Nested exception is : " + e.fillInStackTrace());
        }
    }

    /**
     * Sets the terminology search service.
     *
     * @param terminologySearchService the new terminology search service
     */
    public synchronized void setTerminologySearchService(TerminologySearchService terminologySearchService) {
        this.terminologySearchService = terminologySearchService;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.impl.AbstractTerminologySearchServiceProvider#dispose()
     */
    public synchronized void dispose() {
        // unregister from terminologySearchService
        terminologySearchService.unregisterProvider(this);
    }
}
