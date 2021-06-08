package com.gatech.osmowsis.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gatech.osmowsis.ui.service.ImageService;

// panel to show recharging pad
public class RechargingPadPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private boolean isHighlighted;

	public RechargingPadPanel(boolean isHighlighted) {
		super();
		this.isHighlighted = isHighlighted;
		initialize();
		// TODO Auto-generated constructor stub
	}

	// initialize fields
	private void initialize() {
		this.setLayout(new BorderLayout());

		// add recharging pad
		JLabel labelRechargingPad = new JLabel(ImageService.getInstance().getRechargingPadImageIcon());
		labelRechargingPad.setSize(ImageService.getInstance().getSquareWidth() / 8, ImageService.getInstance().getSquareWidth() / 8);
		this.add(labelRechargingPad, BorderLayout.CENTER);

		// set background color
		if (isHighlighted) {
			this.setBackground(Color.YELLOW);
		} else {
			this.setBackground(Color.WHITE);
		}

	}

}
