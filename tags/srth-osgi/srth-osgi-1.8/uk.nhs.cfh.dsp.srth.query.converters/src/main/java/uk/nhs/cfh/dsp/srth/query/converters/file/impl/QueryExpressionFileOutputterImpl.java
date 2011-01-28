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
package uk.nhs.cfh.dsp.srth.query.converters.file.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import uk.nhs.cfh.dsp.srth.query.converters.file.QueryExpressionFileOutputter;
import uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLConverter;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.converters.file.QueryExpressionFileOutputter}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 21, 2010 at 12:21:10 PM
 */
public class QueryExpressionFileOutputterImpl implements QueryExpressionFileOutputter {

    /** The logger. */
    private static Log logger = LogFactory.getLog(QueryExpressionFileOutputterImpl.class);
    
    /** The query expression xml converter. */
    private QueryExpressionXMLConverter queryExpressionXMLConverter;

    /**
     * Instantiates a new query expression file outputter impl.
     *
     * @param queryExpressionXMLConverter the query expression xml converter
     */
    public QueryExpressionFileOutputterImpl(QueryExpressionXMLConverter queryExpressionXMLConverter) {
        this.queryExpressionXMLConverter = queryExpressionXMLConverter;
    }

    /**
     * Empty constructor for IOC.
     */
    public QueryExpressionFileOutputterImpl() {
        // empty constructor for IOC
    }

    /**
     * Save.
     *
     * @param queryStatement the query statement
     */
    public void save(QueryStatement queryStatement){
        // get physicalURL of the query
        URI physicalURI = queryStatement.getPhysicalLocation();
        if(physicalURI != null)
        {
            save(queryStatement, physicalURI);
        }
        else
        {
            logger.warn("Physical URI not associated with query");
        }
    }

    /**
     * Save.
     *
     * @param queryStatement the query statement
     * @param physicalURI the physical uri
     */
    public void save(QueryStatement queryStatement, URI physicalURI){

        if (logger.isDebugEnabled()) {
            logger.debug("Saving query to physicalURI = " + physicalURI);
        }
        // get file location for URL
        File file = new File(physicalURI);
        if(!file.exists())
        {
            try
            {
                boolean success = file.createNewFile();
                if (success)
                {
                    // save to file as XML
                    saveToFile(file, queryExpressionXMLConverter.getElementForQueryStatement(queryStatement));
                }

            } catch (IOException e) {
                logger.warn("Error writing query to file. Nested exception is : " + e.fillInStackTrace().getMessage());
            }
        }
        else
        {
            // save to file as XML
            saveToFile(file, queryExpressionXMLConverter.getElementForQueryStatement(queryStatement));

        }
    }

    /**
     * Save to file.
     *
     * @param file the file
     * @param element the element
     */
    private void saveToFile(File file, Element element){
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        Writer writer;
        try
        {
            writer = new FileWriter(file);
            outputter.output(element, writer);
        } catch (IOException e) {
            logger.warn("Error saving query to file. Nested exception is : " + e.fillInStackTrace().getMessage());
        }
    }

    /**
     * Sets the query expression xml converter.
     *
     * @param queryExpressionXMLConverter the new query expression xml converter
     */
    public void setQueryExpressionXMLConverter(QueryExpressionXMLConverter queryExpressionXMLConverter) {
        this.queryExpressionXMLConverter = queryExpressionXMLConverter;
    }
}
