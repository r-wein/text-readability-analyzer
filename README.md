# READABILITY
This program computes a .txt files readability, a metric to determine how difficult a text is to understand, and stores the results in a MySQL database for comparison.  

## Example Gif
One Moment Please.

## About This Project
### Inception
This project is an extension of one of my classroom assignments (I'm a graduate student at the University of St. Thomas).  We were tasked to find each unique word in Lewis Carroll's Alice in Wonderland and record their frequency.  While working on this assignment, which was originally in Python, I had the idea to expand on the basic requirements by adding readability tests and MySQL.  I decided to switch from Python to Java because I wanted to try out JDBC.


### Text Readability
This program calculates six readability indexes:

  1. Flesch Reading Ease
  1. Flesch-Kincaid Grade Level
  1. Gunning Fog
  1. Coleman-Laiu
  1. SMOG (Simple Measure of Gobbledygook)
  1. Automated Readability
  
Each of these indexes, except for Flesch Reading Ease, produces a number which estimates the number of years of formal education a person would require to fully understand the text on first reading.  Flesh Reading Ease produces a score out of 100 where the lower the values, the more difficult the text is considered.    

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
The database for this program is comprised of three tables.  The SQL for each table can be viewed in the MySQL folder.

### Functionality
I wanted to keep it simple.  The goal of this project was an introduction to JDBC so I decided to only retrieve an average of each text's readability values (excluding Flesch Reading Ease).  Through a basic command line interface, the program prompts the user to supply a file path for a directory containing .txt files.  From there, the program will read in each file, analyze, and put the data into the database.  

Once the database is populated, the user has a few options:  
  1. They can view an ordered list of all the results, 
  1. They can view an ordered list of the most difficult texts to read (user sets list size) 
  1. They can view an ordered list of the least difficut texts to read (user sets list size)
  1. They can view two ordered lists; one of the most difficult and one of the least difficult (user sets list size)
  1. They can delete an entry from the database
  1. They can delete the entire database


## Outside Resources
I used the optimized syllable dictionary from Troy Watson's GitHub page in my SyllableDetector class.

Lawrence Style Checker Repository:
https://github.com/troywatson/Lawrence-Style-Checker

Optimized Syllable Dictionary:
https://github.com/troywatson/Lawrence-Style-Checker/blob/master/dict/syllables-optimized-list.txt
 
