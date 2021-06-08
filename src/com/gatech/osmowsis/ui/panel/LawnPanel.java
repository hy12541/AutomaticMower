package com.gatech.osmowsis.ui.panel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.JPanel;
import com.gatech.osmowsis.ui.model.UIRechargingPad;
import com.gatech.osmowsis.ui.service.ModelService;
import com.gatech.osmowsis.simstate.Location;
import com.gatech.osmowsis.simstate.MowerStatus;
import com.gatech.osmowsis.square.Square;
import com.gatech.osmowsis.square.SquareState;
import com.gatech.osmowsis.ui.model.UIGopher;
import com.gatech.osmowsis.ui.model.UILawn;
import com.gatech.osmowsis.ui.model.UIMower;

// panel to show lawn board
public class LawnPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private UILawn uiLawn;
	private UIMower[] uiMowers;
	private UIGopher[] uiGophers;
	private UIRechargingPad[] uiRechargingPads;
	private Map<Location, Square> scannedSquares;
	private JPanel[][] board;
	private int boardEdge_west;
	private int boardEdge_east;
	private int boardEdge_north;
	private int boardEdge_south;
	private int lawn_width;
	private int lanw_length;
	private int boardWidth;
	private int boardLength;

	public LawnPanel() {
		super();
		 // set ui simulation state
		resetUISimState();
		// initialize ui elements
		initialize();
		// set LawnPanel in factory
		ModelService.setLawnPanel(this);

	}
   // set ui simulation state
	private void resetUISimState() {
		uiLawn = ModelService.getUILawn();
		uiMowers = ModelService.getUIMowers();
		uiGophers = ModelService.getUIGophers();
		uiRechargingPads = ModelService.getRechargingPads();
		scannedSquares = ModelService.getAllScannedSquares();
	}

	// initialize ui elements
	private void initialize() {
		if (uiLawn != null && uiMowers != null && this.uiRechargingPads != null) {
			// this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			initBoard();
			updateLawn();
			showBoard();

		}

	}

	// set side area of lawn board, like index, compass, fence and empty squares
	private void initBoard() {
		boardEdge_west = 2;
		boardEdge_east = 2;
		boardEdge_north = 1;
		boardEdge_south = 2;
		lawn_width = uiLawn.getSize().getWidth();
		lanw_length = uiLawn.getSize().getLength();

		boardLength = lanw_length + boardEdge_north + boardEdge_south;
		boardWidth = lawn_width + boardEdge_west + boardEdge_east;

		board = new JPanel[boardLength][boardWidth];

		int i, j;
		for (i = 0; i < boardLength; i++)
			for (j = 0; j < boardWidth; j++) {
				int x = j - boardEdge_west;
				int y = boardLength - boardEdge_south - i - 1;

				boolean isHighlighted = false;
				Square square = null;
				// check if it will be highlighted
				if (scannedSquares != null && scannedSquares.size() > 0) {
					square = scannedSquares.get(new Location(x, y));
					if (square != null) {
						isHighlighted = true;
					}
				}

				if (i == 0) {
					if (j >= boardEdge_west - 1 && j <= boardWidth - boardEdge_east) {
						board[i][j] = new FencePanel(0, isHighlighted);
					} else {
						board[i][j] = new EmptyPanel(false, null, isHighlighted);
					}
				} else if (i >= boardEdge_north && i < boardLength - boardEdge_south) {
					if (j == 0) {
						board[i][j] = new IndexPanel(boardLength - boardEdge_south - boardEdge_north - i);
					} else if (j == boardEdge_west - 1) {
						board[i][j] = new FencePanel(2, isHighlighted);
					} else if (j == boardWidth - boardEdge_east) {
						board[i][j] = new FencePanel(1, isHighlighted);
					} else {
						board[i][j] = new EmptyPanel(false, null, isHighlighted);
					}

				} else if (i == boardLength - boardEdge_south) {
					if (j >= boardEdge_west - 1 && j <= boardWidth - boardEdge_east) {
						board[i][j] = new FencePanel(0, isHighlighted);
					} else {
						board[i][j] = new EmptyPanel(false, null, isHighlighted);
					}
				} else if (i == boardLength - boardEdge_south + 1) {
					if (j == 0) {
						board[i][j] = new CompassPanel();
					} else if (j >= boardEdge_west && j < boardWidth - boardEdge_east) {
						board[i][j] = new IndexPanel(j - boardEdge_west);
					} else {
						board[i][j] = new EmptyPanel(false, null, isHighlighted);
					}
				} else {
					board[i][j] = new EmptyPanel(false, null, isHighlighted);
				}

			}

	}

	// update lawn board
	private void updateLawn() {
		int i, j, k, r, s;
		for (i = 0; i < boardLength; i++)
			for (j = 0; j < boardWidth; j++) {
				if (i >= boardEdge_north && i < boardLength - boardEdge_south && j >= boardEdge_west
						&& j < boardWidth - boardEdge_east) {

					int x = j - boardEdge_west;
					int y = boardLength - boardEdge_south - i - 1;
					board[i][j] = new IndexPanel(x);
					boolean hasRechargingPad = false;
					boolean isHighlighted = false;

					UIMower uiMower = null;
					UIGopher uiGopher = null;
					Square square = null;

					// Check if there is a recharging pad
					if (uiRechargingPads != null) {
						for (r = 0; r < uiRechargingPads.length; r++) {
							if (uiRechargingPads[r].getLocation().getxCoordinate() == x
									&& uiRechargingPads[r].getLocation().getyCoordinate() == y) {
								hasRechargingPad = true;
								break;
							}
						}
					}

					// check if it will be highlighted
					if (scannedSquares != null && scannedSquares.size() > 0) {
						square = scannedSquares.get(new Location(x, y));
						if (square != null) {
							isHighlighted = true;
						}
					}

					// Check if there is a mower
					if (uiMowers != null) {
						for (k = 0; k < uiMowers.length; k++) {
							if ((uiMowers[k].getMowerStatus() == MowerStatus.ok)
									&& uiMowers[k].getLocation().getxCoordinate() == x
									&& uiMowers[k].getLocation().getyCoordinate() == y) {
								uiMower = uiMowers[k];
								board[i][j] = new MowerPanel(uiMower, hasRechargingPad, isHighlighted);

							}
						}
					}

					// Check if there is a gopher
					if (uiGophers != null) {
						for (s = 0; s < uiGophers.length; s++) {
							if (uiGophers[s].getLocation().getxCoordinate() == x
									&& uiGophers[s].getLocation().getyCoordinate() == y) {
								uiGopher = uiGophers[s];
								break;
							}
						}
					}

					// Otherwise check Lawn Square State
					if (uiMower == null) {

						// check if grass has been cut
						if (uiLawn.getLawnSquares() != null && uiLawn.getLawnSquares()[x][y] != null) {
							if (uiLawn.getLawnSquares()[x][y].getSquareState() == SquareState.grass) {

								board[i][j] = new GrassPanel(hasRechargingPad, null, isHighlighted);

							} else if (uiLawn.getLawnSquares()[x][y].getSquareState() == SquareState.empty) {

								board[i][j] = new EmptyPanel(hasRechargingPad, null, isHighlighted);

							} else if (uiLawn.getLawnSquares()[x][y].getSquareState() == SquareState.gopher_grass) {

								board[i][j] = new GrassPanel(hasRechargingPad, uiGopher, isHighlighted);

							} else if (uiLawn.getLawnSquares()[x][y].getSquareState() == SquareState.gopher_empty) {

								board[i][j] = new EmptyPanel(hasRechargingPad, uiGopher, isHighlighted);

							} else {

								board[i][j] = new EmptyPanel(false, null, isHighlighted);

							}

						} else {

							board[i][j] = new EmptyPanel(false, null, isHighlighted);

						}

					}

				}

			}

	}

	// set layout and and lawn board
	private void showBoard() {
		// set layout
		GridLayout gridLayout = new GridLayout(boardLength, boardWidth, 0, 0);
		this.setLayout(gridLayout);
		
		int i, j;
		for (i = 0; i < boardLength; i++)
			for (j = 0; j < boardWidth; j++) {

				this.add(board[i][j]);
			}
	}

	// update lawn board
	public void updateBoard() {
		// reset state
		resetUISimState();
		// update lawn board
		initBoard();
		updateLawn();
		// remove old lawn board
		this.removeAll();
		// add update board
		showBoard();
		// revalidate and repaint
		refresh();

	}

	// reset panel
	public void refresh() {
		this.revalidate();
		this.repaint();

	}

}
