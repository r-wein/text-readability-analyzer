package textAnalysis;

/**
 * This enum is in charge of the three ways we an analyze a text
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
public enum AnalysisOption {
	STATS_ONLY, READABILITY_ONLY, ANALYZE_ALL;

}
