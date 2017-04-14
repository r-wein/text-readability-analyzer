package textAnalysis;

import java.util.HashMap;
import java.util.Map;
import java.io.*;

/**
 * To find the syllables in a word I used a syllable dictionary posted on GitHub
 * by Troy Watson:
 * 
 * https://github.com/troywatson/Lawrence-Style-Checker
 * 
 * Watson provided two dictionaries to choose from. I selected the optimized
 * list.
 * 
 * https://github.com/troywatson/Lawrence-Style-Checker/blob/master/dict/syllables-optimized-list.txt
 * 
 * For my class here I have a combination of his code (simpleSyllableCounter
 * method) as well as some of mine.
 * 
 */
public class SyllableDetector {

	private Map<String, Integer> syllableDictionary;
	private boolean foundSyllableDictionary;

	public SyllableDetector() {
		this.syllableDictionary = new HashMap<>();
		this.foundSyllableDictionary = this.loadSyllableDictionary();
	}

	/**
	 * Lets us know if we found the syllable dictionary
	 * 
	 * @return boolean true if we successfully loaded the syllable dictionary;
	 *         false otherwise
	 */
	public boolean didFindSyllableDictionary() {
		return this.foundSyllableDictionary;
	}

	/**
	 * Reads our syllable dictionary in and makes it into a map for us to use in
	 * ReadText
	 * 
	 * @return boolean true is we succeeded; false otherwise
	 */
	private boolean loadSyllableDictionary() {

		try {

			FileReader file = new FileReader("src/SyllableDictionary/Syllables.txt");

			BufferedReader input = new BufferedReader(file);

			String line;

			while ((line = input.readLine()) != null) {

				String[] data = line.split(",");
				this.syllableDictionary.put(data[0], Integer.parseInt(data[1]));
			}
			input.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * When provided with a word, it will find the number of syllables in that
	 * word
	 * 
	 * @param word
	 *            String word to find the syllables in
	 * @return int how many syllables were found
	 */
	public int getSyllableCount(String word) {

		if (this.syllableDictionary.containsKey(word)) {
			return this.syllableDictionary.get(word);
		} else {
			return this.simpleSyllableFinder(word);
		}
	}

	/**
	 * The syllable dictionary is not needed for many words whose syllables can
	 * be determined by the number of vowels within that word
	 * 
	 * @param word
	 *            String word to find the syllables in
	 * @return int how many syllables were found
	 */
	private int simpleSyllableFinder(String word) {
		int syllables = word.length() - word.toLowerCase().replaceAll("a|e|i|o|u|", "").length();
		return syllables < 1 ? 1 : syllables;
	}

	public static void main(String[] args) {
		SyllableDetector sd = new SyllableDetector();
		System.out.println(sd.didFindSyllableDictionary());
		System.out.println(sd.getSyllableCount("research"));
	}

}
