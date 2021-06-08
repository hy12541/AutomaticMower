package com.gatech.osmowsis.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.gatech.osmowsis.ui.service.ImageService;

// panel to show compass
public class CompassPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public CompassPanel() {
		super();
		initialize();
		// TODO Auto-generated constructor stub
	}

	// initialize fields
	private void initialize() {
		this.setLayout(new BorderLayout());

		// add compass
		JLabel labelCompass = new JLabel(ImageService.getInstance().getCompassImageIcon());
		labelCompass.setSize(ImageService.getInstance().getSquareWidth(), ImageService.getInstance().getSquareWidth());
		this.add(labelCompass);

		// create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.black, 1);
		this.setBorder(border);

		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(ImageService.getInstance().getSquareWidth(), ImageService.getInstance().getSquareWidth()));

	}

}
