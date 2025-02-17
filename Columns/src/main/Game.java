package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Scanner;

import enigma.console.TextWindow;
import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseMotionListener;
import nodes.Node_MLL;
import nodes.Node_SLL;
import structures.MLL;
import structures.SLL;
import utilities.HSTable;
import utilities.Player;
import utilities.Visuals;

import java.io.IOException;

public class Game {
	// Console
	private static Visuals visuals = new Visuals();
	private static enigma.console.Console console = Enigma.getConsole("Definitely Not Solitaire", visuals.SCREEN_WIDTH,
			visuals.SCREEN_HEIGHT, visuals.SCREEN_FONTSIZE);
	public static TextWindow screen = console.getTextWindow();

	// RNG
	public static Random rand = new Random();

	// List structures
	public static SLL box = new SLL();
	public static MLL columns = new MLL();
	private SLL selection = new SLL();

	public static HSTable hstable = new HSTable();

	// Game status
	private Boolean boxSelected = false;
	private int mX, mY;
	private int cursorCol = 0;
	private int cursorRow = 0;
	private int selectionCol = 0;
	private int selectionRow = 0;

	// Player status
	private int currentScore = 0;
	private int transferCount = 0;
	private int setCount = 0;
	Player currentPlayer = new Player("player[0]", "player[1]", 0);

