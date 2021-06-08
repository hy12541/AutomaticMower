package com.gatech.osmowsis.ui.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

import com.gatech.osmowsis.controller.SimController;
import com.gatech.osmowsis.ui.model.UISimState;
import com.gatech.osmowsis.ui.panel.MainPanel;
import com.gatech.osmowsis.ui.service.ImageService;
import com.gatech.osmowsis.ui.service.ModelService;

// main gui
public class MainJFrame extends JFrame {

	private static final long serialVersionUID = -1979055543679455519L;
	private String title;

	public MainJFrame(String testFileName, boolean showState) throws HeadlessException {
		super();
		title = "OSMOWSIS--Team 11";

		// initialize UISimstate
		SimController simController = new SimController();
		simController.initialize(testFileName, showState);

		// initialize UI elements
		initialize();

	}

	// initialize fields
	private void initialize() {

		// calculate and set square width
		//int squareWidth = calculateSquareWidth();
		
		// initialize all images and image icons
		// set square width 
		//ImageService.getInstance().setSquareWidth(squareWidth);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// set title and icon image
		this.setTitle(title);
		this.setIconImage(ImageService.getInstance().getMainJframeIconImage());

		// set size to 100% of the screen size
		/*Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = (int) (screenSize.width*0.9);
		int screenHeight = (int) (screenSize.height*0.9);
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));*/
		
		this.setPreferredSize(new Dimension(1800, 1000));
		
	/*	ImageService.getInstance().setScreenWidth(1200);
		ImageService.getInstance().setScreenHeight(900);
*/
		MainMenuBar mainMenuBar = new MainMenuBar();
		this.setJMenuBar(mainMenuBar);

		// create content panel
		MainPanel mainPanel = new MainPanel();
		

		// create panel with scroll bar
		JScrollPane scrollPane = new JScrollPane(mainPanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//scrollPane.setPreferredSize(new Dimension((int)(ImageService.getInstance().getScreenWidth()*0.8), (int)(ImageService.getInstance().getScreenHeight()*0.8)));
		this.add(scrollPane);
	
		/*
		JPanel panelC = new JPanel(new BorderLayout());
		panelC.setPreferredSize(new Dimension(ImageService.getInstance().getScreenWidth(), ImageService.getInstance().getScreenHeight()));
		panelC.add(scrollPane,BorderLayout.CENTER);
		panelC.setVisible(true);
		this.add(panelC);*/
	
		// disable resize
		//this.setResizable(false);
		// show Jframe
		this.pack();
		System.out.println(this.getWidth());

		this.setVisible(true);
		
	}

	// calculate lawn square width by screen size
	/*private int calculateSquareWidth() {
		int squareWidth = 60;
		int lawnWidth, lawnHeight;
		UISimState uiSimstate = ModelService.getUISimState();
		if (uiSimstate != null && uiSimstate.getUilawn() != null && uiSimstate.getUilawn().getSize() != null) {
			lawnWidth = uiSimstate.getUilawn().getSize().getWidth();
			lawnHeight = uiSimstate.getUilawn().getSize().getLength();

			int w = 500 / lawnWidth;
			int l = 500 / lawnHeight;
			int result = Math.min(w, l);
			if (squareWidth < result) {
				squareWidth = result;
			}
		}

		return squareWidth;
	}
*/
}
