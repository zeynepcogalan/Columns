package utilities;

import java.awt.Color;

import enigma.console.*;
import main.Game;
import nodes.Node_SLL;
import nodes.Node_DLL;
import nodes.Node_MLL;
import structures.DLL;
import structures.SLL;

public class Visuals {
	// Screen offset value
	private final int BOX_OFFSET_Y = 0;

	// Screen variables
	public final int SCREEN_WIDTH = 80;
	public final int SCREEN_HEIGHT = 30;
	public final int SCREEN_FONTSIZE = 18;

	// Custom Colors
	private final Color custom_green = new Color(10, 110, 70);
	private final Color custom_darkgreen = new Color(5, 55, 35);
	private final Color custom_red = new Color(100, 0, 0);

	// Attributes
	// Background attributes
	public final TextAttributes bg_green = new TextAttributes(custom_green, custom_green);
	public final TextAttributes bg_black = new TextAttributes(Color.white, Color.black);
	// Number attributes
	public final TextAttributes defaults = new TextAttributes(Color.white, custom_green);
	public final TextAttributes selected = new TextAttributes(Color.white, custom_darkgreen);
	// Box attributes
	public final TextAttributes box = new TextAttributes(Color.black, Color.white);
	public final TextAttributes shadows = new TextAttributes(Color.white, custom_darkgreen);
	// Hand attributes
	public final TextAttributes red = new TextAttributes(Color.red, Color.white);
	public final TextAttributes black = new TextAttributes(Color.black, Color.white);
	// Banner attributes
	public final TextAttributes whiteBanner = new TextAttributes(Color.white, Color.black);
	public final TextAttributes greenBanner = new TextAttributes(Color.green, Color.black);
	public final TextAttributes redBanner = new TextAttributes(Color.red, Color.black);

	// Output methods
	public void renderChar(int x, int y, char character, TextAttributes attr) {
		Game.screen.output(x, y, character, attr);
	}

	public void renderString(int x, int y, String str, TextAttributes attr) {
		Game.screen.setCursorPosition(x, y);
		Game.screen.output(str, attr);
	}

	public void clearScreen() {
		for (int i = 2; i < SCREEN_WIDTH - 2; i++) {
			for (int j = 1; j < SCREEN_HEIGHT - 1; j++) {
				renderChar(i, j, ' ', bg_green);
			}
		}
	}
	
	public void deleteScreen() {
		for (int i = 2; i < SCREEN_WIDTH - 2; i++) {
			for (int j = 1; j < SCREEN_HEIGHT - 1; j++) {
				renderChar(i, j, ' ', whiteBanner);
			}
		}
	}

	// Updates
	public void updateTransfer(int transfer) {
		// clear
		renderString(69, 5, "      ", defaults);
		// update
		renderString(69, 5, "" + transfer, defaults);
	}

	public void updateColumns(int x, int y, Boolean selectionMade) {
		// clear
		for (int i = 6; i < 29; i++) {
			for (int j = 11; j < 46; j++) {
				renderString(j, i, " ", defaults);
			}
		}
		// update
		if (selectionMade) {
			Node_MLL temp = Game.columns.getHead();
			int c = 1;
			while (temp != null) {
				Node_SLL temp2 = temp.getChild();
				int m = 1;
				while (temp2 != null) {
					if (c == x && m >= y) {
						renderString(4 + 8 * c, 5 + m, String.valueOf(temp2.getData()), selected);
						if ((int) temp2.getData() != 10) {
							renderString(4 + 8 * c, 5 + m, String.valueOf(temp2.getData()) + " ", selected);
						}
					} else {
						renderString(4 + 8 * c, 5 + m, String.valueOf(temp2.getData()), defaults);
					}
					m++;
					temp2 = temp2.getNext();
				}
				c++;
				temp = temp.getNext();
			}
		} else {
			Node_MLL temp = Game.columns.getHead();
			int c = 1;
			while (temp != null) {
				Node_SLL temp2 = temp.getChild();
				int m = 1;
				while (temp2 != null) {

					renderString(4 + 8 * c, 5 + m, String.valueOf(temp2.getData()), defaults);

					m++;
					temp2 = temp2.getNext();
				}
				c++;
				temp = temp.getNext();
			}
		}
	}

