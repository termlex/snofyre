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
package uk.nhs.cfh.dsp.srth.desktop.modules.simulator.viewcomponent;

import com.jidesoft.swing.ButtonStyle;
import com.jidesoft.swing.JideButton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXLabel;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerService;
import uk.nhs.cfh.dsp.srth.desktop.modules.simulator.viewcomponent.actions.GenerateDataAction;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.srth.simulator.engine.DataGenerationEngine;
import uk.nhs.cfh.dsp.srth.simulator.engine.impl.AbstractQueryBasedDataGenerationEngine;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;

// TODO: Auto-generated Javadoc
/**
 * A custom panel for {@link DataGenerationEngine}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jun 11, 2009 at 5:34:38 PM
 * <br>.
 */

@SuppressWarnings("serial")
public class DataGenerationPanel extends JPanel implements ActionListener {

	/** The logger. */
	private static Log logger = LogFactory.getLog(DataGenerationPanel.class);
	
	/** The location field. */
	private JTextField locationField;
	
	/** The location panel. */
	private JPanel locationPanel;

	/** The generation strategy box. */
	private JComboBox generationStrategyBox;
	
	/** The include excluded terms box. */
	private JCheckBox includeExcludedTermsBox;
	
	/** The randomise numerical values box. */
	private JCheckBox randomiseNumericalValuesBox;
    
    /** The refine qualifiers check box. */
    private JCheckBox refineQualifiersCheckBox;
	
	/** The pt number spinner. */
	private JSpinner ptNumberSpinner;
	
	/** The min age spinner. */
	private JSpinner minAgeSpinner;
    
    /** The initial patients number. */
    private int initialPatientsNumber = 100;

    /** The initial minimum age. */
    private int initialMinimumAge = 40;
    
    /** The padding size. */
    private int paddingSize = 10;
	
	/** The selected folder. */
	private File selectedFolder;

	/** The folder selected. */
	private boolean folderSelected = false;
    
    /** The query service. */
    private QueryService queryService;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The active frame. */
    private JFrame activeFrame;
    
    /** The data generation engine. */
    private DataGenerationEngine dataGenerationEngine;
    
    /** The property change tracker service. */
    private PropertyChangeTrackerService propertyChangeTrackerService;
    
    /** The include pre coordinated data check box. */
    private JCheckBox includePreCoordinatedDataCheckBox;

    /**
     * Instantiates a new data generation panel.
     *
     * @param applicationService the application service
     * @param queryService the query service
     * @param dataGenerationEngine the data generation engine
     * @param propertyChangeTrackerService the property change tracker service
     */
    public DataGenerationPanel(ApplicationService applicationService, QueryService queryService,
                               DataGenerationEngine dataGenerationEngine,
                               PropertyChangeTrackerService propertyChangeTrackerService) {
        this.applicationService = applicationService;
        this.queryService = queryService;
        this.propertyChangeTrackerService = propertyChangeTrackerService;
		this.dataGenerationEngine = dataGenerationEngine;
	}

    /**
     * Instantiates a new data generation panel.
     */
    public DataGenerationPanel(){
        
    }

