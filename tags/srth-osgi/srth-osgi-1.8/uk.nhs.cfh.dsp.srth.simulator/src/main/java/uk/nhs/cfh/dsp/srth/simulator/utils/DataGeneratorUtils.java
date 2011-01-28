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
package uk.nhs.cfh.dsp.srth.simulator.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLConverter;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.simulator.DataGenerationEngineFactory;
import uk.nhs.cfh.dsp.srth.simulator.engine.DataGenerationEngine;
import uk.nhs.cfh.dsp.srth.simulator.engine.DataGenerationEngine.DataGenerationSource;
import uk.nhs.cfh.dsp.srth.simulator.engine.impl.QueryBasedDataGenerationEngineImpl;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * A class that implements some utility methods for data generation.
 *
 * <br> See also {@link DataGenerationEngine}, {@link QueryBasedDataGenerationEngineImpl}
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jun 11, 2009 at 2:59:21 PM
 * <br>
 */
public class DataGeneratorUtils {

    /** The logger. */
    private static Log logger = LogFactory.getLog(DataGeneratorUtils.class);
    private DataGenerationEngineFactory dataGenerationEngineFactory;

    public DataGeneratorUtils(DataGenerationEngineFactory dataGenerationEngineFactory) {
        this.dataGenerationEngineFactory = dataGenerationEngineFactory;
    }

    /**
     * No args constructor for IOC
     */
    public DataGeneratorUtils() {
    }

    /**
     * Generate data from query with generation time.
     *
     * @param query the query
     *
     * @return the long
     */
    public long generateDataFromQueryWithGenerationTime(QueryStatement query){

        long startTime = Calendar.getInstance().getTimeInMillis();
        logger.info("Started data generation at : "+Calendar.getInstance().getTime());
        // use query based data generation to generate data
        DataGenerationEngine engine =
                dataGenerationEngineFactory.getDataGenerationEngine(DataGenerationSource.QUERY);

        engine.generateDataForQuery(query);
        logger.info("Finished data generation at : "+Calendar.getInstance().getTime());
        // get finish time
        long dataGenTime = Calendar.getInstance().getTimeInMillis() - startTime;

        return dataGenTime;
    }

    /**
     * Generate data from query with generation time.
     *
     * @param file the file
     *
     * @return the long
     */
    public long generateDataFromQueryWithGenerationTime(File file,
                                                               QueryExpressionXMLConverter queryExpressionXMLConverter){

        long time = 0;
        // load query from file
        SAXBuilder builder = new SAXBuilder(false);
        try
        {
            Document doc = builder.build(file);
            QueryStatement query = queryExpressionXMLConverter.getQueryStatementFromElement(doc.getRootElement());
            if(query != null)
            {
                return generateDataFromQueryWithGenerationTime(query);
            }
            else
            {
                logger.warn("Unable to load query from file");
            }
        }
        catch (JDOMException e) {
            logger.warn("Error parsing file. Nested exception is : "+e.fillInStackTrace());
        }
        catch (IOException e) {
            logger.warn("Error reading file. Nested exception message is : "+e.fillInStackTrace());
        }

        return time;
    }

    public void setDataGenerationEngineFactory(DataGenerationEngineFactory dataGenerationEngineFactory) {
        this.dataGenerationEngineFactory = dataGenerationEngineFactory;
    }
}
