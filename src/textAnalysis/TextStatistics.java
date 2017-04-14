package textAnalysis;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class finds many basic statistics for a test.
 * 
 * It will find:
 * 
 * total words, total sentences, total syllables, total characters, total
 * complex words (those with 3 or more syllables), unique words, words per
 * sentence, letters per word, syllables per word
 * 
 * It returns all that information in a Map to be used, if desired, in the
 * calculation of several readability tests
 * 
 * @author RossWeinstein
 */
public class TextStatistics implements TextAnalysis {

	private ReadText text;
	private Map<String, Double> results;

	public TextStatistics(Path text) {
		this.text = new ReadText(text);
		this.text.processText();
		this.results = new HashMap<>();
	}

	// TextAnalysis interface : get all statistics results
	public Map<String, Double> getResults() {
		return this.results;
	}

	// TextAnalysis interface : get text's title
	public String getTitle() {
		return this.text.getFileName();
	}

	// TextAnalysis interface : analyze the text for the desired results
	public void analyze() {
		this.findTotals();
		this.findAverages();
	}

	/**
	 * From the text dictionary build in ReadText, loop through and find all the
	 * different static values that are desired for the readability tests
	 */
	private void findTotals() {

		int[] resultsKeeper = new int[5];

		for (Map.Entry<String, List<Integer>> entry : this.text.getTextDictionary().entrySet()) {

			resultsKeeper[0] += entry.getValue().get(0);
			resultsKeeper[1] += (entry.getKey().length() * entry.getValue().get(0));
			resultsKeeper[2] += (entry.getValue().get(0) * entry.getValue().get(1));
			resultsKeeper[3] += entry.getValue().get(1) >= 3 ? 1 : 0;
			resultsKeeper[4] += 1;
		}
		populateResultsMap(resultsKeeper);
	}

	/**
	 * Populate our map with the statistic results from analyzing the text
	 * 
	 * @param resultsKeeper
	 *            int the values for each statistic to be put into the results
	 *            Map
	 */
	private void populateResultsMap(int[] resultsKeeper) {
		this.results.put("words", (double) resultsKeeper[0]);
		this.results.put("sentences", (double) this.text.getSentenceCount());
		this.results.put("letters", (double) resultsKeeper[1]);
		this.results.put("syllables", (double) resultsKeeper[2]);
		this.results.put("complex words", (double) resultsKeeper[3]);
		this.results.put("unique words", (double) resultsKeeper[4]);
	}

	/**
	 * Find several averages needed to perform our readability tests and add
	 * those to the Map
	 */
	private void findAverages() {

		this.results.put("words per sentence", this.results.get("words") / this.results.get("sentences"));
		this.results.put("letters per word", this.results.get("letters") / this.results.get("words"));
		this.results.put("syllables per word", this.results.get("syllables") / this.results.get("words"));
	}
}
