package com.gatech.osmowsis.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.gatech.osmowsis.ui.service.ImageService;

// panel to show fence
public class FencePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int fenceType;
	private boolean isHighlighted;

	public FencePanel(int fenceType, boolean isHighlighted) {
		super();
		this.fenceType = fenceType;
		this.isHighlighted = isHighlighted;
		initialize();
		// TODO Auto-generated constructor stub
	}

	// initialize fields
	private void initialize() {
		this.setLayout(new BorderLayout());

		// add fence
		JLabel labelFence = new JLabel(ImageService.getInstance().getFenceImageIcon(fenceType));
		labelFence.setSize(ImageService.getInstance().getSquareWidth(), ImageService.getInstance().getSquareWidth());
		this.add(labelFence);

		// create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.black, 1);
		this.setBorder(border);

		// set background color
		if (isHighlighted) {
			this.setBackground(Color.YELLOW);
		} else {
			this.setBackground(Color.WHITE);
		}

		this.setPreferredSize(new Dimension(ImageService.getInstance().getSquareWidth(), ImageService.getInstance().getSquareWidth()));

	}

}
