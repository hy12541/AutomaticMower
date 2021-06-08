package com.gatech.osmowsis.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.gatech.osmowsis.ui.model.UIGopher;
import com.gatech.osmowsis.ui.service.ImageService;

// panel to show grass square
public class GrassPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private boolean hasRechargingPad;
	private UIGopher uiGopher;
	private boolean isHighlighted;

	public GrassPanel(boolean hasRechargingPad, UIGopher uiGopher, boolean isHighlighted) {
		super();
		this.hasRechargingPad = hasRechargingPad;
		this.uiGopher = uiGopher;
		this.isHighlighted = isHighlighted;
		initialize();

	}

	// initialize fields
	private void initialize() {
		this.setLayout(new BorderLayout());

		// add grass
		JLabel labelGrass = new JLabel(ImageService.getInstance().getGrassImageIcon());
		labelGrass.setSize(ImageService.getInstance().getSquareWidth(), ImageService.getInstance().getSquareWidth());
		this.add(labelGrass, BorderLayout.CENTER);

		// add recharging Pad
		if (hasRechargingPad) {
			RechargingPadPanel rechargingPadPanel = new RechargingPadPanel(isHighlighted);
			this.add(rechargingPadPanel, BorderLayout.EAST);
		}
		// add gopher
		if (uiGopher != null) {
			JLabel labelGopher = new JLabel(ImageService.getInstance().getGopherImageIcon());
			labelGopher.setSize(ImageService.getInstance().getSquareWidth(), ImageService.getInstance().getSquareWidth());
			this.add(labelGopher, BorderLayout.CENTER);

			// add id
			GopherInfoPanel gopherInfoPanel = new GopherInfoPanel(uiGopher, isHighlighted);
			this.add(gopherInfoPanel, BorderLayout.NORTH);
		}

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
