package textAnalysis;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * This class if for when we want to do analyze a text with all available tests.
 * It will return the results of both a statistical and readability tests.
 * 
 * @author RossWeinstein
 */

public class TextCompleteAnalysis implements TextAnalysis {
	
	private TextReadability readable;
	
	public TextCompleteAnalysis(Path filePath) {
		readable = new TextReadability(filePath);
		this.readable.analyze();
	}
	
	
	public String getTitle() {
		return readable.getTitle();
	}
	
	public void analyze() {
		readable.analyze();
	}
	
	public Map<String, Double> getResults() {
		Map<String, Double> resultsMap = new HashMap<>();
		resultsMap.putAll(readable.getResults());
		resultsMap.putAll(readable.getTextAnalysisResults());
		return resultsMap;
	}
}
