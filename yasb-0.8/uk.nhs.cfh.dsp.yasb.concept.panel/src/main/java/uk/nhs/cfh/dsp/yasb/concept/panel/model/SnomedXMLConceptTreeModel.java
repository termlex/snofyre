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
package uk.nhs.cfh.dsp.yasb.concept.panel.model;

import org.jdom.Element;
import uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A {@link javax.swing.tree.TreeModel} that is based on the XML representation of
 * a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 4, 2010 at 7:43:57 PM
 */
public class SnomedXMLConceptTreeModel implements TreeModel{

    /** The listeners. */
    private List<TreeModelListener> listeners = new ArrayList<TreeModelListener>();
    
    /** The root element. */
    private Element rootElement;
    
    /** The root concept. */
    private SnomedConcept rootConcept;
    
    /** The snomed xml converter. */
    private SnomedXMLConverter snomedXMLConverter;

    /**
     * Instantiates a new snomed xml concept tree model.
     *
     * @param snomedXMLConverter the snomed xml converter
     */
    public SnomedXMLConceptTreeModel(SnomedXMLConverter snomedXMLConverter) {
        if (snomedXMLConverter != null)
        {
            this.snomedXMLConverter = snomedXMLConverter;
        }
        else
        {
            throw new IllegalArgumentException("XML Converter passed can not be null.");
        }
    }

    /**
     * Sets the root.
     *
     * @param concept the new root
     */
    public void setRoot(SnomedConcept concept){
        if(concept != null && ! concept.equals(rootConcept))
        {
            Element newRootElement = snomedXMLConverter.getElementForConcept(concept);
            // set concept to root
            rootConcept = concept;

            // notify listeners
            if(newRootElement != null)
            {
                rootElement = newRootElement;
                fireTreeStructureChanged(newRootElement);
            }
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getRoot()
     */
    public Object getRoot() {
        return rootElement;
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
     */
    public Object getChild(Object parent, int index) {
        return ((Element)parent).getChildren().get(index);
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object parent) {
        return ((Element)parent).getChildren().size();
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
     */
    public boolean isLeaf(Object node) {
        Element element = (Element) node;
        return element.getChildren().size() == 0;
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
     */
    public void valueForPathChanged(TreePath path, Object newValue) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
     */
    public int getIndexOfChild(Object parent, Object child) {
        Element parentElement = (Element) parent;
        Element childElement = (Element) child;
        return parentElement.getChildren().indexOf(childElement);
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#addTreeModelListener(javax.swing.event.TreeModelListener)
     */
    public void addTreeModelListener(TreeModelListener l) {
        if(l != null)
        {
            listeners.add(l);
        }
        else
        {
            throw new IllegalArgumentException("Listener passed can not be null.");
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#removeTreeModelListener(javax.swing.event.TreeModelListener)
     */
    public void removeTreeModelListener(TreeModelListener l) {
        if(l != null)
        {
            listeners.remove(l);
        }
        else
        {
            throw new IllegalArgumentException("Listener passed can not be null.");
        }
    }

    /**
     * Fire tree structure changed.
     *
     * @param oldRoot the old root
     */
    protected void fireTreeStructureChanged(Element oldRoot) {

        TreeModelEvent e = new TreeModelEvent(this, new Object[] {oldRoot});
        for (TreeModelListener tml : listeners)
        {
            tml.treeStructureChanged(e);
        }
    }
}
