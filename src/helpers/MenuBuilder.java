package helpers;

import java.util.*;

/**
 * This class aids in the building of menus and can display them in one of two
 * ways. The first is without a banner and the other is with a banner. The
 * banner will surround the title with '*' whereas the other just separates the
 * title from the list. It prints the menu options in the form of an ordered
 * list.
 * 
 * @author RossWeinstein
 */

public class MenuBuilder {

	private String menuTitle;
	private List<String> menuItems;

	/**
	 * Constructs a new menu with a title and all possible options as Strings
	 * 
	 * @param title
	 *            what is the title for the menu
	 * @param options
	 *            what options would you like for your menu
	 */
	public MenuBuilder(String title, String... options) {

		// give the menu a title
		this.menuTitle = title;

		// since a variable number of menu options are allowed
		// count how many options were given and organize them in an array
		this.menuItems = new ArrayList<>();

		for (String eachOption : options) {
			this.menuItems.add(eachOption);
		}
	}

	/**
	 * Returns the menu title and all options in order
	 * 
	 * @return String the menu title on its own line without a border
	 */
	public String displayMenuWithoutBanner() {
		return this.menuTitle + "\n" + this.getOrderedListOfMenuItems();
	}

	/**
	 * Returns the menu title inside a border of '*' and all options in order
	 * 
	 * @return String the menu title with a border of '*'
	 */
	public String displayMenuWithBanner() {
		this.banner(menuTitle, 1);
		return "\n" + this.getOrderedListOfMenuItems();
	}

	/**
	 * Returns the menu title
	 * 
	 * @return String the menu title
	 */
	public String getMenuTitle() {
		return this.menuTitle;
	}

	/**
	 * Returns each option within the menu
	 * 
	 * @return List the menu items as a list
	 */
	public List<String> getMenuItems() {
		return this.menuItems;
	}

	/**
	 * Returns each option within the menu as an ordered list
	 * 
	 * @return String the menu items ordered in a list with their order printed
	 *         before each item
	 */
	public String getOrderedListOfMenuItems() {

		// create a blank string
		String list = "";

		// we want our list to start at 1
		int position = 1;

		// loop through all the menuItems and order them into a list
		for (String eachItem : menuItems) {
			list += position + ": " + eachItem + "\n";
			position++;
		}
		return list;
	}

	/**
	 * Returns the selected menu item
	 * 
	 * @param item
	 *            what menu item are you interested in
	 * @return String the menu item selected
	 */
	public String selectItem(int item) {

		return this.menuItems.get(item - 1);
	}

	/**
	 * Creates a banner of '*' around a given String. Padding determines the
	 * spacing within the border.
	 * 
	 * @param String
	 *            what title would you like within the banner
	 * @param paddingAmount
	 *            how much space would you like between the banner text and the
	 *            '*'
	 */
	private void banner(String title, int paddingAmount) {

		int totalNumberOfRows = paddingAmount * 2 + 3;
		int totalNumberOfCols = title.length() * 2 + paddingAmount * 2 + 2;

		for (int currentRow = 0; currentRow != totalNumberOfRows; ++currentRow) {

			int currentCol = 0;

			while (currentCol != totalNumberOfCols) {

				if (currentRow == paddingAmount + 1 && currentCol == paddingAmount + 1 + title.length() / 2) {
					System.out.print(title);
					currentCol += title.length();
				} else {
					if (currentRow == 0 || currentRow == totalNumberOfRows - 1 || currentCol == 0
							|| currentCol == totalNumberOfCols - 1)
						System.out.print("*");
					else
						System.out.print(" ");
					++currentCol;
				}
			}
			System.out.print("\n");
		}
	}
}
