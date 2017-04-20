# READABILITY AND MYSQL
This program computes a .txt file's readability, a metric to determine how difficult a text is to understand, and stores the result in a MySQL database for comparison.  

## Sample Output
![Usage Gif](ReadabilityVideo.gif)

## Sample Code
```java
/**
 * Adds appropriate information into readability.textreadability table; this
 * table holds all the results from performing the readability tests
 * 
 * @param data
 *            TextAnalysis object with all needed information for the table
 * @param idNumber
 *            from textStatistics table for foreign key
 * @throws SQLException
 *             could not add information to readability.textReadability
 *             table
 */
private void addToReadability_TextReadability(TextAnalysis data, int idNumber) throws SQLException {
    this.prepStatement = this.dbConnect
        .prepareStatement("INSERT INTO READABILITY.TEXTREADABILITY VALUES (NULL,?,?,?,?,?,?,?,?)");
    this.prepStatement.setInt(1, idNumber);
    this.prepStatement.setFloat(2, data.getResults().get("flesch reading ease").floatValue());
    this.prepStatement.setFloat(3, data.getResults().get("flesch-kincaid grade level").floatValue());
    this.prepStatement.setFloat(4, data.getResults().get("gunning fog").floatValue());
    this.prepStatement.setFloat(5, data.getResults().get("coleman-liau").floatValue());
    this.prepStatement.setFloat(6, data.getResults().get("SMOG").floatValue());
    this.prepStatement.setFloat(7, data.getResults().get("automated readability").floatValue());
    this.prepStatement.setFloat(8, data.getResults().get("average readability").floatValue());
    this.prepStatement.executeUpdate();
}
```

## About This Project
### Origin
This project is an extension of one of my classroom assignments.  We were tasked to find each unique word in Lewis Carroll's *Alice's Adventures in Wonderland* and record their frequency.  While working on this assignment, which was originally in Python, I had the idea to expand on the basic requirements by adding readability tests and MySQL.  I decided to switch from Python to Java because I wanted to try JDBC.

### Text Readability
This program calculates six readability indexes:

  1. [Flesch Reading Ease](https://en.wikipedia.org/wiki/Flesch–Kincaid_readability_tests)
  1. [Flesch-Kincaid Grade Level](https://en.wikipedia.org/wiki/Flesch–Kincaid_readability_tests)
  1. [Gunning Fog](https://en.wikipedia.org/wiki/Gunning_fog_index)
  1. [Coleman-Liau](https://en.wikipedia.org/wiki/Coleman–Liau_index)
  1. [SMOG (Simple Measure of Gobbledygook)](https://en.wikipedia.org/wiki/SMOG)
  1. [Automated Readability](https://en.wikipedia.org/wiki/Automated_readability_index)
  
Each of these indexes, except for Flesch Reading Ease, produces a number which estimates the number of years of formal education a person would require to fully understand the text on first reading.  Flesh Reading Ease produces a score out of 100 where the lower the value, the more difficult the text.    

To calculate these formulas, several pieces of information were collected from the text:

  1. Total words
  1. Total sentences
  1. Total syllables
  1. Total letters
  1. Total complex words (words with three or more syllables)
  1. Average words per sentence
  1. Average syllables per words
  1. Average letters per word

### MySQL
The database for this program is comprised of three tables.  The SQL for each table can be viewed in the [MySQL](https://github.com/rossweinstein/text-readability-analyzer/tree/master/MySQL) folder.

### How To Use
I wanted to keep it simple.  The goal of this project was an introduction to JDBC so I decided to only retrieve an average of each text's readability values (excluding Flesch Reading Ease).  Through a basic command line interface, the program prompts the user to supply a file path for a directory containing .txt files.  Once a file path is given, the program will read in each .txt file within that directory, analyze, and then put the results into the database.  

Once the database is populated, the user has a few options:  
  1. They can view an ordered list of all the results 
  1. They can view an ordered list of the most difficult texts to read (user sets list size) 
  1. They can view an ordered list of the least difficult texts to read (user sets list size)
  1. They can view two ordered lists; one of the most difficult and one of the least difficult (user sets list size)
  1. They can delete an entry from the database
  1. They can delete the entire database
  
### Testing
I used JUnit to test all my code in this project.  Those tests can be seen [here](https://github.com/rossweinstein/text-readability-analyzer/tree/master/test).

## Outside Resources
I used the [optimized syllable dictionary](https://github.com/troywatson/Lawrence-Style-Checker/blob/master/dict/syllables-optimized-list.txt) from [Troy Watson](https://github.com/troywatson/Lawrence-Style-Checker) in my SyllableDetector class.

## Sample Texts
I used a combination of [The Open Library](https://openlibrary.org) and [The Internet Classics Archive](http://classics.mit.edu/Browse/index.html) for my text samples in the example above.

## License
[MIT License](https://en.wikipedia.org/wiki/MIT_License)
