package textAnalysis;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class TextAnalyzerTest {

	Path filePath = Paths.get("test/sampleForReadFile/someText.txt/");
	
	@Test
	public void isOfTypeTextCompleteAnalysis() {
		assertTrue("FAIL - build wrong class type", TextAnalyzer.analyzeText(filePath, AnalysisOption.ANALYZE_ALL) instanceof TextCompleteAnalysis);
	}
	
	@Test
	public void isOfTypeTextReadability() {
		assertTrue("FAIL - build wrong class type", TextAnalyzer.analyzeText(filePath, AnalysisOption.READABILITY_ONLY) instanceof TextReadability);
	}
	
	@Test
	public void isOfTypeTextStatistics() {
		assertTrue("FAIL - build wrong class type", TextAnalyzer.analyzeText(filePath, AnalysisOption.STATS_ONLY) instanceof TextStatistics);
	}
}
