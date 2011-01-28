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
package uk.nhs.cfh.dsp.yasb.concept.hierarchy.panel.actions;

import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.yasb.concept.hierarchy.panel.model.SnomedConceptHierarchyTreeModel;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link org.jdesktop.application.Task} that populates a {@link javax.swing.tree.TreeModel}
 * with a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 24, 2010 at 3:14:19 PM
 */
public class PopulateHierarchyTreeModelTask extends Task<Void, Void> {

    /** The root concept. */
    private SnomedConcept rootConcept;
    
    /** The tree model. */
    private SnomedConceptHierarchyTreeModel treeModel;

    /**
     * Instantiates a new populate hierarchy tree model task.
     *
     * @param application the application
     * @param rootConcept the root concept
     * @param treeModel the tree model
     */
    public PopulateHierarchyTreeModelTask(Application application, SnomedConcept rootConcept,
                                          SnomedConceptHierarchyTreeModel treeModel) {
        super(application);
        this.rootConcept = rootConcept;
        this.treeModel = treeModel;
    }

    /* (non-Javadoc)
     * @see org.jdesktop.swingworker.SwingWorker#doInBackground()
     */
    @Override
    protected Void doInBackground() throws Exception {
        // populate treeModel
        treeModel.populateFields(rootConcept);

        return null;
    }
}
