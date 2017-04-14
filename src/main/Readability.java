package main;

import java.nio.file.Path;
import dbCRUD.DBInteract;
import fileHandler.GetTXTFiles;
import textAnalysis.AnalysisOption;
import textAnalysis.TextAnalysis;
import textAnalysis.TextAnalyzer;

/**
 * This class brings together the text analysis classes, the file search class,
 * and the databases interaction classes.
 * 
 * @author RossWeinstein
 */

public class Readability {

	private DBInteract data;
	private GetTXTFiles fileFilder;

	public Readability() {
		this.fileFilder = new GetTXTFiles();
		this.data = new DBInteract();
	}

	/**
	 * Sets which directory we want to search for .txt files
	 * 
	 * @param filepath
	 *            String the path to the directory
	 */
	public void setDirectory(String filepath) {
		this.fileFilder.setPath(filepath);
	}

	/**
	 * Gets the number of files found in the directory search
	 * 
	 * @return int how many files were found in the directory search
	 */
	public int NumberOfFilesFound() {
		return this.fileFilder.getTxtPaths().size();
	}

	/**
	 * Analyzes each of the .txt for their file statistics and readability and
	 * then adds their results to the database
	 */
	public void runAnalysis() {
		
		int counter = 1;
		
		for (Path text : this.fileFilder.getTxtPaths()) {
			System.out.println("\t" + counter++ + "\t" + text.getFileName());
			TextAnalysis analysis = TextAnalyzer.analyzeText(text, AnalysisOption.ANALYZE_ALL);
			this.data.addEntries(analysis);
		}
		System.out.println("Analysis Complete");
	}

	/**
	 * Truncates the database.
	 */
	public void clearDatabase() {
		this.data.deleteAllEntries();
	}

	/**
	 * Deletes the provided entry from the database
	 * 
	 * @param title
	 *            String the entry you want to delete
	 */
	public boolean deleteEntry(String title) {
		return this.data.deleteEntry(title);
	}

	/**
	 * Prints to the console the items in the database with the highest average
	 * readability
	 * 
	 * @param limit
	 *            int how many results do you want back
	 */
	public void getMostDifficultTest(int limit) {
		this.data.showTop(limit);
	}

	/**
	 * Prints to the console the items in the database with the lowest average
	 * readability
	 * 
	 * @param limit
	 *            int how many results do you want back
	 */
	public void getLeastDifficultText(int limit) {
		this.data.showBottom(limit);
	}

	/**
	 * Prints a selection of text with the highest and lowest average
	 * readability values
	 * 
	 * @param limit
	 *            int how many results do you want back
	 */
	public void getMostAndLeastDifficultText(int limit) {
		this.data.showBestAndWorst(limit);
	}

	/**
	 * Prints every item in the database to the console
	 */
	public void showAllResults() {
		this.data.printAllResults();
	}
}
