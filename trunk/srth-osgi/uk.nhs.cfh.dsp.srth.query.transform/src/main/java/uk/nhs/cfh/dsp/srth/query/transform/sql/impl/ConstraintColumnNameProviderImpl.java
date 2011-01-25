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
package uk.nhs.cfh.dsp.srth.query.transform.sql.impl;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint;
import uk.nhs.cfh.dsp.srth.query.transform.sql.ConstraintColumnNameProvider;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.transform.sql.ConstraintColumnNameProvider}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 22, 2010 at 2:48:37 PM
 */
public class ConstraintColumnNameProviderImpl implements ConstraintColumnNameProvider {

    /** The column map. */
    private Map<String, String> columnMap = new HashMap<String, String>();
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(ConstraintColumnNameProviderImpl.class);
    private URL configFileURL;

    /**
     * empty no args constructor for IOC
     */
    public ConstraintColumnNameProviderImpl() {
        // empty constructor
    }


    /**
     * Configure map.
     *
     */
    public synchronized void configureMap(){
        if(configFileURL != null)
        {
            configureMap(configFileURL);
        }
    }

    /**
     * Configure map.
     *
     * @param configFileURL the config file's URL
     */
    private void configureMap(URL configFileURL){

        try
        {
            URI fileURI = configFileURL.toURI();
            File file = new File(fileURI);
            if (file.exists())
            {
                XMLConfiguration configuration = new XMLConfiguration(file);
                configuration.setExpressionEngine(new XPathExpressionEngine());
                List fields = configuration.configurationsAt("mapped_columns.mapped_column");
                for (Object field : fields) {
                    HierarchicalConfiguration sub = (HierarchicalConfiguration) field;
                    // sub contains now all data about a single field
                    String constraintName = sub.getString("name");
                    String columnName = sub.getString("column");

                    // put values in map
                    columnMap.put(constraintName, columnName);
                }
            }
            else
            {
                logger.warn("Error reading file from provided URL.");
                throw new IllegalArgumentException("Error reading file from provided URL.");
            }
        }
        catch (URISyntaxException e) {
            logger.warn("Error reading file from provided URL. Nested exception is : " + e.fillInStackTrace().getMessage());
        }
        catch (ConfigurationException e) {
            logger.warn("Error reading configuration file. Nested exception is : " + e.fillInStackTrace().getMessage());
        }
    }

    /**
     * Gets the column name.
     * 
     * @param constraint the constraint
     * 
     * @return the column name
     */
    public String getColumnName(Constraint constraint){
        if(constraint != null && constraint.getName() != null)
        {
            return getColumnName(constraint.getName());
        }
        else
        {
            throw new IllegalArgumentException("Constraint passed must not be null and must have a name.");
        }
    }

    /**
     * Gets the column name.
     * 
     * @param constraintName the constraint name
     * 
     * @return the column name
     */
    public String getColumnName(String constraintName){
        if(columnMap.containsKey(constraintName))
        {
            return columnMap.get(constraintName);
        }
        else
        {
            throw new IllegalArgumentException("Constraint Name specified does not exist. Name : "+constraintName);
        }
    }

    /**
     * Gets the column map.
     * 
     * @return the column map
     */
    public Map<String, String> getColumnMap() {
        return columnMap;
    }

    /**
     * Sets the column map.
     * 
     * @param columnMap the column map
     */
    public void setColumnMap(Map<String, String> columnMap) {
        this.columnMap = columnMap;
    }

    public synchronized void setConfigFileURL(URL configFileURL) {
        this.configFileURL = configFileURL;
        logger.info("configFileURL = " + configFileURL);
        configureMap(configFileURL);
    }
}
