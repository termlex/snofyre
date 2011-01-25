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
import uk.nhs.cfh.dsp.snomed.objectmodel.ComponentStatus;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider;

import java.io.File;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 23, 2009 at 9:49:17 PM
 */
public abstract class AbstractTerminologySearchServiceProvider implements TerminologySearchServiceProvider{

    /** The service name. */
    private String serviceName;
    
    /** The service version. */
    private String serviceVersion;
    
    /** The terminology name. */
    private String terminologyName;
    
    /** The terminology version. */
    private String terminologyVersion;
    
    /** The supported algorithms. */
    private Set<TerminologySearchServiceProvider.SearchAlgorithm> supportedAlgorithms;
    
    /** The default service provider. */
    private boolean defaultServiceProvider = false;
    
    /** The max results size. */
    private int maxResultsSize = 100;
    
    /** The index location. */
    private String indexLocation;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(AbstractTerminologySearchServiceProvider.class);

    /**
     * Instantiates a new abstract terminology search service provider.
     *
     * @param serviceName the service name
     * @param serviceVersion the service version
     */
    public AbstractTerminologySearchServiceProvider(String serviceName, String serviceVersion) {
        this.serviceName = serviceName;
        this.serviceVersion = serviceVersion;
        supportedAlgorithms = new HashSet<TerminologySearchServiceProvider.SearchAlgorithm>();
    }

    /**
     * Empty constructor for IOC.
     */
    public AbstractTerminologySearchServiceProvider() {
    }

    /**
     * Inits the.
     */
    public abstract void init();

    /**
     * Dispose.
     */
    public abstract void dispose();

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#getServiceName()
     */
    public String getServiceName() {
        return serviceName;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#setServiceName(java.lang.String)
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#getServiceVersion()
     */
    public String getServiceVersion() {
        return serviceVersion;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#setServiceVersion(java.lang.String)
     */
    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#getTerminologyName()
     */
    public String getTerminologyName() {
        return terminologyName;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#setTerminologyName(java.lang.String)
     */
    public void setTerminologyName(String terminologyName) {
        this.terminologyName = terminologyName;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#getTerminologyVersion()
     */
    public String getTerminologyVersion() {
        return terminologyVersion;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#setTerminologyVersion(java.lang.String)
     */
    public void setTerminologyVersion(String terminologyVersion) {
        this.terminologyVersion = terminologyVersion;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#getSupportedAlgorithms()
     */
    public Set<TerminologySearchServiceProvider.SearchAlgorithm> getSupportedAlgorithms() {
        return supportedAlgorithms;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#setSupportedAlgorithms(java.util.Set)
     */
    public void setSupportedAlgorithms(Set<TerminologySearchServiceProvider.SearchAlgorithm> supportedAlgorithms) {
        this.supportedAlgorithms = supportedAlgorithms;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#addSupportedAlgorithm(SearchAlgorithm)
     */
    public void addSupportedAlgorithm(TerminologySearchServiceProvider.SearchAlgorithm searchAlgorithm){
        if(searchAlgorithm != null)
        {
            supportedAlgorithms.add(searchAlgorithm);
        }
        else
        {
            throw new IllegalArgumentException("Search algorithm passed can not be null");
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#removeSupportedAlgorithm(SearchAlgorithm)
     */
    public void removeSupportedAlgorithm(TerminologySearchServiceProvider.SearchAlgorithm searchAlgorithm){
        if(searchAlgorithm != null)
        {
            supportedAlgorithms.remove(searchAlgorithm);
        }
        else
        {
            throw new IllegalArgumentException("Search algorithm passed can not be null");
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#getMatchesForTerm(SearchAlgorithm, String, ComponentStatus, ConceptType)
     */
    public List<? extends Object> getMatchesForTerm(SearchAlgorithm algorithm,
                                                    String searchTerm, ComponentStatus componentStatus, ConceptType conceptType){
        throw new UnsupportedOperationException("This method is not supported");
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#getMatchesForID(SearchAlgorithm, String, ComponentStatus, ConceptType)
     */
    public List<? extends Object> getMatchesForID(SearchAlgorithm algorithm,
                                                  String conceptId, ComponentStatus conceptStatus, ConceptType conceptType){
        throw new UnsupportedOperationException("This method is not supported");
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#isDefaultServiceProvider()
     */
    public boolean isDefaultServiceProvider() {
        return defaultServiceProvider;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#setDefaultServiceProvider(boolean)
     */
    public void setDefaultServiceProvider(boolean defaultServiceProvider) {
        this.defaultServiceProvider = defaultServiceProvider;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#getMaxResultsSize()
     */
    public synchronized int getMaxResultsSize() {
        return maxResultsSize;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#setMaxResultsSize(int)
     */
    public synchronized void setMaxResultsSize(int maxResultsSize) {
        this.maxResultsSize = maxResultsSize;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#getIndexLocation()
     */
    public String getIndexLocation() {
        return indexLocation;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider#setIndexLocation(java.lang.String)
     */
    public synchronized void setIndexLocation(String indexLocation) {
        File file = new File(indexLocation);
        if(file.exists() && file.canRead())        {
            this.indexLocation = indexLocation;
        }else{
            logger.warn("Error location file specified by index location. Index Location specified: "+indexLocation);
        }
    }
}
