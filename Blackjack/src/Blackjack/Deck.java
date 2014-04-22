package Blackjack;
/**
 * Class that represents the deck of cards to be used in the Blackjack game.
 * @author petercheung
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

	public class Deck {
		
		//	The mapping for 'cardValueMap' is <rank, value> like <"king", 10> or <"2", 2>
		private HashMap<String, Integer> cardValueMap = new HashMap<String, Integer>();
		
		private final String[] suits = { "Club", "Diamond", "Heart", "Spade" };
		private final String[] ranks  = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };
		private final List<String> suitRanks = new ArrayList<String>(52);

		/////////////////////////////////////////////////////////////////////////////////////////////////
		//	The constructor prepares the deck of cards for the game.
		Deck() {
			//	Used to assign values to each card rank.
			Integer cardValue = new Integer(1);
			String suitRank;
			
			/**
			 * Assign a unique <"rank-suit", cardValue> mapping for each card and store it into the 'cardValueMap', which
			 * will be the main data structure to represent our deck of cards.
			 */
			for (String rank: ranks) {
				for (String suit: suits) {	
					//	Create the unique "rank-suit" key by concatenating the rank and suit strings.
					suitRank = rank.concat("-" + suit);
					//	Add the unique "rank-suit" key into a list to keep track of them.
					suitRanks.add(suitRank);
					//	Finally, assign an integer value to each "rank-suit" key, a.k.a card.
					cardValueMap.put(suitRank, cardValue);
				}
				if(cardValue != 10) { cardValue++; }
			}			
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////
		public HashMap<String, Integer> getCardValueMap(){
			return cardValueMap;
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////
		public List<String> getSuitRanks() {
			return suitRanks;
		}
	}