	public void updateScore(int score) {
		// clear
		renderString(65, 4, "         ", bg_green);
		// update
		renderString(59, 4, "SCORE: " + score, defaults);
	}

	public void updateBox(SLL Box, Boolean boxSelected) {
		// clear
		renderString(59, 7 + BOX_OFFSET_Y, "       ", box);
		renderString(59, 8 + BOX_OFFSET_Y, "       ", box);
		renderString(59, 9 + BOX_OFFSET_Y, "       ", box);
		renderString(59, 10 + BOX_OFFSET_Y, "       ", box);
		renderString(59, 11 + BOX_OFFSET_Y, "       ", box);

		if (boxSelected) {
			renderString(59, 6 + BOX_OFFSET_Y, "BOX:   ", selected);
		} else {
			renderString(59, 6 + BOX_OFFSET_Y, "BOX:   ", defaults);
		}

		// update
		if (Box.getHead() == null) {
			renderString(59, 7 + BOX_OFFSET_Y, "       ", box);
			renderString(59, 8 + BOX_OFFSET_Y, "       ", box);
			renderString(59, 9 + BOX_OFFSET_Y, "       ", box);
			renderString(59, 10 + BOX_OFFSET_Y, "       ", box);
			renderString(59, 11 + BOX_OFFSET_Y, "       ", box);
		} else {
			int n = (int) Box.getHead().getData();
			switch (n) {
			case 0:
				renderString(59, 7 + BOX_OFFSET_Y, "       ", red);
				renderString(59, 8 + BOX_OFFSET_Y, "       ", red);
				renderString(59, 9 + BOX_OFFSET_Y, "       ", red);
				renderString(59, 10 + BOX_OFFSET_Y, "       ", red);
				renderString(59, 11 + BOX_OFFSET_Y, "       ", red);
				break;
			case 1:
				renderString(59, 7 + BOX_OFFSET_Y, n + "      ", black);
				renderString(59, 8 + BOX_OFFSET_Y, "       ", black);
				renderString(59, 9 + BOX_OFFSET_Y, "   ♠   ", black);
				renderString(59, 10 + BOX_OFFSET_Y, "       ", black);
				renderString(59, 11 + BOX_OFFSET_Y, "      " + n, black);
				break;
			case 2:
				renderString(59, 7 + BOX_OFFSET_Y, n + "      ", red);
				renderString(59, 8 + BOX_OFFSET_Y, "   ♥   ", red);
				renderString(59, 9 + BOX_OFFSET_Y, "       ", red);
				renderString(59, 10 + BOX_OFFSET_Y, "   ♥   ", red);
				renderString(59, 11 + BOX_OFFSET_Y, "      " + n, red);
				break;
			case 3:
				renderString(59, 7 + BOX_OFFSET_Y, n + "      ", black);
				renderString(59, 8 + BOX_OFFSET_Y, "   ♣   ", black);
				renderString(59, 9 + BOX_OFFSET_Y, "   ♣   ", black);
				renderString(59, 10 + BOX_OFFSET_Y, "   ♣   ", black);
				renderString(59, 11 + BOX_OFFSET_Y, "      " + n, black);
				break;
			case 4:
				renderString(59, 7 + BOX_OFFSET_Y, n + "      ", red);
				renderString(59, 8 + BOX_OFFSET_Y, "  ♦ ♦  ", red);
				renderString(59, 9 + BOX_OFFSET_Y, "       ", red);
				renderString(59, 10 + BOX_OFFSET_Y, "  ♦ ♦  ", red);
				renderString(59, 11 + BOX_OFFSET_Y, "      " + n, red);
				break;
			case 5:
				renderString(59, 7 + BOX_OFFSET_Y, n + "      ", black);
				renderString(59, 8 + BOX_OFFSET_Y, "  ♠ ♠  ", black);
				renderString(59, 9 + BOX_OFFSET_Y, "   ♠   ", black);
				renderString(59, 10 + BOX_OFFSET_Y, "  ♠ ♠  ", black);
				renderString(59, 11 + BOX_OFFSET_Y, "      " + n, black);
				break;
			case 6:
				renderString(59, 7 + BOX_OFFSET_Y, n + "      ", red);
				renderString(59, 8 + BOX_OFFSET_Y, "  ♥ ♥  ", red);
				renderString(59, 9 + BOX_OFFSET_Y, "  ♥ ♥  ", red);
				renderString(59, 10 + BOX_OFFSET_Y, "  ♥ ♥  ", red);
				renderString(59, 11 + BOX_OFFSET_Y, "      " + n, red);
				break;
			case 7:
				renderString(59, 7 + BOX_OFFSET_Y, n + "      ", black);
				renderString(59, 8 + BOX_OFFSET_Y, "  ♣ ♣  ", black);
				renderString(59, 9 + BOX_OFFSET_Y, " ♣ ♣ ♣ ", black);
				renderString(59, 10 + BOX_OFFSET_Y, "  ♣ ♣  ", black);
				renderString(59, 11 + BOX_OFFSET_Y, "      " + n, black);
				break;
			case 8:
				renderString(59, 7 + BOX_OFFSET_Y, n + "     ", red);
				renderString(59, 8 + BOX_OFFSET_Y, " ♦ ♦ ♦ ", red);
				renderString(59, 9 + BOX_OFFSET_Y, "  ♦ ♦  ", red);
				renderString(59, 10 + BOX_OFFSET_Y, " ♦ ♦ ♦ ", red);
				renderString(59, 11 + BOX_OFFSET_Y, "      " + n, red);
				break;
			case 9:
				renderString(59, 7 + BOX_OFFSET_Y, n + "      ", black);
				renderString(59, 8 + BOX_OFFSET_Y, "  ♠♠♠  ", black);
				renderString(59, 9 + BOX_OFFSET_Y, "  ♠♠♠  ", black);
				renderString(59, 10 + BOX_OFFSET_Y, "  ♠♠♠  ", black);
				renderString(59, 11 + BOX_OFFSET_Y, "      " + n, black);
				break;
			case 10:
				renderString(59, 7 + BOX_OFFSET_Y, n + "     ", red);
				renderString(59, 8 + BOX_OFFSET_Y, "  ♥♥♥  ", red);
				renderString(59, 9 + BOX_OFFSET_Y, " ♥♥ ♥♥ ", red);
				renderString(59, 10 + BOX_OFFSET_Y, "  ♥♥♥  ", red);
				renderString(59, 11 + BOX_OFFSET_Y, "     " + n, red);
				break;
			}
		}
	}

