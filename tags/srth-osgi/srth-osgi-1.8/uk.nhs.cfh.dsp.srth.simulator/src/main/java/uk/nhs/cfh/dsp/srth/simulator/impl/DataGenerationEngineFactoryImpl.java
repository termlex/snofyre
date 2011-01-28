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
package uk.nhs.cfh.dsp.srth.simulator.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import uk.nhs.cfh.dsp.srth.simulator.DataGenerationEngineFactory;
import uk.nhs.cfh.dsp.srth.simulator.engine.DataGenerationEngine;
import uk.nhs.cfh.dsp.srth.simulator.engine.DataGenerationEngine.DataGenerationSource;

import javax.sql.DataSource;
import java.io.File;

/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.simulator.DataGenerationEngineFactory}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jun 10, 2009 at 4:18:22 PM
 * <br>
 */
public class DataGenerationEngineFactoryImpl implements DataGenerationEngineFactory {

	/** The logger. */
	private static Log logger = LogFactory.getLog(DataGenerationEngineFactoryImpl.class);
    private DataGenerationEngine queryBasedDataGenerationEngine;
    private PersistenceUnitManager persistenceUnitManager;
    private DataSource dataSource;
    private String xmlLocation ;

    /**
     * no args constructor for IOC
     */
    public DataGenerationEngineFactoryImpl() {

    }

    /**
	 * Gets the data generation engine.
	 * 
	 * @param source the source
	 *
	 * @return the data generation engine
	 */
	public DataGenerationEngine getDataGenerationEngine(DataGenerationSource source){
		
		logger.debug("Source passed : " + source);
		if(source == DataGenerationSource.QUERY)
		{			
			return queryBasedDataGenerationEngine;
		}
		else if(source == DataGenerationSource.RULES)
		{
			// not implemented
			logger.warn("Not implemented a rules based data generation engine.");
			return null;
		}
		else
		{
			logger.warn("Unknown or unsupported source passed!. Source : "+source);
			return null;
		}
	}


    public void recreateDatabase(){
        try
        {
//            Map<String, String> connectionMap = new HashMap<String, String>();
//            connectionMap.put("hibernate.hbm2ddl.auto", "update");
//            EntityManagerFactory emf = Persistence.createEntityManagerFactory("simulatorjpa", connectionMap);
//            emf.close();
            logger.info("Starting purge of database...");
            persistenceUnitManager.obtainPersistenceUnitInfo("simulatorjpa");
            logger.info("persistenceUnitManager = " + persistenceUnitManager);
            LocalContainerEntityManagerFactoryBean lemf = new LocalContainerEntityManagerFactoryBean();
            lemf.setPersistenceUnitManager(persistenceUnitManager);
            logger.info("xmlLocation = " + xmlLocation);
            File f = new File(xmlLocation);
            logger.info("f.exists() = " + f.exists());
            logger.info("f.getAbsolutePath() = " + f.getAbsolutePath());
            logger.info("dataSource = " + dataSource);
//            lemf.setPersistenceXmlLocation(xmlLocation);
//            lemf.setDataSource(dataSource);
            logger.info("lemf.getPersistenceUnitName() = " + lemf.getPersistenceUnitName());
            lemf.getNativeEntityManagerFactory().close();
            logger.info("Finished recreating database.");
        }
        catch (ExceptionInInitializerError e) {
            logger.warn("Error recreating database. Nested exception is : " + e.fillInStackTrace());
        }
    }

    public void setQueryBasedDataGenerationEngine(DataGenerationEngine queryBasedDataGenerationEngine) {
        this.queryBasedDataGenerationEngine = queryBasedDataGenerationEngine;
    }

    public void setPersistenceUnitManager(PersistenceUnitManager persistenceUnitManager) {
        this.persistenceUnitManager = persistenceUnitManager;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setXmlLocation(String xmlLocation) {
        this.xmlLocation = xmlLocation;
    }
}
