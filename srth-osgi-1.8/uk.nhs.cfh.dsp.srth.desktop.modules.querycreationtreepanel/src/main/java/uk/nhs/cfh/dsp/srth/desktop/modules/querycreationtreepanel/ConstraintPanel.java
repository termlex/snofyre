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
/**
 * 
 */
package uk.nhs.cfh.dsp.srth.desktop.modules.querycreationtreepanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.*;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl.DataConstantConstraintImpl;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl.DataRangeConstraintImpl;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl.DataRangeFacetConstraintImpl;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * A panel for displaying and authoring a {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint}.
 * <br> Version : @#VersionNumber#@
 * <br> Written by @author Jay Kola
 * <br> Created on Jul 16, 2009 at 11:27:39 AM
 * <br> Modified on Thursday; November 26, 2009 at 11:52 AM
 */

@SuppressWarnings("serial")
public class ConstraintPanel extends JPanel {

	/** The logger. */
	private static Log logger = LogFactory.getLog(ConstraintPanel.class);
	
	/** The constraint. */
	private Constraint constraint;
	
	/** The value spinner1. */
	private JSpinner valueSpinner1;
	
	/** The value spinner2. */
	private JSpinner valueSpinner2;
	
	/** The value spinner check box1. */
	private JCheckBox valueSpinnerCheckBox1;
	
	/** The value spinner check box2. */
	private JCheckBox valueSpinnerCheckBox2;
	
	/** The field box. */
	private JComboBox fieldBox;
	
	/** The operators box. */
	private JComboBox operatorsBox;
	
	/** The selected field. */
	private String selectedField ;
	
	/** The selected operator. */
	private Operator selectedOperator;
	
	/** The value1 inclusive. */
	private boolean value1Inclusive = true;
	
	/** The value2 inclusive. */
	private boolean value2Inclusive = true;
	
	/** The operator facet map. */
	private Map<Operator, RangeFacetVocabulary> operatorFacetMap = new HashMap<Operator, RangeFacetVocabulary>();

	/**
	 * The Enum Operator.
	 */
	private enum Operator{
    EQUAL_TO ,
    LESS_THAN_EXCLUSIVE,
	LESS_THAN_INCLUSIVE,
    GREATER_THAN_INCLUSIVE,
    GREATER_THAN_EXCLUSIVE,
    BETWEEN,
    LESS_THAN,
    GREATER_THAN}

	/**
	 * Instantiates a new constraint panel.
	 * 
	 * @param queryService the query service
	 */
	public ConstraintPanel(QueryService queryService){
		// initialise map
		operatorFacetMap.put(Operator.GREATER_THAN_EXCLUSIVE, RangeFacetVocabulary.MIN_EXCLUSIVE);
		operatorFacetMap.put(Operator.GREATER_THAN_INCLUSIVE, RangeFacetVocabulary.MIN_INCLUSIVE);
		operatorFacetMap.put(Operator.LESS_THAN_EXCLUSIVE, RangeFacetVocabulary.MAX_EXCLUSIVE);
		operatorFacetMap.put(Operator.LESS_THAN_INCLUSIVE, RangeFacetVocabulary.MAX_INCLUSIVE);
		// init components
		initComponents();
	}