	// Display high score
	public void printListPlayer(DLL hs) {
		if (hs.getHead() == null)
			renderString(0, 0, "-", red);
		else {
			int i = 10;
			Node_DLL temp = hs.getHead();
			while (temp != null) {
				Player data = (Player) temp.getData();
				renderString(30, i, "                                   ", whiteBanner);
				renderString(30, i + 1, data.getName() + " " + data.getSurname() + ":" + " " + data.getScore(),
						greenBanner);
				System.out.println();
				temp = temp.getNext();
				i = i + 2;
			}
		}
	}

	// Initialization
	public void initScreen() {
		// TEMPORARY
		//renderString(2, SCREEN_HEIGHT - 1, "Column / Row: ", bg_black);

		// Columns
		renderString(11, 4, " C1      C2      C3      C4      C5 ", defaults);
		renderString(11, 5, " --      --      --      --      -- ", defaults);

		// Box
		renderString(60, 8 + BOX_OFFSET_Y, "       ", shadows);
		renderString(60, 9 + BOX_OFFSET_Y, "       ", shadows);
		renderString(60, 10 + BOX_OFFSET_Y, "       ", shadows);
		renderString(60, 11 + BOX_OFFSET_Y, "       ", shadows);
		renderString(60, 12 + BOX_OFFSET_Y, "       ", shadows);

		renderString(59, 6 + BOX_OFFSET_Y, "BOX:", defaults);
		updateBox(Game.box, false);

		// UI
		renderString(59, 5, "TRANSFER: 0", defaults);
		renderString(59, 4, "SCORE: 0", defaults);

		// First column update
		updateColumns(0, 0, false);
	}

