/*	
	TODO: THE SELECTED OPTIONS COME IN AS CSV VALUES STRING. EACH WORD IN THE CSV STRING WILL HAVE TO BE 
	PARSED AND CHECKED UP AGAINST THE CURRENT SET OF ALREADY ASSIGNED SENTIMENTS TO THE TEXT. PERHAPS ASSIGN
	AN ID TO EACH OF THE SENTIMENT WORDS SO THAT STRING MATCHING WOULD BE AVOIDED. JUST DO ID INTEGER COMPARISONS.
*/

/*	
	COMPLETED (9/12/14): Able to now focus on current text object and allow users to tag it and add more into the sentiments object.
	Completed (9/12/14): identified the jQuery parts. These are explicitly marked to serve as points of 
	freedom in case we want to work without dependency on jQuery in the future and serve as an independent library. 
*/

////////////////////////////////////////////////////////////////////////////////////////////////////
function getSelectedText() {
	var text = "";

	if ( window.getSelection ) {
    	text = window.getSelection().toString();
	} else if ( document.selection && document.selection.type != "Control" ) {
    	text = document.selection.createRange().text;
	}

	return text;
}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
function parseSelection(csvString) {
	alert("CSV: " + typeof(csvString));	//DEBUGGER
	var stringList = csvString.split(",");
	alert("STRING LIST: " + stringList);	//DEBUGGER
}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
function printObject(object) {
	var alertText = ' ';
	/*Put the name of your own object after "in",
	  and you can change the text "property"
	  to be whatever you please.
	*/

	for (property in object) {

	 /*this will create one string with all the Javascript 
	  properties and values to avoid multiple alert boxes: */

	  alertText += property + ':' + object[property]+'; ';
	  alert(object[property] + " AND TYPE: " + typeof(object[property]));
	}

	alert(alertText);
}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
function isDuplicateSentiment(object, sentiments) {
	return String(object["sentiments"]).indexOf(sentiments);
}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
function aggegrateSentimentsIntoObjects (textObject, extractedText, selectedSentiments) {
	//alert("aggegrateSentimentsIntoObjects TEXT: " + extractedText);
	//alert("aggegrateSentimentsIntoObjects SENT: " + selectedSentiments);

	//	Very first sentiment assignment.
	if ( textObject["sentiments"] == null ) {
		alert("TEST ALERT: Has no sentiments associated yet. Will be assigned. ");	//DEBUGGER
		textObject["sentiments"] = selectedSentiments; 
	}
	//	Already has some sentiments. Make sure that it does not have the one just assigned to it.
	else  {
		//	Check for duplicate sentiment tags first. If none, then add it
		if ( isDuplicateSentiment(textObject, selectedSentiments) == -1 ) {
			alert("TEST ALERT: Does not have sentiment in list. Will be added. ");	//DEBUGGER
			//	Save the text and its associated CSV sentiment tags. Parse later before persisting.
			textObject["sentiments"] = textObject["sentiments"].concat(selectedSentiments);
		} 
		else { alert("TEST ALERT: Already contains this sentiment. Will not be added."); }	//DEBUGGER
   	}

}
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

//(function($, window, document, undefined) {

jQuery(document).ready(function () {

	/*
	var sentimentTags = [
		"Happy",
		"Sad",
		"Neutral",
	]
	*/
	var idCount = 0;
	var currentArrayIndex = 0;
	var textSentimentObjectArray = [];

	var sentiments = {	"sentimentAssociations": []	};

	var textSentinmentRelationObjectJSON = '';
	var extractedText = '';

	var selectOption = jQuery('select');	//	Targets all select elements
	jQuery(selectOption.prop('disabled', true));	

	//	The completion of this event indicates that the user(s) have selected a new segment of text to tag.
	jQuery('p').mouseup(function (e){
		extractedText = getSelectedText();

		//	Initiate sentiment tagging option only if there is selected text.
		if( extractedText ) {
			alert( extractedText );	//DEBUGGER
			//alert(sentimentTags[0]);
			selectOption.prop('disabled', false);

			var selectedSentiments = '';

			/*	
				Push a new object into the sentiments object array and return its length and minus 1 from it. 
				This keeps focus on the most recently selected text object by the user(s).	
			*/
			currentArrayIndex = sentiments["sentimentAssociations"].push( { "id" : idCount, "text" : extractedText, "sentiments" : "" }) - 1;

			//	Create a new object for the newly-selected segment of text.
			//var textSentinmentRelationObject = { "id": idCount };

			//	Increment the id counter for the next new text selection.
			idCount++;

			//	The completion of this event indicates that the user(s) have selected a new sentiment to associate with the curren text object in scope.
			jQuery(selectOption.change(function() {
				alert("INSIDE SELECTED CHANGE: " + extractedText);	//DEBUGGER

				//	Get the current text object that's being tagged.
				var currentTextObject = sentiments["sentimentAssociations"][currentArrayIndex];

				alert("INSIDE SELECTED CHANGE OBJECT: " + currentTextObject["id"]);	//DEBUGGER
        		
        		//	Get the selected sentiment(s).
        		selectedSentiments = jQuery(this).val();

    	    	//	Make sure that there is a sentiment chosen.
    	    	if( selectedSentiments ) {
    	    		//	Add the sentiments into the current text object in scope for the selected text.
					aggegrateSentimentsIntoObjects(currentTextObject, extractedText, selectedSentiments);
					
					//storeSentiments(textSentimentObjectArray, extractedText, selectedSentiments); //	Store the sentiments for the current text.

					printObject(currentTextObject);	//DEBUGGER
					//alert("TEST ALERT: " + console.log(textSentinmentRelationObject));	//DEBUGGER

					//textSentinmentRelationObjectJSON = JSON.stringify(textSentinmentRelationObject);

					textSentinmentRelationObject = JSON.stringify(sentiments);

					alert("TEST ALERT: JSON: " + textSentinmentRelationObject);	//DEBUGGER
					//alert("TEXT: " + extractedText);
					//alert("ITS SENTIMENT: " + textSentinmentRelationObject[extractedText]);
				}
    		}));
		}


	});


});

////////////////////////////////////////////////////////////////////////////////////////////////////
