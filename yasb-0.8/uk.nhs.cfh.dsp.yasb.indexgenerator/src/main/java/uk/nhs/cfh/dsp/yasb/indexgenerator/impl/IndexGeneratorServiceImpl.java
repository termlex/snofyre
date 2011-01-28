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
package uk.nhs.cfh.dsp.yasb.indexgenerator.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.store.Directory;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.yasb.indexgenerator.IndexGeneratorService;
import uk.nhs.cfh.dsp.yasb.indexgenerator.SnomedLuceneDescriptionIndexer;
import uk.nhs.cfh.dsp.yasb.indexgenerator.SnomedLuceneIndexer;

import javax.sql.DataSource;
import java.net.URL;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of an {@link uk.nhs.cfh.dsp.yasb.indexgenerator.IndexGeneratorService}.
 * <br> NOTE: this class does not use the indexURL. This needs to be implemented to generate file based
 * or jdbc based indices.
 *
 * @scr.component immediate=“true”
 * @scr.service 
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 6, 2010 at 11:24:36 AM
 */
public class IndexGeneratorServiceImpl implements IndexGeneratorService{

    /** The index location. */
    private URL indexLocation;
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(IndexGeneratorServiceImpl.class);
    
    /** The data source. */
    private DataSource dataSource;

    /**
     * Instantiates a new index generator service impl.
     *
     * @param dataSource the data source
     * @param terminologyConceptDAO the terminology concept dao
     */
    public IndexGeneratorServiceImpl(DataSource dataSource,
                                     TerminologyConceptDAO terminologyConceptDAO) {
        this.dataSource = dataSource;
        this.terminologyConceptDAO = terminologyConceptDAO;
    }

    /**
     * empty constructor for dependency injection.
     */
    public IndexGeneratorServiceImpl() {
    }

    /**
     * the method that generates an index in the specified index location and returns
     * the {@link org.apache.lucene.store.Directory} for the index
     *
     * @param indexFlavour the {@link uk.nhs.cfh.dsp.yasb.indexgenerator.IndexGeneratorService.Index_Flavour}
     * @return the {@link org.apache.lucene.store.Directory} object for the index.
     */
    public Directory getIndexDirectory(Index_Flavour indexFlavour){

        if(Index_Flavour.CONCEPT == indexFlavour)
        {
            // indexers are tasks, so we execute the task in a separate thread
            SnomedLuceneIndexer luceneIndexer =
                    new SnomedLuceneIndexer(dataSource, terminologyConceptDAO);
            luceneIndexer.execute();
            int counter =0;
            while(! luceneIndexer.isDone())
            {
                if(counter % 10000 == 0)
                {
                    logger.info("Generating index please wait...");
                }

                // increment counter;
                counter++;
            }

            return luceneIndexer.getDirectory();
        }
        else if(Index_Flavour.DESCRIPTION == indexFlavour)
        {
            // indexers are tasks, so we execute the task in a separate thread
            SnomedLuceneDescriptionIndexer luceneDescriptionIndexer =
                    new SnomedLuceneDescriptionIndexer(dataSource, terminologyConceptDAO);
            luceneDescriptionIndexer.execute();
            int counter = 0;
            while(! luceneDescriptionIndexer.isDone())
            {
                if(counter % 10000 == 0)
                {
                    logger.info("Generating index please wait...");
                }

                // increment counter;
                counter++;
            }

            return luceneDescriptionIndexer.getDirectory();
        }
        else
        {
            throw new IllegalArgumentException("Unknown Index flavour type passed : "+indexFlavour);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.indexgenerator.IndexGeneratorService#getIndexLocation()
     */
    public URL getIndexLocation() {
        return indexLocation;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.indexgenerator.IndexGeneratorService#setIndexLocation(java.net.URL)
     */
    public void setIndexLocation(URL indexLocation) {
        this.indexLocation = indexLocation;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.indexgenerator.IndexGeneratorService#setTerminologyConceptDAO(uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO)
     */
    public synchronized void setTerminologyConceptDAO(TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologyConceptDAO = terminologyConceptDAO;
    }

    /**
     * Sets the data source.
     *
     * @param dataSource the new data source
     */
    public synchronized void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
