package com.gatech.osmowsis.ui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.gatech.osmowsis.ui.service.ImageService;

// panel to show index
public class IndexPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int index;

	public IndexPanel(int i) {
		super();
		index = i;
		initialize();
		// TODO Auto-generated constructor stub
	}

	// initialize fields
	private void initialize() {
		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		// add index
		JLabel labelIndex = new JLabel(String.valueOf(index));
		labelIndex.setSize(ImageService.getInstance().getSquareWidth(), ImageService.getInstance().getSquareWidth());
		this.add(labelIndex);

		// create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.black, 1);
		this.setBorder(border);

		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(ImageService.getInstance().getSquareWidth(), ImageService.getInstance().getSquareWidth()));

	}

}
