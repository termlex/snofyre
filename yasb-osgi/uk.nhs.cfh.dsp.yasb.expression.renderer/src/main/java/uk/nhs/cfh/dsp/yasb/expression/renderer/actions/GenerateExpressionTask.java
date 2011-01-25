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
package uk.nhs.cfh.dsp.yasb.expression.renderer.actions;

import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.yasb.expression.renderer.model.SnomedNormalFormExpressionTreeModel;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link org.jdesktop.application.Task} that generates a
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression} version of  a given
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} using a
 * {@link uk.nhs.cfh.dsp.snomed.converters.concept.tree.SnomedConceptTreeExpressionProvider}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 24, 2010 at 3:39:48 PM
 */
public class GenerateExpressionTask extends Task<Void, Void> {

    /** The normal form generator. */
    private NormalFormGenerator normalFormGenerator;
    
    /** The close to user expression. */
    private CloseToUserExpression closeToUserExpression;
    
    /** The tree model. */
    private SnomedNormalFormExpressionTreeModel treeModel;
    
    /** The render short normal form. */
    private boolean renderShortNormalForm = false;

    /**
     * Instantiates a new generate expression task.
     *
     * @param application the application
     * @param closeToUserExpression the close to user expression
     * @param normalFormGenerator the normal form generator
     * @param treeModel the tree model
     * @param renderShortNormalForm the render short normal form
     */
    public GenerateExpressionTask(Application application, CloseToUserExpression closeToUserExpression,
                                  NormalFormGenerator normalFormGenerator,
                                  SnomedNormalFormExpressionTreeModel treeModel, boolean renderShortNormalForm) {
        super(application);
        this.treeModel = treeModel;
        this.normalFormGenerator = normalFormGenerator;
        this.closeToUserExpression = closeToUserExpression;
        this.renderShortNormalForm = renderShortNormalForm;
    }

    /* (non-Javadoc)
     * @see org.jdesktop.swingworker.SwingWorker#doInBackground()
     */
    @Override
    protected Void doInBackground() throws Exception {

        if(closeToUserExpression != null)
        {
            NormalFormExpression nfe;
            if (! renderShortNormalForm)
            {
                nfe = normalFormGenerator.getLongNormalFormExpression(closeToUserExpression);
            }
            else
            {
                nfe = normalFormGenerator.getShortNormalFormExpression(closeToUserExpression);
            }

            // set as root in treeModel 
            treeModel.setNormalFormExpression(nfe);
        }

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}