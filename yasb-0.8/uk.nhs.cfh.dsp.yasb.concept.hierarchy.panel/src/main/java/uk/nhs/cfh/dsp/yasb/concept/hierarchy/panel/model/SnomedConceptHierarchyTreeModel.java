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
package uk.nhs.cfh.dsp.yasb.concept.hierarchy.panel.model;

import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.tree.TreeModel} that renders the hierarchy of a
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept}. Note this class uses getChildIDSet method
 * for some calls to improve performance.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 7, 2010 at 1:54:50 PM
 */
public class SnomedConceptHierarchyTreeModel implements TreeModel{

    /** The root concept. */
    private SnomedConcept rootConcept;
    
    /** The listeners. */
    private List<TreeModelListener> listeners = new ArrayList<TreeModelListener>();
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;
    
    /** The sorted children list. */
    private List<SnomedConcept> sortedChildrenList = new ArrayList<SnomedConcept>();
    
    /** The current concept. */
    private SnomedConcept currentConcept;
    
    /** The sort hierarchy by alphabetical order. */
    private boolean sortHierarchyByAlphabeticalOrder = false;

    /**
     * Instantiates a new snomed concept hierarchy tree model.
     *
     * @param terminologyConceptDAO the terminology concept dao
     */
    public SnomedConceptHierarchyTreeModel(TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologyConceptDAO = terminologyConceptDAO;
    }

    /**
     * Populate fields.
     *
     * @param concept the concept
     */
    public void populateFields(SnomedConcept concept){
        if(concept != null && concept != rootConcept)
        {
            // set new root and notify listeners
            rootConcept = concept;
            fireTreeStructureChanged(rootConcept);
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getRoot()
     */
    public Object getRoot() {
        return rootConcept;
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
     */
    public Object getChild(Object parent, int index) {
        return getChildrenList(parent).get(index);
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object parent) {
        if(parent instanceof SnomedConcept)
        {
            SnomedConcept concept = (SnomedConcept) parent;
            return concept.getChildIDSet().size();
        }
        else
        {
            throw new IllegalArgumentException("Node passed is not a concept.");
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
     */
    public boolean isLeaf(Object node) {
        if(node instanceof SnomedConcept)
        {
            SnomedConcept concept = (SnomedConcept) node;
            return concept.getChildIDSet().size() == 0;
        }
        else
        {
            throw new IllegalArgumentException("Node passed is not a concept.");
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
     */
    public void valueForPathChanged(TreePath path, Object newValue) {
        // not implemented yet!
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
     */
    public int getIndexOfChild(Object parent, Object child) {
        return getChildrenList(parent).indexOf(child);
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#addTreeModelListener(javax.swing.event.TreeModelListener)
     */
    public void addTreeModelListener(TreeModelListener l) {
        if(listeners != null)
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
        if(listeners != null)
        {
            listeners.remove(l);
        }
        else
        {
            throw new IllegalArgumentException("Listener passed can not be null.");
        }
    }

    /**
     * Gets the children list.
     *
     * @param node the node
     * @return the children list
     */
    private List<SnomedConcept> getChildrenList(Object node){
        if(node instanceof SnomedConcept)
        {
            SnomedConcept concept = (SnomedConcept) node;

            // check if we've generated sortedChildrenList for this concept;
            if(concept.equals(currentConcept))
            {
                return sortedChildrenList;
            }
            else
            {
                // set local concept to currentConcept
                currentConcept = concept;

                // clear existing sortedChildrenList
                sortedChildrenList.clear();

                // create sorted set to hold children
                Collection<SnomedConcept> sortedChildren = new ArrayList<SnomedConcept>();
                if(isSortHierarchyByAlphabeticalOrder())
                {
                    sortedChildren =new TreeSet<SnomedConcept>(new SnomedConceptByFSNComparator());
                }

                // populate children from childID list
                for(String childId : concept.getChildIDSet())
                {
                    SnomedConcept childConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO , childId);
                    // add childConcept to unsortedChildren
                    sortedChildren.add(childConcept);
                }

                // set children in concept
                concept.setChildren(sortedChildren);

                // add sortedChildren to sortedChildrenList
                sortedChildrenList.addAll(sortedChildren);

                return sortedChildrenList;
            }
        }
        else
        {
            return Collections.emptyList();
        }
    }

    /**
     * Fire tree structure changed.
     *
     * @param newRoot the new root
     */
    private void fireTreeStructureChanged(SnomedConcept newRoot){

        // get event
        TreeModelEvent event = new TreeModelEvent(this, new Object[]{newRoot});
        // notify listeners
        for(TreeModelListener listener : listeners)
        {
            listener.treeStructureChanged(event);
        }
    }

    /**
     * Gets the root concept.
     *
     * @return the root concept
     */
    public SnomedConcept getRootConcept() {
        return rootConcept;
    }

    /**
     * Checks if is sort hierarchy by alphabetical order.
     *
     * @return true, if is sort hierarchy by alphabetical order
     */
    public boolean isSortHierarchyByAlphabeticalOrder() {
        return sortHierarchyByAlphabeticalOrder;
    }

    /**
     * Sets the sort hierarchy by alphabetical order.
     *
     * @param sortHierarchyByAlphabeticalOrder the new sort hierarchy by alphabetical order
     */
    public void setSortHierarchyByAlphabeticalOrder(boolean sortHierarchyByAlphabeticalOrder) {
        this.sortHierarchyByAlphabeticalOrder = sortHierarchyByAlphabeticalOrder;
    }

    // private inner class for comparing concepts by FSN
    /**
     * The Class SnomedConceptByFSNComparator.
     */
    class SnomedConceptByFSNComparator implements Comparator<SnomedConcept>{

        /* (non-Javadoc)
         * @see java.util.Comparator#compare(T, T)
         */
        public int compare(SnomedConcept o1, SnomedConcept o2) {
            return o1.getFullySpecifiedName().compareTo(o2.getFullySpecifiedName());
        }
    }
}
