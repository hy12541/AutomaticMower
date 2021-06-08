package com.gatech.osmowsis.ui.frame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import com.gatech.osmowsis.ui.model.UISimState;
import com.gatech.osmowsis.ui.panel.LawnPanel;
import com.gatech.osmowsis.ui.panel.NextObjectPanel;
import com.gatech.osmowsis.ui.service.ModelService;

public class MainMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;
	private JMenu menu;
	private JMenuItem menuItemOpenScenarioFile;
	private JMenuItem menuItemRestart;
	private JMenuItem menuItemExit;
	private String lineBreak = "\n";

	public MainMenuBar() {

		super();
		initialize();
		addActionListener();
	}

	// initialize fields
	public void initialize() {

		// create menu and set menu font
		Font menuFont = new Font(this.getFont().getFontName(), this.getFont().getStyle(), 14);
		UIManager.put("Menu.font", menuFont);
		menu = new JMenu("Menu");

		// create menu items
		menuItemOpenScenarioFile = new JMenuItem("Open Scenario File");
		menuItemRestart = new JMenuItem("Restart");
		menuItemExit = new JMenuItem("Exit");

		// set font for menu items
		Font menuItemFont = new Font(this.getFont().getFontName(), this.getFont().getStyle(), 14);
		menuItemOpenScenarioFile.setFont(menuItemFont);
		menuItemRestart.setFont(menuItemFont);
		menuItemExit.setFont(menuItemFont);

		// add ui elements
		menu.add(menuItemOpenScenarioFile);
		menu.add(menuItemRestart);
		menu.add(menuItemExit);
		this.add(menu);
	}

	// add action listener to menu items - Open Scenario File, Restart and Exit
	public void addActionListener() {

		// add action listener to menu item - Open Scenario File
		menuItemOpenScenarioFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JFileChooser jfc = new JFileChooser();
				File selectedFile = null;

				int returnValue = jfc.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = jfc.getSelectedFile();
				}

				if (selectedFile != null) {

					// re-initialize simulation state
					ModelService.getSimController().startByFile(selectedFile);
					// get updated UISimstate
					UISimState uiSimstate = ModelService.getUISimState();
					// reset output area
					ModelService.getSidePanel().resetOutputArea();

					// if there is an error
					if (uiSimstate != null && uiSimstate.getUiError() != null && !uiSimstate.getUiError().isEmpty()) {
						// append error text in textArea
						String errorText = ModelService.getUiError();
						ModelService.getSidePanel().getOutputArea().append(errorText + lineBreak);
						return;
					}

					// if there is no error and simulation run is not completed
					if (uiSimstate != null && !uiSimstate.isSimRunCompleted()) {

						// append updated output text in textArea
						String outputText = ModelService.getUiOutputText();
						ModelService.getSidePanel().getOutputArea().append(outputText + lineBreak);

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
			}
		});

		// add action listener to menu item - Restart
		menuItemRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// re-initialize simulation state
				ModelService.getSimController().restart();

				// get updated UISimstate
				UISimState uiSimstate = ModelService.getUISimState();
				// reset output area
				ModelService.getSidePanel().resetOutputArea();

				// if there is an error
				if (uiSimstate != null && uiSimstate.getUiError() != null && !uiSimstate.getUiError().isEmpty()) {
					// append error text in textArea
					String errorText = ModelService.getUiError();
					ModelService.getSidePanel().getOutputArea().append(errorText + lineBreak);
					return;
				}

				// if there is no error and simulation run is not completed
				if (uiSimstate != null && !uiSimstate.isSimRunCompleted()) {

					// append updated output text in textArea
					String outputText = ModelService.getUiOutputText();
					ModelService.getSidePanel().getOutputArea().append(outputText + lineBreak);

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
		// add action listener to menu item - Exit
		menuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				ModelService.getSimController().exit();
			}
		});
	}

}
