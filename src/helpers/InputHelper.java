package helpers;

import java.util.List;
import java.util.Scanner;

/**
 * This class aids in getting input from the user. It takes care of all checks
 * and prompts
 * 
 * @author RossWeinstein
 */

public class InputHelper {

	private Scanner input;

	/** Constructs a basic InputHelper ready to take input */
	public InputHelper() {
		this.input = new Scanner(System.in);
	}

	/**
	 * Returns a String for the given question
	 * 
	 * @param question
	 *            what question would you like to ask the user
	 * @return String the user's answer to the question
	 */
	public String askForString(String question) {

		System.out.print(question);
		String answer = this.input.next();
		return answer;
	}

	/**
	 * Returns a int for a given question. This method checks to see if the
	 * answer is a int before returning anything.
	 * 
	 * @param question
	 *            what question would you like to ask the user
	 * @return int the user's answer to the question. Will re-prompt question if
	 *         answer is not an int.
	 */
	public int askForInteger(String question) {

		int answer = 0;
		boolean itsANumber = false;

		while (!itsANumber) {

			try {
				String possibleInt = this.askForString(question);
				answer = Integer.parseInt(possibleInt);
				itsANumber = true;

			} catch (NumberFormatException e) {
				System.out.println("You Must Enter An Integer.");
			}
		}

		return answer;

	}

	/**
	 * Returns the user's selection based on the options given. Takes a List and
	 * makes sure the number of selection is equal to the length of the List
	 * 
	 * @param choices
	 *            a list of choices that you want to give the user
	 * @return int the choice the user decided upon
	 */
	public int askForSelection(List<String> choices) {

		int validAnswer = 0;

		while (!this.numberInRange(validAnswer, choices.size())) {

			validAnswer = this.askForInteger("Selection (1-" + choices.size() + "): ");
		}

		return validAnswer;
	}

	/**
	 * Checks if a number is given is within a predefined range
	 * 
	 * @param number
	 *            the lower range of the possible choices
	 * @param upperRange
	 *            the upper range of the possible choices
	 * @return boolean true if the number is within the range; false otherwise
	 */
	private boolean numberInRange(int number, int upperRange) {
		return number >= 1 && number <= upperRange;
	}

	/**
	 * This method asks a question with only two possible responses. It returns
	 * true if the first selection is chosen, false otherwise
	 * 
	 * @param question
	 *            what question would you like to ask the user
	 * @param yes
	 *            what choice would bring about a true response
	 * @param no
	 *            what choice would bring about a false response
	 * @return boolean returns true if they chose the yes option; false if they
	 *         chose to no
	 */
	public boolean askBinaryQuestion(String question, String yes, String no) {

		String answer = "";
		boolean questionUnanswered = true;
		boolean result = false;

		while (questionUnanswered) {

			System.out.print(question + ": ");
			answer = this.input.next();

			if (answer.equalsIgnoreCase(yes)) {
				result = true;
				questionUnanswered = false;
			} else if (answer.equalsIgnoreCase(no)) {
				result = false;
				questionUnanswered = false;
			} else {
				System.out.println("Invalid Entry. You Must Enter " + yes + " or " + no);
			}
		}
		return result;
	}
}
