package com.gatech.osmowsis.ui.panel;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gatech.osmowsis.ui.model.UIGopher;
import com.gatech.osmowsis.ui.service.ImageService;

// panel to show gopher information -id
public class GopherInfoPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private int id;
	private boolean isCurrentGopher;
	private boolean isHighlighted;

	public GopherInfoPanel(UIGopher uiGopher, boolean isHighlighted) {
		super();
		if (uiGopher != null) {
			id = uiGopher.getId();
			isCurrentGopher = uiGopher.isCurrentGopher();
		}

		this.isHighlighted = isHighlighted;

		initialize();

	}

	// initialize fields
	private void initialize() {
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));
		// add id
		JLabel labelId = new JLabel("  " + String.valueOf(id));
		labelId.setSize(ImageService.getInstance().getSquareWidth() / 4, ImageService.getInstance().getSquareWidth() / 4);
		this.add(labelId);
		// highlight background when it's current object
		/*
		 * if (isCurrentGopher) { labelId.setOpaque(true);
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
