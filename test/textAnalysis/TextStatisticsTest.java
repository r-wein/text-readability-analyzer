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

public class TextStatisticsTest {
	
	TextStatistics textstats;
	Path filePath = Paths.get("test/sampleForReadFile/someText.txt/");
	Map<String, Double> expected;
	
	@Before
	public void setUp() {
		this.textstats = new TextStatistics(filePath);
		this.textstats.analyze();
		this.expected = new HashMap<>();
		this.populateMap();
	}
	
	private void populateMap() {
		this.expected.put("words", 14.0);
		this.expected.put("sentences", 3.0);
		this.expected.put("letters", 54.0);
		this.expected.put("syllables", 17.0);
		this.expected.put("complex words", 0.0);
		this.expected.put("unique words", 9.0);
		this.expected.put("words per sentence", this.expected.get("words") / this.expected.get("sentences"));
		this.expected.put("letters per word", this.expected.get("letters") / this.expected.get("words"));
		this.expected.put("syllables per word", this.expected.get("syllables") / this.expected.get("words"));
	}
	
	@Test
	public void correctResults() {
		assertThat(this.textstats.getResults(), is(this.expected));
	}
	
	@Test
	public void correctTitle() {
		assertEquals("FAIL - wrong title", "someText.txt", this.textstats.getTitle());
	}
	
	

}
