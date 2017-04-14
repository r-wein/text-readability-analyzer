package main;

/**
 * This project grew out of a class assignment where we were tasked to find all
 * the different words in Alice in Wonderland using python. While I was
 * completing that assignment, I had the idea to expand it to incorporate
 * readability tests and MySQL. When I had some free time I began to put this
 * together, I decided to write this in Java because I wanted to try out JDBC.
 * 
 * Readability tests try to determine, mathematically, how many years of formal
 * education a person would require to fully understand a text on first reading.
 * There is some debate how accurate their results are, however, to a degree,
 * they can put you in the general area for how complex a text is.
 * 
 * This program, when given a path to a directory, will read all the .txt files
 * within that directory, find number of sentences, words, syllables, and the
 * like before using that information to calculate a number of readability
 * tests. It then averages those values to determine, generally, which texts may
 * be more complicated to read. It stores all those results in a MySQL
 * database where you can look up the most and least difficult entries. If so
 * desired, entries can be deleted as well as truncating the entire database.
 * 
 * @author RossWeinstein
 */

public class Main {

	public static void main(String[] args) {
		ReadabilityMenus textAnalysis = new ReadabilityMenus();
		textAnalysis.runApplication();
		System.out.println("\nExiting Program...");
	}
}
