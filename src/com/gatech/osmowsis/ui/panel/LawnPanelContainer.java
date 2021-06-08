package com.gatech.osmowsis.ui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.gatech.osmowsis.ui.service.ImageService;

// panel which contains lawn panel and next object panel
public class LawnPanelContainer extends JPanel {

	private static final long serialVersionUID = 1L;
	private LawnPanel lawnPanel;
	private NextObjectPanel nextObjectPanel;

	public LawnPanelContainer() {
		super();
		initialize();

	}

	// initialize fields
	private void initialize() {
		// this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		//BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(1250, 950));
		
		JPanel panelContainer1 = new JPanel();
		panelContainer1.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelContainer1.setBorder(BorderFactory.createLineBorder(Color.black, 1));

		JPanel panelContainer2 = new JPanel();
		panelContainer2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		panelContainer2.setLayout(new FlowLayout(FlowLayout.CENTER));

		// add next object panel
		nextObjectPanel = new NextObjectPanel();
		panelContainer1.add(nextObjectPanel);
		// add lawn panel
		lawnPanel = new LawnPanel();
		panelContainer2.add(lawnPanel);

		this.add(panelContainer1);
		this.add(panelContainer2);

	}

}
