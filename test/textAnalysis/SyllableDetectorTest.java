package textAnalysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class SyllableDetectorTest {

	SyllableDetector syllables;
	
	@Before
	public void setUp() {
		this.syllables = new SyllableDetector();
	}
	
	@Test
	public void didLoadDictionary() {
		assertTrue("FAIL - could not load dictionary", this.syllables.didFindSyllableDictionary());
	}
	
	@Test
	public void syllablesInSentence() {
		assertEquals("FAIL - wrong syllable count", 2, this.syllables.getSyllableCount("sentence"));
	}
	
	@Test
	public void syllablesInElephant() {
		assertEquals("FAIL - wrong syllable count", 3, this.syllables.getSyllableCount("elephant"));
	}
	
	@Test
	public void syllablesInThis() {
		assertEquals("FAIL - wrong syllable count", 1, this.syllables.getSyllableCount("this"));
	}
	
	@Test
	public void syllablesInMinnesota() {
		assertEquals("FAIL - wrong syllable count", 4, this.syllables.getSyllableCount("minnesota"));
	}
	
	
	
	
	
}