	/**
	 * Calculate endGameScore, Append current player to the high score list. Clear
	 * screen, display high score list
	 */
	public void end() {
		double endGameScore = 0;
		if (transferCount != 0) {
			endGameScore = 100 * setCount + (currentScore / transferCount);
		}
		currentPlayer.setScore(endGameScore);
		hstable.addToList(currentPlayer);
		try {
			hstable.writeToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		visuals.clearScreen();
		visuals.gameOver();
		visuals.printListPlayer(hstable.getList());
	}

	/**
	 * Test all columns for potential ordered sets. When found, clear the column
	 * where a set resides and increment currentScore.
	 */
	public void checkSets() {
		for (int i = 1; i < 6; i++) {
			if (columns.size(i) == 10) {
				int streak = 0;
				Node_MLL tempColumn = columns.getHead();
				for (int j = 1; j < i; j++) {
					tempColumn = tempColumn.getNext();
				}
				Node_SLL current = tempColumn.getChild();
				if ((int) current.getData() == 1) {
					int lastNumber = 0;
					while (current != null) {
						if ((int) current.getData() - lastNumber == 1) {
							streak++;
							lastNumber = (int) current.getData();
							current = current.getNext();
						} else {
							break;
						}
					}
				} else if ((int) current.getData() == 10) {
					int lastNumber = 11;
					while (current != null) {
						if (lastNumber - (int) current.getData() == 1) {
							streak++;
							lastNumber = (int) current.getData();
							current = current.getNext();
						} else {
							break;
						}
					}
				}

				if (streak == 10) {
					tempColumn.setChild(null);
					currentScore += 1000;
					setCount++;
				}
			}
		}
	}

	/**
	 * Place the box head to the column where the cursor is at. Increment transfer
	 * count. Next of head becomes the head of the box. Render visual updates. Box
	 * is no longer selected.
	 */
	public void carryBox() {
		columns.addTo(box.getHead().getData(), cursorCol);
		transferCount++;
		box.deleteNumber((int) box.getHead().getData());
		visuals.updateBox(box, false);
		selection.setHead(null);
		boxSelected = false;
	}

	public void carrySelection() {
		if (selectionRow <= columns.size(selectionCol)) {
			// MUST #1: Selected row of selected column is not empty.
			Node_MLL tempColumn = columns.getHead();
			for (int i = 1; i < selectionCol; i++) {
				tempColumn = tempColumn.getNext();
			}
			Node_SLL tempSelection = tempColumn.getChild();
			Node_SLL breakpoint = tempSelection;
			for (int i = 1; i < selectionRow; i++) {
				if (i == selectionRow - 1) {
					breakpoint = tempSelection;
				}
				tempSelection = tempSelection.getNext();
			}
			selection.setHead(tempSelection);

			if (columns.size(cursorCol) + selection.size() < 23 && selectionCol != cursorCol) {
				// MUST #2: A column's size is 22 at max.
				// MUST #3: Selection column and target column can't be the same.
				if (columns.size(cursorCol) > 0) {
					// CASE #1: Target column is not empty.
					if (Math.abs((int) selection.getHead().getData()
							- (int) columns.getLastNode(cursorCol).getData()) <= 1) {
						// RULE: Head of selection must be equal, one more or one less than the target
						// column's last element.
						columns.appendTo(selection.getHead(), cursorCol);
						transferCount++;
						if (selectionRow != 1) {
							// exception handling for "breakpoint" usage.
							if (cursorCol != selectionCol) {
								breakpoint.setNext(null);
							}
						} else {
							tempColumn.setChild(null);
						}
					}
				} else if ((int) selection.getHead().getData() == 1 || (int) selection.getHead().getData() == 10) {
					// CASE #2: Target column is empty but head of selection is either 1 or 10.
					columns.appendTo(selection.getHead(), cursorCol);
					transferCount++;
					if (selectionRow != 1) {
						// exception handling for "breakpoint" usage.
						if (cursorCol != selectionCol) {
							breakpoint.setNext(null);
						}
					} else {
						tempColumn.setChild(null);
					}
				}
			}
		}
	}

	/**
	 * Prepare the initial box and column states. Render screen.
	 */
	public void initialize() {

		// initialize box and the first 30 cards
		box.shuffleFifty();

		// initialize 5 columns with 6 elements from the box in each
		for (int c = 1; c < 6; c++) {
			columns.newColumn(c);

			for (int r = 0; r < 6; r++) {
				columns.addTo(box.getHead().getData(), c);
				box.deleteNumber(box.getHead().getData());
			}
		}

		// initialize screen
		visuals.clearScreen();
		visuals.initScreen();
	}

	/**
	 * Call initialization method, then initialize the key listeners.
	 */
	public void start() {
		visuals.gameStart();
		
		while (true) {
			visuals.renderString(visuals.SCREEN_WIDTH/2-12, visuals.SCREEN_HEIGHT/2+7, "                                              ", visuals.whiteBanner);
			visuals.renderString(visuals.SCREEN_WIDTH/2-12, visuals.SCREEN_HEIGHT/2+8, "                                              ", visuals.whiteBanner);
			
			visuals.renderString(visuals.SCREEN_WIDTH/2-12, visuals.SCREEN_HEIGHT/2+7, "Please enter your name: ", visuals.whiteBanner);
			screen.setCursorPosition(visuals.SCREEN_WIDTH/2-12, visuals.SCREEN_HEIGHT/2+8);
			
			String name = console.readLine();
			name = name.trim();
			if (name.length() > 0) {
				currentPlayer.setName(name);
			} else {
				continue;
			}
			
			visuals.renderString(visuals.SCREEN_WIDTH/2-12, visuals.SCREEN_HEIGHT/2+7, "                                              ", visuals.whiteBanner);
			visuals.renderString(visuals.SCREEN_WIDTH/2-12, visuals.SCREEN_HEIGHT/2+8, "                                              ", visuals.whiteBanner);
			
			visuals.renderString(visuals.SCREEN_WIDTH/2-12, visuals.SCREEN_HEIGHT/2+7, "Please enter your surname: ", visuals.whiteBanner);
			screen.setCursorPosition(visuals.SCREEN_WIDTH/2-12, visuals.SCREEN_HEIGHT/2+8);
			String surname = console.readLine();
			surname = surname.trim();
			if (surname.length() > 0) {
				currentPlayer.setSurname(surname);
			} else {
				continue;
			}
			break;
		}

		
		initialize();
		// KEY LISTENER
		KeyListener keyListener = new KeyListener() {
			// not used
			@Override
			public void keyTyped(KeyEvent e) {
			}

			// not used
			@Override
			public void keyReleased(KeyEvent e) {
			}

			// USED
			@Override
			public void keyPressed(KeyEvent event) {
				int keyCode = event.getKeyCode();

				switch (keyCode) {

				case KeyEvent.VK_B:
					if (!boxSelected && box.getHead() != null) {
						selection.setHead(box.getHead());
						boxSelected = true;
					} else if (boxSelected && box.getHead() != null) {
						selection.setHead(null);
						boxSelected = false;
					}
					selectionCol = 0;
					selectionRow = 0;
					visuals.updateBox(box, boxSelected);
					visuals.updateColumns(cursorCol, cursorRow, false);
					break;

				case KeyEvent.VK_Z:
					if (cursorCol > 0 && cursorRow > 0) {
						selectionCol = cursorCol;
						selectionRow = cursorRow;

						boxSelected = false;
						visuals.updateBox(box, false);
						visuals.updateColumns(cursorCol, cursorRow, true);
					}
					break;

				case KeyEvent.VK_X:
					if (cursorCol > 0 && cursorRow > 0) {
						// MUST #1: Cursor is at a proper target.
						if (boxSelected) {
							// CASE 1: Box is selected.
							if (columns.size(cursorCol) > 0) {
								if ((Math.abs((int) selection.getHead().getData()
										- (int) columns.getLastNode(cursorCol).getData()) <= 1)) {
									carryBox();
								}
							} else if ((int) selection.getHead().getData() == 1
									|| (int) selection.getHead().getData() == 10) {
								carryBox();
							}
						} else if (selectionCol > 0 && selectionRow > 0) {
							// CASE 2: Selection inside a column is made (properly).
							carrySelection();
						}
					}

					// Reset selection.
					selectionCol = 0;
					selectionRow = 0;
					boxSelected = false;

					checkSets();

					// Render visuals after game logic is applied.
					visuals.updateBox(box, false);
					visuals.updateColumns(cursorCol, cursorRow, false);
					visuals.updateTransfer(transferCount);
					visuals.updateScore(currentScore);

					if (setCount == 5) {
						end();
					}
					break;

				case KeyEvent.VK_E:
					end();
					break;
				}
			}
		};
		screen.addKeyListener(keyListener);

		// MOUSE MOTION LISTENER
		TextMouseMotionListener mouseMotionListener = new TextMouseMotionListener() {
			// not used
			@Override
			public void mouseDragged(TextMouseEvent arg0) {
			}

			// USED
			@Override
			public void mouseMoved(TextMouseEvent event) {
				mX = event.getX();
				mY = event.getY();

				cursorRow = mY - 5;
				if (cursorRow < 0) {
					cursorRow = 0;
				}

				if (mX >= 9 && mX <= 16) {
					cursorCol = 1;
				} else if (mX >= 17 && mX <= 24) {
					cursorCol = 2;
				} else if (mX >= 25 && mX <= 32) {
					cursorCol = 3;
				} else if (mX >= 33 && mX <= 40) {
					cursorCol = 4;
				} else if (mX >= 41 && mX <= 48) {
					cursorCol = 5;
				} else {
					cursorCol = 0;
					cursorRow = 0;
				}

				// bottom - left indicators
				// visuals.renderString(18, visuals.SCREEN_HEIGHT - 1, " ", visuals.bg_black);
				// visuals.renderString(18, visuals.SCREEN_HEIGHT - 1, cursorCol + "," +
				// cursorRow, visuals.bg_black);
			}
		};
		screen.addTextMouseMotionListener(mouseMotionListener);

	}
}
