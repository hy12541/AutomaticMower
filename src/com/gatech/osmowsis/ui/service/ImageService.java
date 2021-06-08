package com.gatech.osmowsis.ui.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

// load all image icons and images in memory
public class ImageService {
	private static ImageService imageService;
	private int squareWidth = 60;
	private int screenWidth;
	private int screenHeight;
	private ImageIcon grassImageIcon;
	private ImageIcon compassImageIcon;
	private ImageIcon gopherImageIcon;
	private ImageIcon mowerImageIcon;
	private ImageIcon directionNorthImageIcon;
	private ImageIcon directionNortheastImageIcon;
	private ImageIcon directionEastImageIcon;
	private ImageIcon directionSoutheastImageIcon;
	private ImageIcon directionSouthImageIcon;
	private ImageIcon directionSouthwestImageIcon;
	private ImageIcon directionWestImageIcon;
	private ImageIcon directionNorthwestImageIcon;
	private ImageIcon fenceImageIcon0;
	private ImageIcon fenceImageIcon1;
	private ImageIcon fenceImageIcon2;
	private ImageIcon batteryImageIcon;
	private ImageIcon rechargingPadImageIcon;
	private Image mainJframeIconImage;

	private ImageService() {
		// initialize fields
		setGrassImageIcon();
		setCompassImageIcon();
		setGopherImageIcon();
		setMowerImageIcon();
		setDirectionImageIcon();
		setFenceImageIcon();
		setBatteryImageIcon();
		setRechargingPadImageIcon();
		setMainJframeIconImage();
	}
	
	public static ImageService getInstance() {
		if (imageService == null) {
			imageService = new ImageService();
		}
		return imageService;

	}
	
	public int getSquareWidth() {
		return squareWidth;
	}