	/**
	 * Inits the components.
	 */
	private void initComponents() {

		String[] fields = new String[]{"has_dose", "has_value"};
		fieldBox = new JComboBox(fields);
		fieldBox.addActionListener(new AbstractAction() {

			public void actionPerformed(ActionEvent arg0) {
				// get selected item
				selectedField = fieldBox.getSelectedItem().toString();
			}
		});
		// select has_value by default
		fieldBox.setSelectedItem(fields[1]);
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.LINE_AXIS));
		p1.add(new JLabel("Field Name"));
		p1.add(Box.createHorizontalStrut(10));
		p1.add(fieldBox);

		Operator[] ops = {Operator.EQUAL_TO, Operator.BETWEEN, Operator.LESS_THAN, Operator.GREATER_THAN};
		operatorsBox = new JComboBox(ops);
		// select operator 'equal to' by default
		operatorsBox.setSelectedItem(Operator.EQUAL_TO);
		selectedOperator = Operator.EQUAL_TO;
		operatorsBox.addActionListener(new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				// get selected operator
				selectedOperator = Operator.valueOf(operatorsBox.getSelectedItem().toString());

				if(selectedOperator == Operator.BETWEEN)
				{
					// enable second value spinner
					valueSpinner2.setEnabled(true);
					valueSpinnerCheckBox2.setEnabled(true);
				}
				else if(selectedOperator == Operator.EQUAL_TO)
				{
					// disable value 1 checkbox
					valueSpinnerCheckBox1.setEnabled(false);

					// disable value spinner 2
					valueSpinner2.setEnabled(false);
					valueSpinnerCheckBox2.setEnabled(false);
				}
				else
				{
					// disable value 1 checkbox
					valueSpinnerCheckBox1.setEnabled(true);

					// disable value spinner 2
					valueSpinner2.setEnabled(false);
					valueSpinnerCheckBox2.setEnabled(false);
				}
			}
		});
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.LINE_AXIS));
		p2.add(new JLabel("Operator"));
		p2.add(Box.createHorizontalStrut(20));
		p2.add(operatorsBox);

		// create spinners
		SpinnerNumberModel model1 = new SpinnerNumberModel(0, 0, 10000, 0.1);
		SpinnerNumberModel model2 = new SpinnerNumberModel(0, 0, 10000, 0.1);
		valueSpinner1 = new JSpinner(model1);
		valueSpinner1.setName("valueSpinner1");
		valueSpinner2 = new JSpinner(model2);
		valueSpinner2.setName("valueSpinner2");
		// disable spinner 2 by default
		valueSpinner2.setEnabled(false);
		// create check boxes for inclusive ranges...
		valueSpinnerCheckBox1 = new JCheckBox(new AbstractAction("Inclusive") {

			public void actionPerformed(ActionEvent e) {
				value1Inclusive = valueSpinnerCheckBox1.isSelected();
			}
		});
		valueSpinnerCheckBox2 = new JCheckBox(new AbstractAction("Inclusive") {

			public void actionPerformed(ActionEvent e) {
				value2Inclusive = valueSpinnerCheckBox2.isSelected();
			}
		});
		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.LINE_AXIS));
		p3.add(new JLabel("Lower Bound"));
		p3.add(Box.createHorizontalStrut(10));
		p3.add(valueSpinner1);
		JPanel lowerBoundPanel = new JPanel(new GridLayout(0, 1));
		lowerBoundPanel.add(p3);
		lowerBoundPanel.add(valueSpinnerCheckBox1);
		JPanel p4 = new JPanel();
		p4.setLayout(new BoxLayout(p4, BoxLayout.LINE_AXIS));
		p4.add(new JLabel("Upper Bound"));
		p4.add(Box.createHorizontalStrut(10));
		p4.add(valueSpinner2);
		JPanel upperBoundPanel = new JPanel(new GridLayout(0, 1));
		upperBoundPanel.add(p4);
		upperBoundPanel.add(valueSpinnerCheckBox2);

		// set grid layout
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(p1);
		add(p2);
		add(lowerBoundPanel);
		add(upperBoundPanel);
	}

	/**
	 * Gets the constraint.
	 * 
	 * @return the constraint
	 */
	public Constraint getConstraint() {

		// use selected field to create mapped constraint name
		if(selectedOperator == Operator.EQUAL_TO)
		{
			double value = (Double) valueSpinner1.getValue();
			// create data constant constraint
			DataConstantConstraint dcc = new DataConstantConstraintImpl(value);
			constraint = dcc;
		}
		else if(selectedOperator == Operator.BETWEEN)
		{
			double lowerBound = (Double) valueSpinner1.getValue();
			double upperBound = (Double) valueSpinner2.getValue();
			// change selected operator based on toggle status of check boxes
			Operator op1 = Operator.LESS_THAN_EXCLUSIVE;
			if(value1Inclusive)
			{
				op1 = Operator.LESS_THAN_INCLUSIVE;
			}
			Operator op2 = Operator.GREATER_THAN_EXCLUSIVE;
			if(value2Inclusive)
			{
				op2 = Operator.GREATER_THAN_INCLUSIVE;
			}

			// get lower bound facet
			DataRangeFacetConstraintImpl lfc = new DataRangeFacetConstraintImpl(lowerBound, operatorFacetMap.get(op1));
			DataRangeFacetConstraintImpl ufc = new DataRangeFacetConstraintImpl(upperBound, operatorFacetMap.get(op2));
			Set<DataRangeFacetConstraint> facets = new HashSet<DataRangeFacetConstraint>();
			facets.add(lfc);
			facets.add(ufc);

			DataRangeConstraint drc = new DataRangeConstraintImpl(facets);
			constraint = drc;
		}
		else
		{
			double bound = (Double) valueSpinner1.getValue();
			Operator op = null;
			if(selectedOperator == Operator.LESS_THAN && value1Inclusive)
			{
				op = Operator.LESS_THAN_INCLUSIVE;
			}
			else if(selectedOperator == Operator.LESS_THAN && !value1Inclusive)
			{
				op = Operator.LESS_THAN_EXCLUSIVE;
			}
			else if(selectedOperator == Operator.GREATER_THAN && !value1Inclusive)
			{
				op = Operator.GREATER_THAN_EXCLUSIVE;
			}
			else if(selectedOperator == Operator.GREATER_THAN && value1Inclusive)
			{
				op = Operator.GREATER_THAN_INCLUSIVE;
			}

			DataRangeFacetConstraint dfc = new DataRangeFacetConstraintImpl(bound, operatorFacetMap.get(op));
			constraint = dfc;
		}

		return constraint;
	}

	/**
	 * Sets the constraint.
	 * 
	 * @param constraint the new constraint
	 */
	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
		populateFields(constraint);
	}

	/**
	 * Populate fields.
	 * 
	 * @param constraint the constraint
	 */
	private void populateFields(Constraint constraint){

		// populate fields based on type of constraint
		if(constraint instanceof DataConstantConstraint)
		{
			DataConstantConstraint dcc = (DataConstantConstraint) constraint;
			operatorsBox.setSelectedItem(Operator.EQUAL_TO);
			valueSpinner1.setValue(dcc.asDouble());
		}
		else if(constraint instanceof DataRangeFacetConstraint)
		{
			DataRangeFacetConstraint dfc = (DataRangeFacetConstraint) constraint;
			populateFieldsForFacet(dfc, valueSpinner1);
		}
		else if(constraint instanceof DataRangeConstraint)
		{
			DataRangeConstraint drc = (DataRangeConstraint) constraint;
			// get facets and set values
			DataRangeFacetConstraint lfc = drc.getLowerBoundFacet();
			DataRangeFacetConstraint ufc = drc.getUpperBoundFacet();
			populateFieldsForFacet(lfc, valueSpinner1);
			populateFieldsForFacet(ufc, valueSpinner2);
			operatorsBox.setSelectedItem(Operator.BETWEEN);
		}

		// refresh gui
		revalidate();
	}

	/**
	 * Populate fields for facet.
	 * 
	 * @param dfc the dfc
	 * @param spinner the spinner
	 */
	private void populateFieldsForFacet(DataRangeFacetConstraint dfc, JSpinner spinner){

		if(spinner.getName().equals("valueSpinner1"))
		{
			valueSpinner1.setValue(dfc.asDouble());
			// set selected operator based on facet value
			if(dfc.getFacet() == RangeFacetVocabulary.MAX_EXCLUSIVE)
			{
				operatorsBox.setSelectedItem(Operator.LESS_THAN);
				valueSpinnerCheckBox1.setSelected(false);
			}
			else if(dfc.getFacet() == RangeFacetVocabulary.MAX_INCLUSIVE)
			{
				operatorsBox.setSelectedItem(Operator.LESS_THAN);
				valueSpinnerCheckBox1.setSelected(true);
			}
			else if(dfc.getFacet() == RangeFacetVocabulary.MIN_EXCLUSIVE)
			{
				operatorsBox.setSelectedItem(Operator.GREATER_THAN);
				valueSpinnerCheckBox1.setSelected(false);
			}
			else if (dfc.getFacet() == RangeFacetVocabulary.MIN_INCLUSIVE)
			{
				operatorsBox.setSelectedItem(Operator.GREATER_THAN);
				valueSpinnerCheckBox1.setSelected(true);
			}
		}
		else
		{
			valueSpinner2.setValue(dfc.asDouble());
			// set selected operator based on facet value
			if(dfc.getFacet() == RangeFacetVocabulary.MAX_EXCLUSIVE)
			{
				operatorsBox.setSelectedItem(Operator.LESS_THAN);
				valueSpinnerCheckBox2.setSelected(false);
			}
			else if(dfc.getFacet() == RangeFacetVocabulary.MAX_INCLUSIVE)
			{
				operatorsBox.setSelectedItem(Operator.LESS_THAN);
				valueSpinnerCheckBox2.setSelected(true);
			}
			else if(dfc.getFacet() == RangeFacetVocabulary.MIN_EXCLUSIVE)
			{
				operatorsBox.setSelectedItem(Operator.GREATER_THAN);
				valueSpinnerCheckBox2.setSelected(false);
			}
			else if (dfc.getFacet() == RangeFacetVocabulary.MIN_INCLUSIVE)
			{
				operatorsBox.setSelectedItem(Operator.GREATER_THAN);
				valueSpinnerCheckBox2.setSelected(true);
			}
		}
	}
}