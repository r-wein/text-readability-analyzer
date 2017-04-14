package textAnalysis;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * This class calculates all of our readability values. The readability indexes
 * used are Flesch Reading Ease, Flesh-Kincaid Grade Level, Gunning Fog,
 * Coleman-Liau, SMOG, and Automated Readability. Average Readability is an
 * average of all results except for Flesch Reading Ease. This is because Flesch
 * Reading Ease is based on a different scale than the other tests. All the
 * other tests try to determine how many years of formal education it would take
 * for a person to completely understand the text on first reading.
 * 
 * @author RossWeinstein
 *
 */
public class TextReadability implements TextAnalysis {

	private Map<String, Double> textValues;
	private Map<String, Double> results;
	private String fileName;

	public TextReadability(Path filePath) {
		TextStatistics analysis = new TextStatistics(filePath);
		analysis.analyze();
		this.fileName = analysis.getTitle();
		this.textValues = analysis.getResults();
		this.results = new HashMap<String, Double>();
	}

	// TextAnalysis interface : get text's title
	public String getTitle() {
		return this.fileName;
	}

	// TextAnalysis interface : get all readability results
	public Map<String, Double> getResults() {
		return this.results;
	}

	/**
	 * All the results from the text analysis used to calculate each of the readability indexes
	 * @return Map the text statistics
	 */
	public Map<String, Double> getTextAnalysisResults() {
		return this.textValues;
	}

	// TextAnalysis interface : analyze the text for the desired results
	public void analyze() {
		this.calcFleschReadingEase();
		this.calcFleschKincaidGradeLevel();
		this.calcGunningFog();
		this.calcColemanLiau();
		this.calcSMOG();
		this.calcAutomatedReadability();
		this.calcAverageReadability();
	}

	/**
	 * Calculates and adds the Flesch Reading Ease to the results Map
	 */
	public void calcFleschReadingEase() {
		double readingEase = 206.835 - (1.015 * this.textValues.get("words per sentence"))
				- (84.6 * this.textValues.get("syllables per word"));
		this.results.put("flesch reading ease", readingEase);
	}

	/**
	 * Calculates and adds the Flesch-Kincaid Grade Level to the results Map
	 */
	public void calcFleschKincaidGradeLevel() {
		double gradeLevel = (0.39 * this.textValues.get("words per sentence"))
				+ (11.8 * this.textValues.get("syllables per word")) - 15.59;
		this.results.put("flesch-kincaid grade level", gradeLevel);
	}

	/**
	 * Calculates and adds Gunning Fog to the results Map
	 */
	public void calcGunningFog() {
		double gunningFog = 0.4 * (this.textValues.get("words per sentence")
				+ 100 * (this.textValues.get("complex words") / this.textValues.get("words")));
		this.results.put("gunning fog", gunningFog);
	}

	/**
	 * Calculates and adds Coleman-Liau to the results Map
	 */
	public void calcColemanLiau() {
		double colemanLiau = 0.0588 * (this.textValues.get("letters per word") * 100)
				- 0.296 * ((this.textValues.get("sentences") / this.textValues.get("words")) * 100) - 15.8;
		this.results.put("coleman-liau", colemanLiau);
	}

	/**
	 * Calculates and adds SMOG (Simple Measure of Gobbledygook) to the results Map
	 */
	public void calcSMOG() {
		double smog = 1.0430
				* Math.sqrt(this.textValues.get("complex words") * (30 / this.textValues.get("sentences")) + 3.1291);
		this.results.put("SMOG", smog);
	}

	public void calcAutomatedReadability() {
		double automatedReadability = 4.71 * this.textValues.get("letters per word")
				+ 0.5 * this.textValues.get("words per sentence") - 21.43;
		this.results.put("automated readability", automatedReadability);
	}

	/**
	 * Calculates and adds the average readability to the results Map
	 */
	public void calcAverageReadability() {
		double avgReadability = (this.results.get("flesch-kincaid grade level") + this.results.get("gunning fog")
				+ this.results.get("coleman-liau") + this.results.get("SMOG")
				+ this.results.get("automated readability")) / 5.0;
		this.results.put("average readability", avgReadability);
	}
}
