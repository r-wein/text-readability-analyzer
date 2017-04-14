package fileHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class searches a directory and returns all the .txt files that exists
 * within that directory.
 * 
 * @author RossWeinstein
 */
public class GetTXTFiles {

	private List<Path> foundFiles;
	private String directory;

	public GetTXTFiles() {
		this.foundFiles = new ArrayList<>();
		this.directory = "";
	}

	/**
	 * File path to the directory of .txt files
	 * 
	 * @param directory
	 *            String the file path
	 */
	public void setPath(String directory) {
		this.directory = directory;
	}

	/**
	 * Returns all the text files as a List. If the List is empty, it calls
	 * search() to fill the List
	 * 
	 * @return List with the found files
	 */
	public List<Path> getTxtPaths() {
		if (this.foundFiles.isEmpty()) {
			this.search();
		}
		return this.foundFiles;
	}

	/**
	 * Searches a directory and all its sub-directories for .txt files
	 */
	private void search() {
		try {
			Files.walk(Paths.get(this.directory)).forEach(file -> this.addFileToList(file.toAbsolutePath()));
		} catch (IOException e) {
			System.out.println("Error - Could not find file path");
		}
	}

	/**
	 * When a file is found, it checks to see if it is a .txt file before adding
	 * it to the list
	 * 
	 * @param theFile the file found within the directory
	 */
	private void addFileToList(Path theFile) {

		if (theFile.toString().endsWith(".txt")) {
			this.foundFiles.add(theFile);
		}
	}
}
