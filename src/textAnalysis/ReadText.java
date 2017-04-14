package textAnalysis;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is responsible for parsing the text. While parsing it records all
 * the different words, the number of times each of those words are used, and
 * the syllables in each of the words.
 * 
 * @author RossWeinstein
 */
public class ReadText {

	private Path filePath;
	private List<String> rawList;
	private List<String> cleanedList;
	private Map<String, List<Integer>> textDictionary;
	private SyllableDetector syllablesDict;
	private int sentenceCount;

	public ReadText(Path filePath) {
		this.filePath = filePath;
		this.rawList = new ArrayList<>();
		this.cleanedList = new ArrayList<>();
		this.textDictionary = new HashMap<>();
		this.syllablesDict = new SyllableDetector();
		this.sentenceCount = 0;
	}

	@Override
	public String toString() {
		return this.filePath.toString();
	}

	/**
	 * How many sentences were found in the text
	 * 
	 * @return int the number of sentences found
	 */
	public int getSentenceCount() {
		return this.sentenceCount;
	}

	/**
	 * Takes a path and gets just the file name
	 * 
	 * @return String the file name with extension
	 */
	public String getFileName() {
		return this.filePath.getFileName().toString();
	}

	/**
	 * A Map containing all the information recorded from the text. The key for
	 * each map entry is each unique word in the text with two values: the first
	 * being the number of times that word is used in the text followed by the
	 * number of syllables in that word.
	 * 
	 * @return Map with each unique word with the number
	 *         of times that word is used and its syllable count
	 */
	public Map<String, List<Integer>> getTextDictionary() {
		return new TreeMap<>(this.textDictionary);
	}

	/**
	 * Analyze the text and make our text dictionary
	 */
	public void processText() {

		if (this.readFile()) {
			this.cleanText(this.rawList);
			this.createTextDictionary();
		}
	}

	/**
	 * From the results of parsing the text, create a dictionary with each word
	 * as a key whose values are their number found in the text and their
	 * syllable count
	 * 
	 * @return boolean false if we do not have any words to add to dictionary;
	 *         true once dictionary is finished
	 */
	private boolean createTextDictionary() {

		// make sure we have something to do before we do it
		if (this.cleanedList.size() == 0) {
			return false;
		}

		for (String word : this.cleanedList) {

			if (this.textDictionary.containsKey(word)) {
				this.textDictionary.put(word, this.updateValues(word));
			} else {
				this.textDictionary.put(word, Arrays.asList(1, this.syllablesDict.getSyllableCount(word)));
			}
		}
		return true;
	}

	/**
	 * Gets the supplied words usage in the text and syllable count to add as a
	 * List to the Map
	 * 
	 * @param word
	 *            String a key for the word dictionary Map
	 * @return a List in the proper form for the Map
	 */
	private List<Integer> updateValues(String word) {
		return Arrays.asList(this.textDictionary.get(word).get(0) + 1, this.textDictionary.get(word).get(1));
	}

	/**
	 * This method cleans, removes all unnecessary punctuation, from a provided
	 * text
	 * 
	 * @param text
	 *            List the text to clean up
	 */
	private void cleanText(List<String> text) {

		for (String word : text) {

			if (this.isAtSentenceEnd(word)) {
				this.sentenceCount++;
			}

			if (this.hasDoubleHyphen(word)) {
				List<String> miniList = this.separateWords(word);
				this.cleanText(miniList);
			} else {

				String cleanedWord = this.cleanWord(word);

				if (cleanedWord.length() != 0) {
					this.cleanedList.add(cleanedWord.toLowerCase());
				}
			}
		}
	}

	/**
	 * Determines if we are at the end of a sentence
	 * 
	 * @param word
	 *            String a word to analyze
	 * @return boolean true is we are at the end of a sentence; false otherwise
	 */
	private boolean isAtSentenceEnd(String word) {
		return word.endsWith(".") || word.endsWith("?") || word.endsWith("!");
	}

	/**
	 * See if a word has a double hyphen (i.e. word--word)
	 * 
	 * @param word
	 *            String a word to analyze
	 * @return boolean true is we have a double hyphen; false otherwise
	 */
	private boolean hasDoubleHyphen(String word) {

		for (int i = 0; i < word.length(); i++) {

			if (i < word.length() - 1 && word.charAt(i) == '-' && word.charAt(i + 1) == '-') {
				return true;
			}
		}
		return false;
	}

