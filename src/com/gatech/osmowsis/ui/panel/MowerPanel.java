package com.gatech.osmowsis.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.gatech.osmowsis.ui.model.UIMower;
import com.gatech.osmowsis.ui.service.ImageService;

// panel to show mower square
public class MowerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	UIMower uiMower;
	private boolean hasRechargingPad;
	private boolean isHighlighted;

	public MowerPanel(UIMower uiMower, boolean hasRechargingPad, boolean isHighlighted) {
		super();
		this.uiMower = uiMower;
		this.hasRechargingPad = hasRechargingPad;
		this.isHighlighted = isHighlighted;
		initialize();

	}

	// initialize fields
	private void initialize() {
		this.setLayout(new BorderLayout());

		// add mower
		JLabel labelMower = new JLabel(ImageService.getInstance().getMowerImageIcon());
		labelMower.setSize(ImageService.getInstance().getSquareWidth(), ImageService.getInstance().getSquareWidth());
		this.add(labelMower, BorderLayout.CENTER);

		if (uiMower != null) {
			// add direction
			JLabel labelDirection = new JLabel(ImageService.getInstance().getDirectionImageIcon(uiMower.getDirection().toString()));
			labelDirection.setSize(ImageService.getInstance().getSquareWidth(), ImageService.getInstance().getSquareWidth());
			this.add(labelDirection, BorderLayout.WEST);

			// add energy and id
			MowerInfoPanel mowerInfoPanel = new MowerInfoPanel(uiMower, isHighlighted);
			this.add(mowerInfoPanel, BorderLayout.NORTH);
		}

		// add recharging pad
		if (hasRechargingPad) {
			RechargingPadPanel rechargingPadPanel = new RechargingPadPanel(isHighlighted);
			this.add(rechargingPadPanel, BorderLayout.EAST);
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
