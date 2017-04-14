package textAnalysis;

import java.nio.file.Path;

/**
 * Makes sure we can only perform analyze the text is certain ways by
 * controlling what objects we can create
 * 
 * STATS_ONLY will just parse the text and get things like work count, sentence
 * count, etc.
 * 
 * READABILITY_ONLY will only find the results of the different readability
 * tests
 * 
 * ANALYZE_ALL returns all results form both STATS_ONLY and READABILITY_ONLY
 * 
 * @author RossWeinstein
 */

public class TextAnalyzer {

	public static TextAnalysis analyzeText(Path filePath, AnalysisOption option) {

		if (option.equals(AnalysisOption.ANALYZE_ALL)) {
			return new TextCompleteAnalysis(filePath);
		} else if (option.equals(AnalysisOption.READABILITY_ONLY)) {
			return new TextReadability(filePath);
		} else {
			return new TextStatistics(filePath);
		}
	}
}
