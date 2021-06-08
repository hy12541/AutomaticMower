package com.gatech.osmowsis.ui.panel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;

import com.gatech.osmowsis.ui.service.ImageService;

// main content panel
public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private LawnPanelContainer lawnPanelContainer;
	private SidePanelContainer sidePanelContainer;

	public MainPanel() {
		super();
		initialize();

	}

	// initialize fields
	private void initialize() {

		lawnPanelContainer = new LawnPanelContainer();
		sidePanelContainer = new SidePanelContainer();

		this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		this.setPreferredSize(new Dimension(1800, 1000));
		this.add(lawnPanelContainer);
		this.add(sidePanelContainer);

	}

	public LawnPanelContainer getLawnPanelContainer() {
		return lawnPanelContainer;
	}

	public void setLawnPanelContainer(LawnPanelContainer lawnPanelContainer) {
		this.lawnPanelContainer = lawnPanelContainer;
	}

}