	public void gameStart() {
		deleteScreen();
		renderString(5, 2 + BOX_OFFSET_Y, "  _____  ______ ______ _____ _   _ _____ _______ ______ _  __     __", whiteBanner);
		renderString(5, 3 + BOX_OFFSET_Y, " |  __ \\|  ____|  ____|_   _| \\ | |_   _|__   __|  ____| | \\ \\   / / ", whiteBanner);
		renderString(5, 4 + BOX_OFFSET_Y, " | |  | | |__  | |__    | | |  \\| | | |    | |  | |__  | |  \\ \\_/ /  ", whiteBanner);
		renderString(5, 5 + BOX_OFFSET_Y, " | |  | |  __| |  __|   | | | . ` | | |    | |  |  __| | |   \\   /   ", whiteBanner);
		renderString(5, 6 + BOX_OFFSET_Y, " | |__| | |____| |     _| |_| |\\  |_| |_   | |  | |____| |____| |    ", whiteBanner);	
		renderString(5, 7 + BOX_OFFSET_Y, " |_____/|______|_|    |_____|_| \\_|_____|  |_|  |______|______|_|    ", whiteBanner);
		
		renderString(30, 8 + BOX_OFFSET_Y, " _   _  ____ _______ ", redBanner);
		renderString(30, 9 + BOX_OFFSET_Y, "| \\ | |/ __ \\__   __|", redBanner);
		renderString(30, 10 + BOX_OFFSET_Y, "|  \\| | |  | | | |   ", redBanner);
		renderString(30, 11 + BOX_OFFSET_Y, "| |\\  | |__| | | |   ", redBanner);
		renderString(30, 12 + BOX_OFFSET_Y, "|_| \\_|\\____/  |_|   ", redBanner);
		
		renderString(2, 13 + BOX_OFFSET_Y, " _______  _______  ___      ___  _______  _______  ___   ______    _______ ", greenBanner);
		renderString(2, 14 + BOX_OFFSET_Y, "|       ||       ||   |    |   ||       ||   _   ||   | |    _ |  |       |", greenBanner);
		renderString(2, 15 + BOX_OFFSET_Y, "|  _____||   _   ||   |    |   ||_     _||  |_|  ||   | |   | ||  |    ___|", greenBanner);
		renderString(2, 16 + BOX_OFFSET_Y, "| |_____ |  | |  ||   |    |   |  |   |  |       ||   | |   |_||_ |   |___ ", greenBanner);
		renderString(2, 17 + BOX_OFFSET_Y, "|_____  ||  |_|  ||   |___ |   |  |   |  |       ||   | |    __  ||    ___|", greenBanner);
		renderString(2, 18 + BOX_OFFSET_Y, " _____| ||       ||       ||   |  |   |  |   _   ||   | |   |  | ||   |___ ", greenBanner);
		renderString(2, 19 + BOX_OFFSET_Y, "|_______||_______||_______||___|  |___|  |__| |__||___| |___|  |_||_______|", greenBanner);
		
	}
	
	public void gameOver(){
		deleteScreen();
		renderString(13, 4 + BOX_OFFSET_Y, "   _________    __  _________   ____ _    ____________  ", redBanner);
		renderString(13, 5 + BOX_OFFSET_Y, "  / ____/   |  /  |/  / ____/  / __ \\\\|  / / ____/ __ \\\\", redBanner);
		renderString(13, 6 + BOX_OFFSET_Y, " / / __/ /| | / /|_/ / __/    / / / / | / / __/ / /_/ / ", redBanner);
		renderString(13, 7 + BOX_OFFSET_Y, "/ /_/ / ___ |/ /  / / /___   / /_/ /| |/ / /___/ _, _/  ", redBanner);
		renderString(13, 8 + BOX_OFFSET_Y, "\\\\____/_/ |_/_/  /_/_____/  \\\\____/ |___/_____/_/ |_|   ", redBanner);	
	}
}
