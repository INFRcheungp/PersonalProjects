import sys
import oauth2 as oauth
import urllib2 as urllib

'''

Author: Peter Cheung
Last Modified: 7/23/14

Description: This script makes a REST call to the Twitter API to get a stream of tweets (in English)
on a user-provided keyword subject.
'''

dir(oauth)

api_key = "REMOVED FOR GITHUB"
api_secret = "REMOVED FOR GITHUB"
access_token_key = "REMOVED FOR GITHUB"
access_token_secret = "REMOVED FOR GITHUB"

_debug = 0

oauth_token    = oauth.Token(key=access_token_key, secret=access_token_secret)
oauth_consumer = oauth.Consumer(key=api_key, secret=api_secret)

signature_method_hmac_sha1 = oauth.SignatureMethod_HMAC_SHA1()

http_method = "GET"


http_handler  = urllib.HTTPHandler(debuglevel=_debug)
https_handler = urllib.HTTPSHandler(debuglevel=_debug)

############################################################################################
# This function constructs, signs, and opens a Twitter request
############################################################################################
def twitterreq(url, method, parameters):
  req = oauth.Request.from_consumer_and_token(oauth_consumer,
                                             token=oauth_token,
                                             http_method=http_method,
                                             http_url=url, 
                                             parameters=parameters)

  req.sign_request(signature_method_hmac_sha1, oauth_consumer, oauth_token)

  headers = req.to_header()

  if http_method == "POST":
    encoded_post_data = req.to_postdata()

  else:
    encoded_post_data = None
    url = req.to_url()

  opener = urllib.OpenerDirector()
  opener.add_handler(http_handler)
  opener.add_handler(https_handler)

  response = opener.open(url, encoded_post_data)

  return response
############################################################################################
############################################################################################

############################################################################################
# This function extracts all of the tweets from the Twiiter JSON response.
############################################################################################
def get_tweets(keyword):

  url = "https://api.twitter.com/1.1/search/tweets.json?q=" + keyword + "&lang=en"
  parameters = []
  tweet_results = twitterreq(url, "GET", parameters)

  output_file = open("tweets_output.txt", "w")

  # Print out the data from the REST call to a text file.
  for line in tweet_results:
    output_file.write(line.strip())

  output_file.close()
############################################################################################
############################################################################################

def main():
  keyword = raw_input("Enter a search keyword: ")
  get_tweets(keyword)

if __name__ == '__main__':
  main()
