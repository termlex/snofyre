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
package uk.nhs.cfh.dsp.yasb.expression.renderer.viewcomponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractViewComponent;
import uk.nhs.cfh.dsp.yasb.expression.renderer.SnomedNormalFormExpressionTreeRendererPanel;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent} that contains
 * a {@link uk.nhs.cfh.dsp.yasb.expression.renderer.SnomedNormalFormExpressionTreeRendererPanel}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 12, 2010 at 3:40:29 PM
 */
public class SnomedExpressionTreeRendererViewComponent extends AbstractViewComponent{

    /** The property change tracker service. */
    private PropertyChangeTrackerService propertyChangeTrackerService;
    
    /** The view component service. */
    private ViewComponentService viewComponentService;
    
    /** The snomed normal form expression tree renderer panel. */
    private SnomedNormalFormExpressionTreeRendererPanel snomedNormalFormExpressionTreeRendererPanel;
    
    /** The normal form generator. */
    private NormalFormGenerator normalFormGenerator;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(SnomedExpressionTreeRendererViewComponent.class);

    /**
     * Instantiates a new snomed expression tree renderer view component.
     *
     * @param propertyChangeTrackerService the property change tracker service
     * @param viewComponentService the view component service
     * @param normalFormGenerator the normal form generator
     * @param applicationService the application service
     */
    public SnomedExpressionTreeRendererViewComponent(PropertyChangeTrackerService propertyChangeTrackerService,
                                                     ViewComponentService viewComponentService,
                                                     NormalFormGenerator normalFormGenerator,
                                                     ApplicationService applicationService) {
        super("Normal Form Expression Renderer", Alignment.BOTTOM, null);
        this.propertyChangeTrackerService = propertyChangeTrackerService;
        this.viewComponentService = viewComponentService;
        this.normalFormGenerator = normalFormGenerator;
        this.applicationService = applicationService;
    }

    /**
     * Empty constructor for IOC.
     */
    public SnomedExpressionTreeRendererViewComponent() {
        super("Normal Form Expression Renderer", Alignment.BOTTOM, null);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent#init()
     */
    public void init() {
        logger.info("Starting module  : "+getName());
        // create snomedNormalFormExpressionTreeRendererPanel and register with services
        snomedNormalFormExpressionTreeRendererPanel =
                new SnomedNormalFormExpressionTreeRendererPanel(normalFormGenerator, applicationService);
        snomedNormalFormExpressionTreeRendererPanel.initComponents();
        propertyChangeTrackerService.registerListener(snomedNormalFormExpressionTreeRendererPanel);

        // set snomedNormalFormExpressionTreeRendererPanel as component
        setComponent(snomedNormalFormExpressionTreeRendererPanel);
        // register self with viewComponentService
        viewComponentService.registerViewComponent(this);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent#dispose()
     */
    public void dispose() {
        // unregister self and snomedNormalFormExpressionTreeRendererPanel  from services
        propertyChangeTrackerService.unregisterListener(snomedNormalFormExpressionTreeRendererPanel);
        viewComponentService.unregisterViewComponent(this);
        logger.info("Stopped module  : "+getName());
    }

    /**
     * Sets the property change tracker service.
     *
     * @param propertyChangeTrackerService the new property change tracker service
     */
    public void setPropertyChangeTrackerService(PropertyChangeTrackerService propertyChangeTrackerService) {
        this.propertyChangeTrackerService = propertyChangeTrackerService;
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
     * Sets the normal form generator.
     *
     * @param normalFormGenerator the new normal form generator
     */
    public void setNormalFormGenerator(NormalFormGenerator normalFormGenerator) {
        this.normalFormGenerator = normalFormGenerator;
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
