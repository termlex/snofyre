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
package uk.nhs.cfh.dsp.yasb.expression.builder.model;

import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 23, 2010 at 2:07:59 PM.
 */
public class PropertyExpressionListModel extends AbstractListModel{

    /** The close to user expression. */
    private CloseToUserExpression closeToUserExpression;
    
    /** The property expression. */
    private List<PropertyExpression> propertyExpression = new ArrayList<PropertyExpression>();
    
    /** The normal form generator. */
    private NormalFormGenerator normalFormGenerator;

    /**
     * Instantiates a new property expression list model.
     *
     * @param normalFormGenerator the normal form generator
     */
    public PropertyExpressionListModel(NormalFormGenerator normalFormGenerator) {
        this.normalFormGenerator = normalFormGenerator;
    }

    /* (non-Javadoc)
     * @see javax.swing.ListModel#getSize()
     */
    public int getSize() {
        return propertyExpression.size();
    }

    /* (non-Javadoc)
     * @see javax.swing.ListModel#getElementAt(int)
     */
    public Object getElementAt(int index) {
        return propertyExpression.get(index);
    }

    /**
     * Populate model.
     */
    private void populateModel(){
        propertyExpression.clear();
        // get normal form for close to user expression
        NormalFormExpression nfe = normalFormGenerator.getLongNormalFormExpressionForRendering(getCloseToUserExpression());
        propertyExpression.addAll(nfe.getRelationshipExpressions());
        propertyExpression.addAll(nfe.getRoleGroupExpressions());

        // notify listeners
        fireContentsChanged(this, 0, propertyExpression.size()-1);
    }

    /**
     * Gets the close to user expression.
     *
     * @return the close to user expression
     */
    public CloseToUserExpression getCloseToUserExpression() {
        return closeToUserExpression;
    }

    /**
     * Sets the close to user expression.
     *
     * @param closeToUserExpression the new close to user expression
     */
    public void setCloseToUserExpression(CloseToUserExpression closeToUserExpression) {
        if (closeToUserExpression != null)
        {
            this.closeToUserExpression = closeToUserExpression;
            populateModel();
        }
    }
}
