package textAnalysis;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TextReadabilityTest {

	TextReadability readable;
	Path filePath = Paths.get("test/sampleForReadFile/someText.txt/");
	Map<String, Double> statResults;
	Map<String, Double> expected;
	
	@Before
	public void setUp() {
		this.readable = new TextReadability(filePath);
		this.readable.analyze();
		this.statResults = new HashMap<>();
		this.populateStatResultsMap();
		this.expected = new HashMap<>();
		this.populateExpectedtatResultsMap();
	}
	
	private void populateStatResultsMap() {
		this.statResults.put("words", 14.0);
		this.statResults.put("sentences", 3.0);
		this.statResults.put("letters", 54.0);
		this.statResults.put("syllables", 17.0);
		this.statResults.put("complex words", 0.0);
		this.statResults.put("unique words", 9.0);
		this.statResults.put("words per sentence", this.statResults.get("words") / this.statResults.get("sentences"));
		this.statResults.put("letters per word", this.statResults.get("letters") / this.statResults.get("words"));
		this.statResults.put("syllables per word", this.statResults.get("syllables") / this.statResults.get("words"));
	}
	
	private void populateExpectedtatResultsMap() {
		this.expected.put("flesch reading ease", 206.835 - (1.015 * this.statResults.get("words per sentence"))
				- (84.6 * this.statResults.get("syllables per word")));
		this.expected.put("flesch-kincaid grade level", (0.39 * this.statResults.get("words per sentence"))
				+ (11.8 * this.statResults.get("syllables per word")) - 15.59);
		this.expected.put("gunning fog", 0.4 * (this.statResults.get("words per sentence")
				+ 100 * (this.statResults.get("complex words") / this.statResults.get("words"))));
		this.expected.put("coleman-liau", 0.0588 * (this.statResults.get("letters per word") * 100)
				- 0.296 * ((this.statResults.get("sentences") / this.statResults.get("words")) * 100) - 15.8);
		this.expected.put("SMOG", 1.0430
				* Math.sqrt(this.statResults.get("complex words") * (30 / this.statResults.get("sentences")) + 3.1291));
		this.expected.put("automated readability", 4.71 * this.statResults.get("letters per word")
				+ 0.5 * this.statResults.get("words per sentence") - 21.43);
		this.expected.put("average readability", (this.expected.get("flesch-kincaid grade level") + this.expected.get("gunning fog")
				+ this.expected.get("coleman-liau") + this.expected.get("SMOG")
				+ this.expected.get("automated readability")) / 5.0);
	}
	
	@Test
	public void correctResults() {
		assertThat(this.readable.getResults(), is(this.expected));
	}
	
	@Test
	public void correctAnalysis() {
		assertThat(this.readable.getTextAnalysisResults(), is (this.statResults));
	}
 	
	@Test
	public void correctTitle() {
		assertEquals("FAIL - wrong title", "someText.txt", this.readable.getTitle());
	}
	
	
}
