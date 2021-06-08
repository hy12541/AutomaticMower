package com.gatech.osmowsis.ui.panel;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gatech.osmowsis.ui.model.UIMower;
import com.gatech.osmowsis.ui.service.ImageService;

// panel to show mower information
public class MowerInfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int energy;
	private int id;
	private boolean isCurrentMower;
	private boolean isHighlighted;

	public MowerInfoPanel(UIMower uiMower, boolean isHighlighted) {
		super();
		if (uiMower != null) {
			this.energy = uiMower.getEnergy();
			this.id = uiMower.getId();
			this.isCurrentMower = uiMower.isCurrentMower();
			this.isHighlighted = isHighlighted;
		}

		initialize();

	}

	// initialize fields
	private void initialize() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		// add energy
		JLabel labelEnergy = new JLabel(String.valueOf(energy));
		labelEnergy.setSize(ImageService.getInstance().getSquareWidth() / 16, ImageService.getInstance().getSquareWidth() / 16);
		this.add(labelEnergy);

		// add energy icon
		JLabel labelEnergyIcon = new JLabel(ImageService.getInstance().getBatteryImageIcon());
		labelEnergyIcon.setSize(ImageService.getInstance().getSquareWidth() / 8, ImageService.getInstance().getSquareWidth() / 16);
		this.add(labelEnergyIcon);

		// add id
		JLabel labelId = new JLabel("  " + String.valueOf(id));
		labelId.setSize(ImageService.getInstance().getSquareWidth() / 16, ImageService.getInstance().getSquareWidth() / 16);
		this.add(labelId);
		
		// highlight background when it's current object
		/*
		 * if(isCurrentMower) { labelId.setOpaque(true);
		 * labelId.setBackground(Color.GREEN); }
		 */
		

		// set background color
		if (isHighlighted) {
			this.setBackground(Color.YELLOW);
		} else {
			this.setBackground(Color.WHITE);
		}

	}

}