	public void setSquareWidth(int squareWidth) {
		this.squareWidth = squareWidth;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return this.screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	// read and load image
	private Image getImage(String imagePath, int scalex, int scaley) {
		Image newimg = null;
		try {
			BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/"+imagePath));
			ImageIcon imageIcon = new ImageIcon(img); // load the image to a imageIcon
			Image image = imageIcon.getImage(); // transform it
			newimg = image.getScaledInstance(squareWidth / scalex, squareWidth / scaley, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException ioe) {
			ioe.printStackTrace();

		}

		return newimg;

	}

	// grass image icon
	private void setGrassImageIcon() {
		Image grassImage = getImage("images/grass.png", 1, 1);
		if (grassImage != null) {
			grassImageIcon = new ImageIcon(grassImage); // transform it back
		}

	}

	public ImageIcon getGrassImageIcon() {
		return grassImageIcon;
	}

	// compass image icon
	private void setCompassImageIcon() {
		Image compassImage = getImage("images/compass.png", 1, 1);
		if (compassImage != null) {
			compassImageIcon = new ImageIcon(compassImage); // transform it back
		}

	}

	public ImageIcon getCompassImageIcon() {
		return compassImageIcon;
	}

	// gopher image icon
	private void setGopherImageIcon() {
		Image gopherImage = getImage("images/gopher.png", 1, 1);
		if (gopherImage != null) {
			gopherImageIcon = new ImageIcon(gopherImage); // transform it back
		}

	}

	public ImageIcon getGopherImageIcon() {
		return this.gopherImageIcon;
	}

	// mower image icon
	private void setMowerImageIcon() {
		Image mowerImage = getImage("images/mower.png", 2, 2);
		if (mowerImage != null) {
			this.mowerImageIcon = new ImageIcon(mowerImage); // transform it back
		}

	}

	public ImageIcon getMowerImageIcon() {
		return this.mowerImageIcon;
	}

	// image icons of 8 directions
	private void setDirectionImageIcon() {
		int scalex = 4, scaley = 4;
		Image directionNorthImage = getImage("images/direction-north.png", scalex, scaley);
		Image directionNortheastImage = getImage("images/direction-northeast.png", scalex, scaley);
		Image directionEastImage = getImage("images/direction-east.png", scalex, scaley);
		Image directionSoutheastImage = getImage("images/direction-southeast.png", scalex, scaley);
		Image directionSouthImage = getImage("images/direction-south.png", scalex, scaley);
		Image directionSouthwestImage = getImage("images/direction-southwest.png", scalex, scaley);
		Image directionWestImage = getImage("images/direction-west.png", scalex, scaley);
		Image directionNorthwestImage = getImage("images/direction-northwest.png", scalex, scaley);

		if (directionNorthImage != null) {
			directionNorthImageIcon = new ImageIcon(directionNorthImage);
		}
		if (directionNortheastImage != null) {
			directionNortheastImageIcon = new ImageIcon(directionNortheastImage);
		}
		if (directionEastImage != null) {
			directionEastImageIcon = new ImageIcon(directionEastImage);
		}
		if (directionSoutheastImage != null) {
			directionSoutheastImageIcon = new ImageIcon(directionSoutheastImage);
		}
		if (directionSouthImage != null) {
			directionSouthImageIcon = new ImageIcon(directionSouthImage);
		}
		if (directionSouthwestImage != null) {
			directionSouthwestImageIcon = new ImageIcon(directionSouthwestImage);
		}
		if (directionWestImage != null) {
			directionWestImageIcon = new ImageIcon(directionWestImage);
		}
		if (directionNorthwestImage != null) {
			directionNorthwestImageIcon = new ImageIcon(directionNorthwestImage);
		}

	}

	public ImageIcon getDirectionImageIcon(String direction) {
		ImageIcon imageIcon = null;
		switch (direction) {
		case "north":
			imageIcon = directionNorthImageIcon;
			break;
		case "northeast":
			imageIcon = directionNortheastImageIcon;
			break;
		case "east":
			imageIcon = directionEastImageIcon;
			break;
		case "southeast":
			imageIcon = directionSoutheastImageIcon;
			break;
		case "south":
			imageIcon = directionSouthImageIcon;
			break;
		case "southwest":
			imageIcon = directionSouthwestImageIcon;
			break;
		case "west":
			imageIcon = directionWestImageIcon;
			break;
		case "northwest":
			imageIcon = directionNorthwestImageIcon;
			break;
		}

		return imageIcon;
	}

	// fence image icons
	private void setFenceImageIcon() {
		Image fenceImage0 = getImage("images/fence-0.png", 1, 1);
		Image fenceImage1 = getImage("images/fence-1.png", 1, 1);
		Image fenceImage2 = getImage("images/fence-2.png", 1, 1);
		if (fenceImage0 != null) {
			fenceImageIcon0 = new ImageIcon(fenceImage0); // transform it back
		}

		if (fenceImage1 != null) {
			fenceImageIcon1 = new ImageIcon(fenceImage1); // transform it back
		}

		if (fenceImage2 != null) {
			fenceImageIcon2 = new ImageIcon(fenceImage2); // transform it back
		}

	}

	public ImageIcon getFenceImageIcon(int fenceType) {
		ImageIcon imageIcon = null;
		switch (fenceType) {
		case 0:
			imageIcon = fenceImageIcon0;
			break;
		case 1:
			imageIcon = fenceImageIcon1;
			break;
		case 2:
			imageIcon = fenceImageIcon2;
			break;

		}

		return imageIcon;
	}

	// battery image icons
	private void setBatteryImageIcon() {
		Image batteryImage = getImage("images/battery.png", 4, 8);
		if (batteryImage != null) {
			batteryImageIcon = new ImageIcon(batteryImage); // transform it back
		}

	}

	public ImageIcon getBatteryImageIcon() {
		return batteryImageIcon;
	}

	// recharging pad image icons
	private void setRechargingPadImageIcon() {
		Image rechargingPadImage = getImage("images/rechargingpad.png", 4, 4);
		if (rechargingPadImage != null) {
			rechargingPadImageIcon = new ImageIcon(rechargingPadImage); // transform it back
		}

	}

	public ImageIcon getRechargingPadImageIcon() {
		return rechargingPadImageIcon;
	}

	private void setMainJframeIconImage() {
		mainJframeIconImage = getImage("images/icon.png", 1, 1);

	}

	public Image getMainJframeIconImage() {
		return mainJframeIconImage;
	}

}
