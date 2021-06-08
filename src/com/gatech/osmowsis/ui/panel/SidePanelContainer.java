package com.gatech.osmowsis.ui.panel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;

import com.gatech.osmowsis.ui.service.ImageService;

// panel which contains side panel
public class SidePanelContainer extends JPanel {

	private static final long serialVersionUID = 1L;
	private SidePanel sidePanel;

	public SidePanelContainer() {
		super();
		initialize();

	}

	// initialize fields
	private void initialize() {
		// this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		this.setLayout(new FlowLayout(FlowLayout.LEADING));	
		
		sidePanel = new SidePanel();
		this.add(sidePanel);

	}

}
