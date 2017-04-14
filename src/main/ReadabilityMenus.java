package main;

import helpers.InputHelper;
import helpers.MenuBuilder;

/**
 * Here is where we define what each menu can do in our console application.
 * 
 * @author RossWeinstein
 */

public class ReadabilityMenus {

	private Readability readability;
	private InputHelper input;

	public ReadabilityMenus() {
		this.readability = new Readability();
		this.input = new InputHelper();
	}

	/**
	 * The entry into the text readability application, brings up the main menu
	 */
	public void runApplication() {

		boolean runApp = true;
		while (runApp) {
			runApp = this.mainMenu(runApp);
		}
	}

	/**
	 * Our main menu. It displays the two main functions of the application: Add
	 * items to the database and search the database for information
	 * 
	 * @param runApp
	 *            boolean always true, from runApp
	 * @return boolean returns true if user want to sill use the application;
	 *         false if they want to quit
	 */
	private boolean mainMenu(boolean runApp) {

		MenuBuilder theMainMenu = new MenuBuilder("Text Readability Analyzer", "Analyze Directory", "Search Database",
				"Exit");
		System.out.println(theMainMenu.displayMenuWithBanner());
		int selection = this.input.askForSelection(theMainMenu.getMenuItems());

		if (selection == 1) {
			System.out.println();
			this.analyzeMenu();
			System.out.println();
		} else if (selection == 2) {
			System.out.println();
			this.databaseMenu();
			System.out.println();
		} else {
			runApp = false;
		}
		return runApp;
	}

	/**
	 * This menus is where we can supply directories to search
	 */
	private void analyzeMenu() {
		System.out.println("Analyze Directory:\n");
		String directory = this.input.askForString("Enter Path (enter \"main\" to return to main menu): ");

		if (!directory.equalsIgnoreCase("main")) {
			this.readability.setDirectory(directory);
			this.analyzeFiles();
		}
	}

	/**
	 * Send all the files off to be analyzed
	 */
	private void analyzeFiles() {
		if (this.readability.NumberOfFilesFound() != 0) {
			System.out.println("Finding Files...");
			System.out.println("Analyzing " + this.readability.NumberOfFilesFound() + " Files...");
			this.readability.runAnalysis();
		}
	}

	/**
	 * This menu allows us to select which database interaction we want to do
	 */
	private void databaseMenu() {

		MenuBuilder database = new MenuBuilder("DATABASE MENU--", "Show All Entries", "Get Most Difficult Text",
				"Get Least Difficult Text", "Get Selection of Most And Least Difficult Text",
				"Delete Entry from Database", "Delete Entire Database", "Back to Main Menu");

		boolean search = true;
		while (search) {
			System.out.println(database.displayMenuWithoutBanner());
			System.out.println();
			int selection = this.input.askForSelection(database.getMenuItems());
			search = this.databaseSelection(selection, search);
			System.out.println();
		}
	}

	/**
	 * This controls what interactions we actually do with the database
	 * 
	 * @param selection
	 *            int which database interaction would be like to do
	 * @param search
	 *            boolean from database menu always true
	 * @return boolean do we still want to interact with the database
	 */
	private boolean databaseSelection(int selection, boolean search) {

		if (selection == 1) {
			System.out.println("\nAll Results in Database:\n");
			this.readability.showAllResults();
		} else if (selection == 2) {
			int mostDifficultLimit = this.input.askForInteger("Limit: ");
			System.out.println();
			this.readability.getMostDifficultTest(mostDifficultLimit);
		} else if (selection == 3) {
			int leastDifficultLimit = this.input.askForInteger("Limit: ");
			System.out.println();
			this.readability.getLeastDifficultText(leastDifficultLimit);
		} else if (selection == 4) {
			int limit = this.input.askForInteger("Limit: ");
			System.out.println();
			this.readability.getMostAndLeastDifficultText(limit);
		} else if (selection == 5) {
			String title = this.input.askForString("Entry Text Title to Delete: ");
			if (this.readability.deleteEntry(title)) {
				System.out.println(title + " deleted successfully");
			}
		} else if (selection == 6) {
			boolean pleaseDelete = this.input
					.askBinaryQuestion("Are you sure you want to delete entire database? (y/n)", "y", "n");
			if (pleaseDelete) {
				this.readability.clearDatabase();
				System.out.println("Database Cleared");
			}
		} else if (selection == 7) {
			search = false;
		}
		return search;
	}
}