	/**
	 * If we have a double hyphen word, this will separate that into two words
	 * and return a mini-list of the two words
	 * 
	 * @param word
	 *            String the double hyphened word to separate
	 * @return List the two words taken from the double hyphened one
	 */
	private List<String> separateWords(String word) {

		List<String> dividedWordList = new ArrayList<>();

		for (int i = 0; i < word.length(); i++) {

			if (word.charAt(i) == '-') {
				dividedWordList.add(word.substring(0, i));
				dividedWordList.add(word.substring(i + 1, word.length()));
				return dividedWordList;
			}
		}
		return new ArrayList<>();
	}

	/**
	 * This method gets rid of all unnecessary information around a word and
	 * only keeps necessary punctuation (i.e. single hyphen words and 's)
	 * 
	 * @param word
	 *            String for the current word we want to clean
	 */
	private String cleanWord(String word) {
		String cleanedWord = "";

		for (int i = 0; i < word.length(); i++) {

			// special case: checks for the rare occurrence of the word 'tis
			if (this.wordIsTis(word)) {
				return word.substring(0, 4);
			}

			if (this.isValidLetter(word.charAt(i), cleanedWord, word, i)) {
				cleanedWord += word.charAt(i);
			}
		}

		while (cleanedWord.endsWith("'") || cleanedWord.endsWith("-")) {
			cleanedWord = cleanedWord.substring(0, cleanedWord.length() - 1);
		}

		return cleanedWord;
	}

	/**
	 * For the special case of 'tis where we do not want to remove the ' before
	 * the word
	 * 
	 * @param word
	 *            String the word to check against
	 * @return boolean is the word 'tis or not
	 */
	private boolean wordIsTis(String word) {
		return word.length() >= 4 && word.substring(0, 4).equals("'tis");
	}

	/**
	 * Determines if we are dealing with a valid letter and do not want to
	 * remove it from the String.
	 * 
	 * @param letter
	 *            char the current letter
	 * @param cleanedWord
	 *            String the clean word we are builder
	 * @param word
	 *            String the unaltered word we are currently cleaning
	 * @param index
	 *            int where we are in the word
	 * @return boolean true is we want to add the letter to word; false if we do
	 *         not
	 */
	private boolean isValidLetter(char letter, String cleanedWord, String word, int index) {
		return Character.isLetter(letter)
				|| this.isInWordsMiddle(cleanedWord, word, index) && this.isValidPunctuation(letter);
	}

	/**
	 * Determines if we are in the middle of a word or not. This is needed
	 * because we need to know if we can look for a single hyphen or an
	 * apostrophe s. Otherwise we do not want to consider any punctuation.
	 * 
	 * @param constructedWord
	 *            String the word we are building
	 * @param rawWord
	 *            String the unaltered String we are testing
	 * @param place
	 *            int where are we in the string
	 * @return boolean true if we are in the middle of the word; false otherwise
	 */
	private boolean isInWordsMiddle(String constructedWord, String rawWord, int place) {
		return constructedWord.length() > 0 && rawWord.length() - place > 1;
	}

	/**
	 * Determines, if we are in the middle of a String, if we can add hyphen or
	 * apostrophe.
	 * 
	 * @param letter
	 *            char the letter we are testing
	 * @return boolean true if we do want to add it to the String; false
	 *         otherwise
	 */
	private boolean isValidPunctuation(char letter) {
		return letter == '\'' || letter == '-';
	}

	/**
	 * Read through the file and split each word based solely on spaces. Our
	 * list will initially contain all punctuation.
	 * 
	 * @return boolean true is no problem reading file; false if there is an
	 *         error
	 */
	public boolean readFile() {
		try (Stream<String> lines = Files.lines(this.filePath, StandardCharsets.UTF_8)) {
			this.rawList = lines.flatMap(eachLine -> Stream.of(eachLine.split(" "))).collect(Collectors.toList());
			return true;
		} catch (IOException e) {
			System.out.println("IOException. Could not find file. Check file path");
			return false;
		}
	}
}
