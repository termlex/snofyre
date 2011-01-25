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
package uk.nhs.cfh.dsp.yasb.concept.hierarchy.panel.viewcomponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractViewComponent;
import uk.nhs.cfh.dsp.yasb.concept.hierarchy.panel.SnomedConceptHierarchyPanel;

// TODO: Auto-generated Javadoc
/**
 * A {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent} that contains a
 * {@link uk.nhs.cfh.dsp.yasb.concept.hierarchy.panel.SnomedConceptHierarchyPanel}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 7, 2010 at 4:55:01 PM
 */
public class SnomedConceptHierarchyViewComponent extends AbstractViewComponent{

    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;
    
    /** The view component service. */
    private ViewComponentService viewComponentService;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(SnomedConceptHierarchyViewComponent.class);
    
    /** The snomed concept hierarchy panel. */
    private SnomedConceptHierarchyPanel snomedConceptHierarchyPanel;
    
    /** The selection service. */
    private SelectionService selectionService;
    
    /** The application service. */
    private ApplicationService applicationService;

    /**
     * Instantiates a new snomed concept hierarchy view component.
     */
    public SnomedConceptHierarchyViewComponent() {
        super("Snomed Concept Hierarchy", Alignment.RIGHT, null);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent#init()
     */
    public synchronized void init() {
        logger.info("Starting module : "+getName());
        // create snomedConceptHierarchyPanel and set as component
        snomedConceptHierarchyPanel = new SnomedConceptHierarchyPanel(terminologyConceptDAO, selectionService, applicationService);
        snomedConceptHierarchyPanel.initComponents();
        // register snomedConceptHierarchyPanel  with selectionService
        selectionService.registerListener(snomedConceptHierarchyPanel);
        setComponent(snomedConceptHierarchyPanel);
        // register self with viewComponentService
        viewComponentService.registerViewComponent(this);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent#dispose()
     */
    public synchronized void dispose() {
        // unregister snomedConceptHierarchyPanel  from selectionService
        selectionService.unregisterListener(snomedConceptHierarchyPanel);
        // unregister self from viewComponentService
        viewComponentService.unregisterViewComponent(this);
        logger.info("Stopped module : "+getName());
    }

    /**
     * Sets the terminology concept dao.
     *
     * @param terminologyConceptDAO the new terminology concept dao
     */
    public void setTerminologyConceptDAO(TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologyConceptDAO = terminologyConceptDAO;
    }

    /**
     * Sets the view component service.
     *
     * @param viewComponentService the new view component service
     */
    public void setViewComponentService(ViewComponentService viewComponentService) {
        this.viewComponentService = viewComponentService;
    }

    /**
     * Sets the selection service.
     *
     * @param selectionService the new selection service
     */
    public void setSelectionService(SelectionService selectionService) {
        this.selectionService = selectionService;
    }

    /**
     * Sets the application service.
     *
     * @param applicationService the new application service
     */
    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
}
