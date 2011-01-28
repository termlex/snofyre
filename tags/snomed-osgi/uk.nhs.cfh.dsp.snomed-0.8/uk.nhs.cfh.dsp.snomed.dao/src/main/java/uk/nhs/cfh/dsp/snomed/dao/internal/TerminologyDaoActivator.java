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
package uk.nhs.cfh.dsp.snomed.dao.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

// TODO: Auto-generated Javadoc
/**
 * /**
 * Extension of the default OSGi bundle activator
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 11, 2010 at 9:35:37 PM.
 */

public final class TerminologyDaoActivator implements BundleActivator{
    
        /** The logger. */
    private static Log logger = LogFactory.getLog(TerminologyDaoActivator.class);
    
    /**
     * Called whenever the OSGi framework starts our bundle.
     *
     * @param bc the bc
     * @throws Exception the exception
     */
    public void start( BundleContext bc ) throws Exception
    {
        String configLocation = "file://"+System.getProperty("settings.folder.location")+"snomed-db.properties";
        logger.debug("configLocation = " + configLocation);
        logger.info( "STARTING Terminology DAO Service" );
    }

    /**
     * Called whenever the OSGi framework stops our bundle.
     *
     * @param bc the bc
     * @throws Exception the exception
     */
    public void stop( BundleContext bc ) throws Exception
    {
        logger.info( "STOPPED Terminology DAO Service" );
    }
}