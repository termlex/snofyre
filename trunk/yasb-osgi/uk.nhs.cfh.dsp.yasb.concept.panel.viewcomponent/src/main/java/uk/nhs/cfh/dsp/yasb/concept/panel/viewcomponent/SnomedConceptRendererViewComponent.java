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
package uk.nhs.cfh.dsp.yasb.concept.panel.viewcomponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractViewComponent;
import uk.nhs.cfh.dsp.yasb.concept.panel.SnomedConceptRendererPanel;

/**
 * A custom {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent} that contains a
 * {@link uk.nhs.cfh.dsp.yasb.concept.panel.SnomedConceptRendererPanel}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 7, 2010 at 12:25:39 PM
 */
public class SnomedConceptRendererViewComponent extends AbstractViewComponent{

    private SnomedConceptRendererPanel snomedConceptRendererPanel;
    private ViewComponentService viewComponentService;
    private TerminologyConceptDAO terminologyConceptDAO;
    private ApplicationService applicationService;
    private SelectionService selectionService;
    private NormalFormGenerator normalFormGenerator;
    private PropertyChangeTrackerService propertyChangeTrackerService;
    private static Log logger = LogFactory.getLog(SnomedConceptRendererViewComponent.class);

    public SnomedConceptRendererViewComponent() {
        super("Snomed Concept Renderer", Alignment.RIGHT, null);
    }

    public void init() {
        logger.info("Starting module : "+getName());
        // create snomedConceptRendererPanel
        snomedConceptRendererPanel = new SnomedConceptRendererPanel(
                terminologyConceptDAO, applicationService, selectionService, propertyChangeTrackerService,
                normalFormGenerator);
        snomedConceptRendererPanel.initComponents();

        // register snomedConceptRendererPanel  as listener with selectionService
        selectionService.registerListener(snomedConceptRendererPanel);
        // set snomedConceptRendererPanel  as component
        setComponent(snomedConceptRendererPanel);
        // register self with viewComponentService
        viewComponentService.registerViewComponent(this);
    }

    public void dispose() {
        // remove snomedConceptRendererPanel  as listener for selectionService
        selectionService.unregisterListener(snomedConceptRendererPanel);
        // unregister self from viewComponentService
        viewComponentService.unregisterViewComponent(this);
        logger.info("Stopped module : "+getName());
    }

    public void setViewComponentService(ViewComponentService viewComponentService) {
        this.viewComponentService = viewComponentService;
    }

    public void setTerminologyConceptDAO(TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologyConceptDAO = terminologyConceptDAO;
    }

    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public void setSelectionService(SelectionService selectionService) {
        this.selectionService = selectionService;
    }

    public void setPropertyChangeTrackerService(PropertyChangeTrackerService propertyChangeTrackerService) {
        this.propertyChangeTrackerService = propertyChangeTrackerService;
    }

    public void setNormalFormGenerator(NormalFormGenerator normalFormGenerator) {
        this.normalFormGenerator = normalFormGenerator;
    }
}
