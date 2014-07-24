import sys
import json
import re

'''

Author: Peter Cheung
Last Modified: 7/23/14

Description: This script is responsible for cataloging and printing out the term frequency in all of the tweets
that are logged in the output file from the twitter_stream.py script.

'''

############################################################################################
# This function catalogs the terms in a tweet string and keeps count of its occurrences.
############################################################################################
def find_number_of_terms(terms, tweet_string):
	# Loop through all of the individual words in the tweet string
	for word in tweet_string:
		# Strip the word of any other non a-z characters
		word = re.sub(r'[\W]+', '', word)
		
		# Check if the word (in lowercase) is in the terms dictionary. If so, then increment its value (frequency)
		if word.lower() in terms:
			# Add to the total number of term occurrences.
			terms[word.lower()] += 1
		else:
			# Create a new key for this term
			terms[word.lower()] = 1
############################################################################################
############################################################################################

def main():
    #tweets_file = open(sys.argv[1])
    tweets_file = open("output.txt", "r")
    terms = {}

    tweets = {}
    count = 0

   	# Extract all of the tweets in the tweets file and store them as JSON.
    with tweets_file as file:
    	for line in file:
    	    tweets[count] = json.loads(line)
    	    count += 1

    # Loop through the tweets dictionary and calculate and 
    for iteration in range(0, len(tweets)):
		# Check if this tweet has any text. Not all tweets have text, apparently.
        if 'text' in tweets[iteration]:
			
			# Split all of the words in a tweet and store them in an array.
		    words_in_tweet = tweets[iteration]['text'].split()

			# Call the function defined above to calculate the sentiment score of the whole tweet and print it out.
		    find_number_of_terms(terms, words_in_tweet)

    for term in terms:
    	# Calculate and print the term frequency in percentages
    	print term + ':\t' + str(terms[term])

    tweets_file.close()
				
if __name__ == '__main__':
    main()


