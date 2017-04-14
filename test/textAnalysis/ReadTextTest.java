package textAnalysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReadTextTest {

	ReadText reader;
	Path filePath = Paths.get("test/sampleForReadFile/someText.txt/");
	Map<String, List<Integer>> expected;
	
	@Before
	public void setUp() {
		this.reader = new ReadText(filePath);
		this.expected = new HashMap<>();
		this.reader.processText();
		this.populateMap();
	}
	
	private void populateMap() {
		this.expected.put("this", Arrays.asList(3,1));
		this.expected.put("is", Arrays.asList(2,1));
		this.expected.put("a", Arrays.asList(2,1));
		this.expected.put("sentence", Arrays.asList(2,2));
		this.expected.put("second", Arrays.asList(1,2));
		this.expected.put("get", Arrays.asList(1,1));
		this.expected.put("rid", Arrays.asList(1,1));
		this.expected.put("of", Arrays.asList(1,1));
		this.expected.put("please", Arrays.asList(1,1));
	}
	
	@Test
	public void canReadFile() {
		assertTrue("FAIL - did not read file", this.reader.readFile());
	}
	
	@Test
	public void foundCorrectFileName() {
		assertEquals("FAIL - wrong file name", "someText.txt", this.reader.getFileName());
	}
	
	@Test
	public void foundAllSentences() {
		assertEquals("FAIL - could not find all sentences", 3, this.reader.getSentenceCount());
	}
	
	@Test
	public void textAnalyzed() {
		assertThat(this.reader.getTextDictionary(), is(this.expected));
	}
	
	
	
	
}
