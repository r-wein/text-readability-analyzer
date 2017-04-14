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

public class TextCompleteAnalysisTest {
	
	TextCompleteAnalysis comAnalysis;
	Path filePath = Paths.get("test/sampleForReadFile/someText.txt/");
	Map<String, Double> expected;
	
	@Before
	public void setUp() {
		this.comAnalysis = new TextCompleteAnalysis(filePath);
		this.comAnalysis.analyze();
		this.expected = new HashMap<String, Double>();
		this.populatedExpectedResults();
	}
	
	private void populatedExpectedResults() {
		this.expected.put("words", 14.0);
		this.expected.put("sentences", 3.0);
		this.expected.put("letters", 54.0);
		this.expected.put("syllables", 17.0);
		this.expected.put("complex words", 0.0);
		this.expected.put("unique words", 9.0);
		this.expected.put("words per sentence", this.expected.get("words") / this.expected.get("sentences"));
		this.expected.put("letters per word", this.expected.get("letters") / this.expected.get("words"));
		this.expected.put("syllables per word", this.expected.get("syllables") / this.expected.get("words"));
		this.expected.put("flesch reading ease", 206.835 - (1.015 * this.expected.get("words per sentence"))
				- (84.6 * this.expected.get("syllables per word")));
		this.expected.put("flesch-kincaid grade level", (0.39 * this.expected.get("words per sentence"))
				+ (11.8 * this.expected.get("syllables per word")) - 15.59);
		this.expected.put("gunning fog", 0.4 * (this.expected.get("words per sentence")
				+ 100 * (this.expected.get("complex words") / this.expected.get("words"))));
		this.expected.put("coleman-liau", 0.0588 * (this.expected.get("letters per word") * 100)
				- 0.296 * ((this.expected.get("sentences") / this.expected.get("words")) * 100) - 15.8);
		this.expected.put("SMOG", 1.0430
				* Math.sqrt(this.expected.get("complex words") * (30 / this.expected.get("sentences")) + 3.1291));
		this.expected.put("automated readability", 4.71 * this.expected.get("letters per word")
				+ 0.5 * this.expected.get("words per sentence") - 21.43);
		this.expected.put("average readability", (this.expected.get("flesch-kincaid grade level") + this.expected.get("gunning fog")
				+ this.expected.get("coleman-liau") + this.expected.get("SMOG")
				+ this.expected.get("automated readability")) / 5.0);
	}
	
	@Test
	public void correctResults() {
		assertThat(this.comAnalysis.getResults(), is(this.expected));
	}
	
	@Test
	public void correctTitle() {
		assertEquals("FAIL - wrong title", "someText.txt", this.comAnalysis.getTitle());
	}

}
