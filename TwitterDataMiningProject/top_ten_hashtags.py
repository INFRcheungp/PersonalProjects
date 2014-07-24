import sys
import json
import re

'''

Author: Peter Cheung
Last Modified: 7/23/14

Description: This script is responsible for cataloging and printing out the top ten hashags from the output.txt file

'''
############################################################################################
#   This function catalogs the number of hashtags in the entire file
############################################################################################
def find_number_of_hashtags(hashtags, hashtags_in_tweet):
	# Loop through all of the individual hashtags in the tweet string
	for ht in hashtags_in_tweet:

		# Check if the hashtag is in the hashtags dictionary. If so, then increment its value (frequency)
		if hashtags_in_tweet[ht] in hashtags:
			# Add to the total number of hashtag occurrences.
			hashtags[hashtags_in_tweet[ht]] += 1
		else:
			# Create a new key for this hashtag
			hashtags[hashtags_in_tweet[ht]] = 1
############################################################################################
############################################################################################

############################################################################################
#   This function finds the max occurrences of hastags
############################################################################################
def find_max(hashtags, top_ten_dict):
	max_hashtag = ''
	max = 0

	for term in hashtags:
		if(hashtags[term] > max and term not in top_ten_dict and not term == ''):
			max = hashtags[term]
			max_hashtag = term

	# Put the hashtag in the dictionary so that'll keep track of the top ten tracks
	top_ten_dict[max_hashtag] = max
	print max_hashtag + ': ' + str(top_ten_dict[max_hashtag])

############################################################################################
############################################################################################

def main():
    #tweets_file = open(sys.argv[1])
    tweets_file = open("output.txt", "r")
    top_ten_dict = {}
    hashtags_in_tweet = {}
    hashtags = {}

    tweets = {}
    count = 0
   	# Extract all of the tweets in the tweets file and store them as JSON.
    with tweets_file as file:
    	for line in file:
    	    tweets[count] = json.loads(line)
    	    count += 1

    count = 0
    # Loop through the tweets dictionary and calculate and 
    for iteration in range(0, len(tweets)):
    	if 'entities' in tweets[iteration]:
    		if 'hashtags' in tweets[iteration]['entities']:

    			for current_hashtag in range(0, len(tweets[iteration]['entities']['hashtags'])):
    				hashtags_in_tweet[count] = tweets[iteration]['entities']['hashtags'][current_hashtag]['text']
    				count += 1
    				#print tweets[iteration]['entities']['hashtags'][current_hashtag]['text']

   	# Tally up the number of hashtags.
    find_number_of_hashtags(hashtags, hashtags_in_tweet)


    # Find the top ten hashtags and print them out and the number of their occurrences
    for iteration in range(0, 10):
        find_max(hashtags, top_ten_dict)

    tweets_file.close()

				
if __name__ == '__main__':
    main()


