package com.gatech.osmowsis.ui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.gatech.osmowsis.ui.model.UISimState;
import com.gatech.osmowsis.ui.service.ModelService;

// panel to show 3 buttons-Next, Fast-Forward, Stop, and text area
public class SidePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private TextArea outputArea;
	private String outputText;
	private String lineBreak = "\n";

	public SidePanel() {
		super();
		this.outputText = ModelService.getUiOutputText();
		initialize();
		ModelService.setSidePanel(this);

	}

	public TextArea getOutputArea() {
		return outputArea;
	}

	public void setOutputArea(TextArea outputArea) {
		this.outputArea = outputArea;
	}

	// initialize fields
	private void initialize() {
		// set border
		this.setBorder(BorderFactory.createLineBorder(Color.black, 1));

		// set layout
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(400, 940));
				
		initOutputArea();

		GridBagConstraints constaints = new GridBagConstraints();
		constaints.fill = GridBagConstraints.CENTER;
		constaints.insets = new Insets(15, 5, 5, 5);
		constaints.ipadx = 5;
		constaints.ipady = 10;
		constaints.weightx = 1;
		constaints.weighty = 1;

		// create buttons
		JButton nextButton = new JButton("Next");
		JButton fastForwardButton = new JButton("Fast-Forward");
		JButton stopButton = new JButton("Stop");

		constaints.gridx = 0;
		constaints.gridy = 0;
		this.add(nextButton, constaints);

		constaints.gridx = 1;
		constaints.gridy = 0;
		this.add(fastForwardButton, constaints);

		constaints.gridx = 2;
		constaints.gridy = 0;
		this.add(stopButton, constaints);

		constaints.gridx = 0;
		constaints.gridy = 1;
		constaints.gridwidth = 3;
		this.add(outputArea, constaints);

		// add action listener to Next button
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// execute next object and reset UISimstate
				ModelService.getSimController().next();

				// get updated UISimstate
				UISimState uiSimstate = ModelService.getUISimState();

				// if there is an error
				if (uiSimstate != null && uiSimstate.getUiError() != null && !uiSimstate.getUiError().isEmpty()) {
					// append error text in textArea
					String errorText = ModelService.getUiError();
					outputArea.append(errorText + lineBreak);
					return;
				}

				// if there is no error and simulation run is not completed
				if (uiSimstate != null && !uiSimstate.isSimRunCompleted()) {

					// append updated output text in textArea
					String outputText = ModelService.getUiOutputText();
					outputArea.append(outputText + lineBreak);

					// if the simulation state is valid
					if (uiSimstate.getTotalGopherNumber() != 0 && uiSimstate.getTotalMowerNumber() != 0) {
						// update lawn board on GUI
						LawnPanel lawnPanel = ModelService.getLawnPanel();
						lawnPanel.updateBoard();

						// update next object statement
						NextObjectPanel nextObjectPanel = ModelService.getNextObjectPanel();
						nextObjectPanel.updateNextObjectText();
					}

				}

			}
		});

		// add action listener to Fast-Forward button
		fastForwardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// poll next object and reset UISimstate
				ModelService.getSimController().next();

				// get updated UISimstate
				UISimState uiSimstate = ModelService.getUISimState();

				// if there is an error
				if (uiSimstate != null && uiSimstate.getUiError() != null && !uiSimstate.getUiError().isEmpty()) {
					// append error text in textArea
					String errorText = ModelService.getUiError();
					outputArea.append(errorText + lineBreak);
					return;
				}

				// if there is no error and simulation run is not completed
				while (uiSimstate != null && !uiSimstate.isSimRunCompleted()) {

					// append updated output text in textArea
					String outputText = ModelService.getUiOutputText();
					outputArea.append(outputText + lineBreak);

					// if the simulation state is valid
					if (uiSimstate.getTotalGopherNumber() != 0 && uiSimstate.getTotalMowerNumber() != 0) {
						// update lawn board on GUI
						LawnPanel lawnPanel = ModelService.getLawnPanel();
						lawnPanel.updateBoard();

						// update next object statement
						NextObjectPanel nextObjectPanel = ModelService.getNextObjectPanel();
						nextObjectPanel.updateNextObjectText();

						// poll next object and reset UISimstate
						ModelService.getSimController().next();

						// get updated UISimstate
						uiSimstate = ModelService.getUISimState();
					}

				}

			}
		});

		// add action listener to Stop button
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// update terminate to true
				ModelService.getSimController().stop();

				// get updated UISimstate
				UISimState uiSimstate = ModelService.getUISimState();

				// if there is an error
				if (uiSimstate != null && uiSimstate.getUiError() != null && !uiSimstate.getUiError().isEmpty()) {
					// append error text in textArea
					String errorText = ModelService.getUiError();
					outputArea.append(errorText + lineBreak);
					return;
				}

				// if there is no error and simulation run is not completed
				if (uiSimstate != null && !uiSimstate.isSimRunCompleted()) {
					// update UISimstate
					ModelService.getSimController().next();

					// append updated output text in textArea
					String outputText = ModelService.getUiOutputText();
					outputArea.append(outputText + lineBreak);

					// if the simulation state is valid
					if (uiSimstate.getTotalGopherNumber() != 0 && uiSimstate.getTotalMowerNumber() != 0) {
						// update lawn board on GUI
						LawnPanel lawnPanel = ModelService.getLawnPanel();
						lawnPanel.updateBoard();

						// update next object statement
						NextObjectPanel nextObjectPanel = ModelService.getNextObjectPanel();
						nextObjectPanel.updateNextObjectText();
					}

				}
			}
		});
	}

	// initialize text area
	public void initOutputArea() {
		outputArea = new TextArea("Output: \n", 50, 50);
		outputArea.setEditable(false);
		outputArea.setBackground(Color.WHITE);
		outputArea.append(outputText + lineBreak);

	}

	// reset text area
	public void resetOutputArea() {
		if (outputArea != null) {
			outputArea.setText("Output: \n");
		}
	}

}