	/**
	 * Inits the components.
	 */
	public synchronized void initComponents() {

        while(activeFrame == null)
        {
            if(applicationService != null && applicationService.getFrameView() != null)
            {
                activeFrame = applicationService.getFrameView().getActiveFrame();
            }
        }

        JPanel runPanel = new JPanel();
		runPanel.setLayout(new BoxLayout(runPanel, BoxLayout.LINE_AXIS));
        runPanel.setBorder(BorderFactory.createEmptyBorder(paddingSize, paddingSize, paddingSize, paddingSize));
        JXLabel label = new JXLabel();
        label.setLineWrap(true);
        label.setText("<html>This panel allows the creation of clinically plausible data based on a query specification. " +
                "Please note that you <b>must</b> always specify a data generation source. " +
                "The rest of the parameters can be left in their default state. When you've configured the parameters " +
                "click the 'Generate data' button.</html>");
        runPanel.add(label);
        runPanel.add(new JSeparator(SwingConstants.VERTICAL));
		runPanel.add(Box.createHorizontalStrut(paddingSize));
		JideButton runButton = new JideButton(new GenerateDataAction(applicationService,
                queryService, dataGenerationEngine, propertyChangeTrackerService));
        runButton.setButtonStyle(ButtonStyle.HYPERLINK_STYLE);
		runPanel.add(runButton);
        runButton.setIcon(new ImageIcon(DataGenerationPanel.class.getResource("resources/linuxconf.png")));
        runPanel.add(Box.createHorizontalStrut(paddingSize));

		// create radio buttons for choosing source
		JRadioButton queryButton = new JRadioButton("Active Query");
        queryButton.setSelected(true);
		queryButton.addActionListener(this);
		queryButton.setActionCommand("activeQuery");
		JRadioButton fileButton = new JRadioButton("File");
        fileButton.addActionListener(this);
        fileButton.setActionCommand("file");

		JRadioButton folderButton = new JRadioButton("Folder");
		folderButton.addActionListener(this);
		folderButton.setActionCommand("folder");
		ButtonGroup bg = new ButtonGroup();
		bg.add(queryButton);
		bg.add(fileButton);
		bg.add(folderButton);
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		//		buttonsPanel.add(new JLabel("Select data generation source"));
		buttonsPanel.setBorder(BorderFactory.createTitledBorder("Select data generation source"));
		buttonsPanel.add(Box.createHorizontalStrut(paddingSize));
		buttonsPanel.add(queryButton);
		buttonsPanel.add(Box.createHorizontalStrut(paddingSize));
		buttonsPanel.add(fileButton);
		buttonsPanel.add(Box.createHorizontalStrut(paddingSize));
		buttonsPanel.add(folderButton);

		locationField = new JTextField(100);
		JButton loadQueryButton = new JButton("Browse");
		loadQueryButton.addActionListener(this);
		loadQueryButton.setActionCommand("load");
		locationPanel = new JPanel();
		locationPanel.setLayout(new BoxLayout(locationPanel, BoxLayout.LINE_AXIS));
		locationPanel.add(new JLabel("Load file from"));
		locationPanel.add(Box.createHorizontalStrut(paddingSize));
		locationPanel.add(locationField);
		locationPanel.add(Box.createHorizontalStrut(paddingSize));
		locationPanel.add(loadQueryButton);

        JPanel lhsPanel = new JPanel(new GridLayout(0, 2));
        JPanel rhsPanel = new JPanel(new GridLayout(0, 2));

		lhsPanel.add(new JLabel("Max number of patients to generate"));

		SpinnerNumberModel model = new SpinnerNumberModel(initialPatientsNumber, 1, 1000000, 1);
		ptNumberSpinner = new JSpinner(model);
		ptNumberSpinner.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent event) {
				// get value currently selected in spinner and set in engine
				dataGenerationEngine.setMaxPatientNumber(Long.parseLong(ptNumberSpinner.getValue().toString()));
//                Object value = ptNumberSpinner.getValue();
//                if(value instanceof Double)
//                {
//                    Double d = (Double) ptNumberSpinner.getValue();
//                    dataGenerationEngine.setMaxPatientNumber(d.longValue());
//                }
//                else
//                {
//                    dataGenerationEngine.setMaxPatientNumber(Long.parseLong(ptNumberSpinner.getValue().toString()));
//                }
                logger.debug("ptNumberSpinner.getValue().getClass() = " + ptNumberSpinner.getValue().getClass());
				logger.debug("Max pt number in engine set to : "+dataGenerationEngine.getMaxPatientNumber());
			}
		});
		lhsPanel.add(ptNumberSpinner);

		lhsPanel.add(new JLabel("Min age of patients to generate"));
		SpinnerNumberModel ageModel = new SpinnerNumberModel(initialMinimumAge, 1, 120, 1);
		minAgeSpinner = new JSpinner(ageModel);
		minAgeSpinner.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				// set min pt age to current value
				dataGenerationEngine.setMinPatientAgeInYears(Integer.parseInt(minAgeSpinner.getValue().toString()));
				logger.debug("Value of engine.getMinPatientAgeInYears() : " + dataGenerationEngine.getMinPatientAgeInYears());
			}
		});
		lhsPanel.add(minAgeSpinner);

		lhsPanel.add(new JLabel("Data generation strategy"));
		generationStrategyBox = new JComboBox(DataGenerationEngine.DataGenerationStrategy.values());
        generationStrategyBox.setSelectedItem(DataGenerationEngine.DataGenerationStrategy.ADD_IF_NOT_EXISTS);
        generationStrategyBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dataGenerationEngine.setDataGenerationStrategy((DataGenerationEngine.DataGenerationStrategy) generationStrategyBox.getSelectedItem());
            }
        });
		lhsPanel.add(generationStrategyBox);

		includeExcludedTermsBox = new JCheckBox(
				new AbstractAction("Include Excluded terms in data"){

					public void actionPerformed(ActionEvent arg0) {
						dataGenerationEngine.setIncludeExcludedTerms(includeExcludedTermsBox.isSelected());
                        logger.debug("dataGenerationEngine.isIncludeExcludedTerms() = " + dataGenerationEngine.isIncludeExcludedTerms());
					}
				});
		rhsPanel.add(includeExcludedTermsBox);

		randomiseNumericalValuesBox = new JCheckBox(
				new AbstractAction("Randomise Numerical values in data"){

					public void actionPerformed(ActionEvent arg0) {
						dataGenerationEngine.setRandomiseNumericalValues(randomiseNumericalValuesBox.isSelected());
                        logger.debug("dataGenerationEngine.isRandomiseNumericalValues() = " + dataGenerationEngine.isRandomiseNumericalValues());
					}
				});
		rhsPanel.add(randomiseNumericalValuesBox);

        refineQualifiersCheckBox = new JCheckBox(new AbstractAction("Refine Qualifiers in expression") {
            public void actionPerformed(ActionEvent e) {
                dataGenerationEngine.setRefineQualifiers(refineQualifiersCheckBox.isSelected());
                logger.debug("dataGenerationEngine.isRefineQualifiers() = " + dataGenerationEngine.isRefineQualifiers());
            }
        });
		rhsPanel.add(refineQualifiersCheckBox);

        includePreCoordinatedDataCheckBox = new JCheckBox(new AbstractAction("Include pre-coordinated expressions") {
            public void actionPerformed(ActionEvent e) {
                dataGenerationEngine.setIncludePrecoordinatedData(includePreCoordinatedDataCheckBox.isSelected());
                logger.debug("dataGenerationEngine.isIncludePrecoordinatedData() = " + dataGenerationEngine.isIncludePrecoordinatedData());
            }
        });
		rhsPanel.add(includePreCoordinatedDataCheckBox);
        rhsPanel.add(new JLabel("  "));

        /*
		 * create panel for parametrising engine
		 */
		JPanel parametrisationPanel = new JPanel();
        parametrisationPanel.setLayout(new GridLayout(0, 2));
		parametrisationPanel.setBorder(BorderFactory.createTitledBorder("Engine Parameters"));

		// add panels to parametrisation panel
        parametrisationPanel.add(lhsPanel);
        parametrisationPanel.add(rhsPanel);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        topPanel.add(runPanel);
        topPanel.add(new JSeparator(SwingConstants.VERTICAL));
        topPanel.add(buttonsPanel);
		// add all panels to this component
		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		add(parametrisationPanel, BorderLayout.CENTER);

        // initialise values
        populateFields(dataGenerationEngine);
	}

	/**
	 * Populate fields.
	 *
	 * @param engine the engine
	 */
	private void populateFields(DataGenerationEngine engine){

		// set values from engine
		ptNumberSpinner.setValue(engine.getMaxPatientNumber());
		minAgeSpinner.setValue(engine.getMinPatientAgeInYears());
		generationStrategyBox.setSelectedItem(engine.getDataGenerationStrategy());
		includeExcludedTermsBox.setSelected(engine.isIncludeExcludedTerms());
        includePreCoordinatedDataCheckBox.setSelected(engine.isIncludePrecoordinatedData());
        refineQualifiersCheckBox.setSelected(engine.isRefineQualifiers());
        randomiseNumericalValuesBox.setSelected(engine.isRandomiseNumericalValues());
	}

	/**
	 * Sets the engine.
	 *
	 * @param dataGenerationEngine the new engine
	 */
	public void setEngine(DataGenerationEngine dataGenerationEngine) {
		this.dataGenerationEngine = dataGenerationEngine;
		populateFields(dataGenerationEngine);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event) {

		String actionCommand = event.getActionCommand();

		if("activeQuery".equals(actionCommand))
		{
            if(dataGenerationEngine instanceof AbstractQueryBasedDataGenerationEngine)
            {
                ((AbstractQueryBasedDataGenerationEngine)dataGenerationEngine).setActiveQuery(queryService.getActiveQuery());
            }
			folderSelected = false;
		}
		else
		{
            applicationService.notifyError("Not supported", new UnsupportedOperationException("This feature is currently not supported"), Level.WARNING);
            logger.warn("Unsupported data generation source selected  : "+actionCommand);
		}
	}

    /**
     * Sets the query service.
     *
     * @param queryService the new query service
     */
    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

    /**
     * Sets the application service.
     *
     * @param applicationService the new application service
     */
    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * Sets the data generation engine.
     *
     * @param dataGenerationEngine the new data generation engine
     */
    public void setDataGenerationEngine(DataGenerationEngine dataGenerationEngine) {
        if (dataGenerationEngine != null) {
            this.dataGenerationEngine = dataGenerationEngine;
            initialMinimumAge = this.dataGenerationEngine.getMinPatientAgeInYears();
            initialPatientsNumber = Integer.parseInt(String.valueOf(dataGenerationEngine.getMaxPatientNumber()));
        }
    }

    /**
     * Sets the property change tracker service.
     *
     * @param propertyChangeTrackerService the new property change tracker service
     */
    public void setPropertyChangeTrackerService(PropertyChangeTrackerService propertyChangeTrackerService) {
        this.propertyChangeTrackerService = propertyChangeTrackerService;
    }
}