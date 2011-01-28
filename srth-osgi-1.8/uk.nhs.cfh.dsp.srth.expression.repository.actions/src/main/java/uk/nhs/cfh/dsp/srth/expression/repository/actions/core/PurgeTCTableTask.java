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
package uk.nhs.cfh.dsp.srth.expression.repository.actions.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.Task;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO;

import java.util.logging.Level;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link org.jdesktop.application.Task} that purges the transitive closure table using
 * the {@link uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Apr 10, 2010 at 3:37:20 PM
 */
public class PurgeTCTableTask extends Task<Void, Void>{

    /** The expression subsumption relationship dao. */
    private ExpressionSubsumptionRelationshipDAO expressionSubsumptionRelationshipDAO;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(PurgeTCTableTask.class);

    /**
     * Instantiates a new purge tc table task.
     *
     * @param applicationService the application service
     * @param expressionSubsumptionRelationshipDAO the expression subsumption relationship dao
     */
    public PurgeTCTableTask(ApplicationService applicationService,
                            ExpressionSubsumptionRelationshipDAO expressionSubsumptionRelationshipDAO) {
        super(applicationService.getActiveApplication());
        this.expressionSubsumptionRelationshipDAO = expressionSubsumptionRelationshipDAO;
        this.applicationService = applicationService;
    }

    /* (non-Javadoc)
     * @see org.jdesktop.swingworker.SwingWorker#doInBackground()
     */
    @Override
    protected Void doInBackground() throws Exception {
        expressionSubsumptionRelationshipDAO.deleteAll();
        return null;
    }

    /* (non-Javadoc)
     * @see org.jdesktop.application.Task#succeeded(T)
     */
    @Override
    protected void succeeded(Void arg0) {
        setMessage("Successfully purged transitive closure table");
    }

    /* (non-Javadoc)
     * @see org.jdesktop.application.Task#failed(java.lang.Throwable)
     */
    @Override
    protected void failed(Throwable e) {
        String simpleMessage ="Error transitive closure table";
        setMessage(simpleMessage);
        applicationService.notifyError(simpleMessage, e, Level.WARNING);
        logger.warn(simpleMessage+"Nested exception is : " + e.fillInStackTrace().getMessage());
    }
}
