package com.gatech.osmowsis.ui.panel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.gatech.osmowsis.ui.service.ModelService;

//panel to show next and current object polled
public class NextObjectPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int FONT_SIZE = 18;
	private String nextObjectText = "";

	public NextObjectPanel() {
		super();
		nextObjectText = ModelService.getUISimState().getNextAndCurrentUiObjectStr();
		initialize();

		ModelService.setNextObjectPanel(this);

	}

	// initialize fields
	private void initialize() {
		// this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setPreferredSize(new Dimension(1245, 30));

		JLabel nextObjectLabel = new JLabel(nextObjectText);
		Font f = new Font(this.getFont().getFontName(), this.getFont().getStyle(), FONT_SIZE);
		nextObjectLabel.setFont(f);
		this.add(nextObjectLabel);

	}

	// update fields
	public void updateNextObjectText() {
		this.removeAll();

		// get updated text
		nextObjectText = ModelService.getUISimState().getNextAndCurrentUiObjectStr();
		JLabel nextObjectLabel = new JLabel(nextObjectText);
		Font f = new Font(this.getFont().getFontName(), this.getFont().getStyle(), FONT_SIZE);
		nextObjectLabel.setFont(f);

		// add new component
		this.add(nextObjectLabel);
		this.refresh();

	}

	// reset this panel
	public void refresh() {
		this.revalidate();
		this.repaint();

	}

}
