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
package uk.nhs.cfh.dsp.yasb.indexgenerator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jdesktop.swingworker.SwingWorker;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.error.ConceptNotFoundException;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


// TODO: Auto-generated Javadoc
/**
 * This class generates a {@link Directory} index for Snomed concepts
 * Written by @author jay
 * Created on Dec 22, 2008 at 5:05:57 PM.
 */
public class SnomedLuceneDescriptionIndexer extends SwingWorker<Void, Void> {

    /** The directory. */
    private Directory directory;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(SnomedLuceneDescriptionIndexer.class);
    
    /** The connection. */
    private Connection connection;
    
    /** The index writer. */
    private IndexWriter indexWriter;
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;

    /**
     * Instantiates a new snomed lucene description indexer.
     *
     * @param dataSource the data source
     * @param terminologyConceptDAO the terminology concept dao
     */
    public SnomedLuceneDescriptionIndexer(DataSource dataSource,
                                          TerminologyConceptDAO terminologyConceptDAO) {

        try
        {
            this.connection = dataSource.getConnection();
            this.terminologyConceptDAO = terminologyConceptDAO;

            // create directory;
            try
            {
                directory = FSDirectory.getDirectory(System.getProperty("user.dir")+"/index/snomed-desc/lucene");
                logger.debug("Value of directory : " + directory);
                indexWriter = new IndexWriter(directory, new StandardAnalyzer(), true, IndexWriter.MaxFieldLength.UNLIMITED);

            } catch (IOException e) {
                logger.warn("Error locating location of index directory.\n" +
                        "Nested exception is : " + e.fillInStackTrace());
            }
        }
        catch (SQLException e) {
            logger.warn("Error obtaining connection from data source.\n" +
                    "Nested exception is : " + e.fillInStackTrace());
        }
    }

    /**
     * Index concepts.
     */
    public void indexConcepts(){

        try
        {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("" +
                    "SELECT DISTINCT DESCRIPTIONID, CONCEPTID, TERM, DESCRIPTIONTYPE FROM SNOMED.TERM");
            int counter = 1;
            while(rs.next())
            {
                String conceptID = rs.getString(2);
                String descId = rs.getString(1);
                String term = rs.getString(3);
                String descType = rs.getString(4);

                SnomedConcept concept = (SnomedConcept) terminologyConceptDAO.getTerminologyConcept(conceptID);

                // get attributes of concept and add to lucene document
                Document document = new Document();
                String source = concept.getSource();
                String status = concept.getStatus().name();
                String conceptType = concept.getType().name();
                // replace all spaces in concept type with underscores
                conceptType = conceptType.replaceAll(" ", "_");

                document.add(new Field("CONCEPTID", conceptID, Store.COMPRESS, Index.ANALYZED));
                document.add(new Field("DESCRIPTIONID", descId, Store.COMPRESS, Index.ANALYZED));
                document.add(new Field("SOURCE", source, Store.NO, Index.ANALYZED));
                document.add(new Field("STATUS", status, Store.COMPRESS, Index.ANALYZED));
                document.add(new Field("TERM", term, Store.COMPRESS, Index.ANALYZED));
                document.add(new Field("CONCEPTTYPE", conceptType, Store.NO, Index.ANALYZED));
                document.add(new Field("DESCRIPTIONTYPE", descType, Store.NO, Index.ANALYZED));

                // add document to index
                indexWriter.addDocument(document);

                // debuggin messages
                if(logger.isDebugEnabled() && counter % 1000 == 0)
                {
                    logger.debug("Concepts indexed : " + counter);
                }
                // increment counter
                counter++;
            }

            // close result set and statement
            rs.close();
            statement.close();

            // optimise index
            indexWriter.optimize();
            indexWriter.commit();

        } catch (SQLException e) {
            logger.warn(e.fillInStackTrace());
        } catch (ConceptNotFoundException e) {
            logger.warn(e.fillInStackTrace());
        } catch (CorruptIndexException e) {
            logger.warn(e.fillInStackTrace());
        } catch (IOException e) {
            logger.warn(e.fillInStackTrace());
        }
    }


    /* (non-Javadoc)
     * @see org.jdesktop.swingworker.SwingWorker#doInBackground()
     */
    @Override
    protected Void doInBackground() throws Exception {

        // index concepts
        logger.info("Started indexing concepts");
        indexConcepts();

        return null;
    }

    /* (non-Javadoc)
     * @see org.jdesktop.swingworker.SwingWorker#done()
     */
    @Override
    protected void done() {

        // close index writer
        try
        {
            indexWriter.close();
        } catch (CorruptIndexException e) {
            logger.warn(e.fillInStackTrace());
        } catch (IOException e) {
            logger.warn(e.fillInStackTrace());
        }
        logger.info("Finished creating index");
    }

    /**
     * Gets the directory.
     *
     * @return the directory
     */
    public Directory getDirectory() {
        return directory;
    }
}

