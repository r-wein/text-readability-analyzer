package textAnalysis;

import java.util.Map;

/**
 * A simple interface to use with our different text analysis methods.
 * 
 * @author RossWeinsetein
 */

public interface TextAnalysis {

	// what information was discovered from the text
	Map<String, Double> getResults();

	// test the text
	void analyze();

	// what is the name of the text were are analyzing
	String getTitle();
}
