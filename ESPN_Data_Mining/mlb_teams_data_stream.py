import requests
import json

espn_api_key = ''
espn_api_shared_secret = ''

espn_get_mlb_teams = 'http://api.espn.com/v1/sports/baseball/mlb/teams'

try:
	response = requests.get(espn_get_mlb_teams + '?apikey=' + espn_api_key)
	print response.status_code
	print json.dumps(response.text, sort_keys=True, indent=4, separators=(',', ': '))
	
except URLError, e:
	print e


