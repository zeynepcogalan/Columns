package utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import nodes.Node_DLL;
import structures.DLL;


public class HSTable {
	private static File file;
	private static DLL list;
	
	public static File getFile() {
		return file;
	}

	public static void setFile(File file) {
		HSTable.file = file;
	}

	public DLL getList() {
		return list;
	}

	public static void setList(DLL list) {
		HSTable.list = list;
	}

	public HSTable() {
		setFile(new File("HighScoreTable.txt"));
		setList(new DLL());
		fileOperations();
	}
	
	public void addToList(Player p) {
		list.add(p);
	}
	
	public void fileOperations() {
		try {
			Scanner in = new Scanner(file);
			while (in.hasNextLine()) {
				String line = in.nextLine();
				String[] values = line.split("#");
				String[] values2 = values[0].split(" ");
				Player player = new Player(values2[0], values2[1], Double.valueOf(values[1]));
				if (line != null) {
					list.add(player);
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void newHighScoreTable(Player player) {
		list.add(player);
	}

	public void writeToFile() throws IOException { // writes High Score Table in file
		if (!file.exists()) {
			file.createNewFile();
		}
		String newline = System.lineSeparator();
		FileWriter fileWriter = new FileWriter(file, false); // it helps to write all high score table again
																		// and again.
		BufferedWriter bWriter = new BufferedWriter(fileWriter);
		Node_DLL temp = list.getHead();
		while (temp != null) {
			Player player = (Player) temp.getData();
			bWriter.write(player.getName() + " " + player.getSurname() + "#" + player.getScore() + newline);
			temp = temp.getNext();
		}
		bWriter.close();
	}
}

