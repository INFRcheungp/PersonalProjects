/**
 * Insight Data Engineering Fellowship Coding Challenge
 * @author petercheung
 * email: pcheung1991@gmail.com
 * date: 04/21/14
 */
package Blackjack;

import java.util.Random;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Blackjack {
	
	/**
	 * The possible combinations (with replacement) of the ace value arrangements are shown below.
	 * 
	 * 					If there are 4 aces in a hand, then there are 5 possible values:
	 * 						[1,1,1,1] = 4, [1,1,1,11] = 14, [1,1,11,11] = 24, [1,11,11,11] = 34, [11,11,11,11] = 44
	 * 
	 * 					If there are 3 aces in a hand, then there are 4 possible values:
	 * 						[1,1,1] = 3, [1,1,11] = 13, [1,11,11] = 23, [11,11,11] = 33
	 * 
	 * 					If there are 2 aces in a hand, then there are 3 possible values:
	 * 						[1,1] = 2, [1,11] = 12, [11,11] = 22
	 * 
	 * 					If there is only 1 ace in a hand, then there are 2 possible values:
	 * 						[1] = 1, [11] = 11 
	 * 
	 * If we eliminate the sums that total to more than 21, then we are left with only eight 
	 * combinations that are of interest to this Blackjack game. Here are the eight arrangements:
	 * 
	 * 					4 Aces in a hand:
	 * 						Sum of [1,1,1,11] = 14
	 * 						Sum of [1,1,1,1] = 4
	 * 					3 Aces in a hand:
	 * 						Sum of [1,1,11] = 13
	 * 						Sum of [1,1,1] = 3
	 * 					2 Aces in a hand:
	 * 						Sum of [1,11] = 12
	 * 						Sum of [1,1] = 2
	 * 					1 Ace in a hand:
	 *						Sum of [11] = 11 
	 * 						Sum of [1] = 1
	 * In this implementation of blackjack all the ace cards in the game deck are initialized to an 
	 * integer value of 1, so we wouldn't even need to store the totals of the 1-value aces since they are
	 * calculated anyways. What we really need are the ace totals for when there is one ace changed to an 
	 * 11-value. That is, we only need these values in our blackjack game:
	 * 
	 * 					4 Aces in a hand (with one changed to an 11-value):
	 * 						Sum of [1,1,1,11] = 14
	 * 					3 Aces in a hand (with one changed to an 11-value):
	 * 						Sum of [1,1,11] = 13
	 * 					2 Aces in a hand (with one changed to an 11-value):
	 * 						Sum of [1,11] = 12
	 * 					1 Ace in a hand (changed to an 11-value):
	 *						Sum of [11] = 11 
	 */
	public static final int twentyOne = 21;
	public static int betTotal = 0;
	
	public static boolean playerStands = false;
    public static boolean inputChecker = false;
    public static boolean isFirstDealHand = false;
    //	Controls whether or not the player decides to keep playing blackjack or wants to quit.
    public static boolean continueGame = true;
    //	Controls whether or not the player decides to keep going in this round or not.
    public static boolean continueRound = true;
    
	public static Integer cardValue;
	public static Integer randomKey;
	
	public static String playerDecision;
	public static String rankValue;
	public static String suitValue;
	
	public static Random randomDrawer = new Random();
	
	public static Deck gameDeck;
	//	Used to bookkeep the collection of cards already picked from the original deck in a certain round
	public static HashMap<String, Integer> pickedCardValues = new HashMap<String, Integer>(); 
	//	Used to bookkeep the collection of suit-rank keys already selected in the game so far.
	public static List<String> pickedSuitRanks = new ArrayList<String>();
	//	Used to read player's playerDecisions throughout the game.
	
	public static Scanner actionInput = new Scanner(System.in);
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {

		System.out.println("Welcome to Insight's Blackjack Game!");
		
		//	Create a new deck of cards for this game.
		gameDeck = new Deck();
		//	Create a new Dealer for this game.
		Dealer dealer = new Dealer();
		//	Create a new Player for this game. 
		Player player = new Player();
		
		//	Begin the game loop here. Reuse the same player, dealer, and deck if the player decides to keep playing.
		while(continueGame && player.getChips() > 0) {
			//	The player makes his/her initial bet in the constructor.
			betTotal += player.bet();

			System.out.println("\n");		
			System.out.println("Player's first hand deal.");		
			
			//	The dealer deals the player his/her first hand.
			dealer.deal(player);
			/**
			 * 	Check if the player's hand has an ace. If so, then calculate another total with the ace in the hand 
			 *  changed to an 11-value. The method 'reviewAceInHand' also checks for the occurrence of blackjack.
			 */
			checkHandForAces(player);

			System.out.println("\t\tYour current score for this round: " + player.getDefaultScore());		
			System.out.println("\n");		
			System.out.println("Dealer's first hand deal.");	
			
			//	The dealer deals his/her first hand.
			dealer.deal(dealer);
			/**
			 * 	Check if the dealer's hand has an ace. If so, then calculate another total with the ace in the hand 
			 *  changed to an 11-value. The method 'reviewAceInHand' also checks for the occurrence of blackjack.
			 */		
			checkHandForAces(dealer);
	
			//	The dealer keeps picking cards until his/her score is 17 <= x <= 21
			while(dealer.getDefaultScore() < 17) {
				System.out.println("The dealer keeps picking cards");
				pickCard(dealer);
				checkHandForAces(dealer);
			}
			
			System.out.println("\t\tDealer's current score for this round: " + dealer.getDefaultScore());
			System.out.println("\n");		
			System.out.println("Now that the hands have been dealt, you can now decide what to do next.");
			
			//	Check hands scores first before going into playerDecision loop for the player.
			reviewHands(dealer, player);
			
			//	Begin this round's loop
			while(continueRound) {
				/*	Keep asking the player for playerDecision to 'hit' or 'stand' if no game-stopping events
				 * 	like bust, tie, win, or lose occur or if the player decides to keep hitting.
				 */				
				keepRoundGoing(dealer, player);
			}		
			displayStats(dealer, player);
			
			System.out.println("\t\tThis round has ended!");
			
			//	Get the player's decision on whether or not the continue the game.
			System.out.println("Would you like continue the game?");
			System.out.print("Enter 'y' to continue the game, or 'n' to stop the game: ");
	        
			playerDecision = actionInput.nextLine();
	        
			while(!playerDecision.matches("[yY]|[nN]")) {
				System.out.println("\n");		
				System.out.println("Whoops! Looks like you've entered something weird. Try again!");
				System.out.println("Press 'y' to continue the game ");
				System.out.println("Press 'n' to stop the game ");
	            System.out.print("What would you like to do next?");
	            playerDecision = actionInput.nextLine();
			}
			
	        //	This block here checks if the input matches the hit or stand regular expressions.  
	        if(playerDecision.matches("[yY]")) {
				System.out.println("\n");		
				System.out.println("tNice, let's continue this game! :)");
				keepGameGoing(dealer, player);
	        } else {
				System.out.println("\n");		
				System.out.println("Hope you had fun playing blackjack! :)");   
				continueGame = false;
    			actionInput.close();
			}
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * This method shows the scores and the chips of the player.
	 * @param dealer
	 * @param player
	 */
	public static void displayStats(Dealer dealer, Player player) {
		System.out.println("\n");		
		System.out.println("\t\tFinal scores for this round:");
		System.out.println("\t\tPlayer: " + player.getDefaultScore());
		System.out.println("\t\tDealer: " + dealer.getDefaultScore());
		System.out.println("\t\tYour chips: " + player.getChips() + " chips.");
		player.showHand();

		System.out.println("\n");
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * This method gives the winnings to the player if he/she wins the current round.
	 * @param player
	 */
	public static int payout() {
		return betTotal*2;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	public static void playerWon(Player player) {
		player.win(payout());
		betTotal = 0;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	public static void dealerWon(Dealer dealer) {
	//	dealer.setWinnings(dealer.getWinnings() + betTotal);
		betTotal = 0;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	public static void tieSituation(Player player) {
		player.setChips(player.getChips() + betTotal);
		betTotal = 0;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Let a blackjack player's hand be represented as {n, x}
	 * 
	 * where 'n' is the total of all other numbers in that hand that are not aces.
	 * where 'x' is the total number of aces in the hand.
	 *   
	 * the default hand score total is always equal to 'n + x' because the default integer value for aces is 1.
	 *    
	 * the changed ace hand score total (i.e., the total with one ace in the hand changed to an 11-value) is 
	 * always equal to 'n + (x-1) + 11' because we're changing one of the x aces that are in the current hand
	 * to an 11-value.
	 * 
	 * @param defaultTotalScore
	 * @return int
	 */
	public static int changeAceValueScore(int defaultTotalScore) {
		return defaultTotalScore-1+11;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * This method picks a card from the deck and adds it onto the calling player's hand.
	 * @param hand
	 */
	public static void pickCard(BlackjackPlayer player) {
		String valueKey;

		//	Randomly pick a suit-rank index based on the current size of the gameDeck's suitRanks list at this point in the game
		randomKey = randomDrawer.nextInt(gameDeck.getSuitRanks().size());
		//	Get and store the suit-rank String value in a temporary string.
		valueKey = gameDeck.getSuitRanks().get(randomKey);
		/*	Document the selected suit-rank String value by adding it to our global list in order to keep track of suit-rank 
		 * 	values already picked in order to avoid selecting the same suit-rank value for a certain round of the game. 
		 */
		pickedSuitRanks.add(valueKey);
		//	Remove the selected suit-rank String element from the gameDeck's suitRanks list in order to avoid duplicate selections.
		gameDeck.getSuitRanks().remove(valueKey);
		//	Get the Integer value of the randomly picked card and assign it to be returned at the end of this method. 
		cardValue = gameDeck.getCardValueMap().get(valueKey);
		//	We only check for value 1 to identify an Ace because that's its default value.
		if(cardValue == 1) {
			player.setHasAceInHand(true);
		}
		//	Remove the selected card element from the gameDeck's cardValueMap list in order to avoid duplicate selections.
		gameDeck.getCardValueMap().remove(valueKey);

		System.out.println("Picked card: " + valueKey + ": " + cardValue);
				
		//	Document the card picked from the original deck by putting it in a temporary List of picked cards.		
		pickedCardValues.put(valueKey, cardValue);
		//	Put the picked card into the calling player's hand.
		player.getHand().put(valueKey, cardValue);
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * This method checks for a bust situation in either player's hand. If neither player has a busted
	 * hand then just return false, indicating no busted hands.
	 * @param dealer
	 * @param player
	 * @return boolean
	 */
	public static boolean checkForBust(Dealer dealer, Player player) {
		int playerScore = player.getDefaultScore();
		int dealerScore = dealer.getDefaultScore();
		
		//	Both busted.
		if(playerScore > twentyOne && dealerScore > twentyOne) {
			System.out.println("Both have busted hands, so tie!");
			tieSituation(player);
			return true;
		}
		//	Dealer busted.
		else if(dealerScore > twentyOne) {
			System.out.println("Dealer has a busted hand, so player wins!");
			playerWon(player);
			return true;
		}
		//	Player busted.
		else if(playerScore> twentyOne) {
			System.out.println("Player has a busted hand, so dealer wins!");
			dealerWon(dealer);
			return true;
		}
		//	Neither player busted.
		else {
			System.out.println("Neither dealer nor player has a busted hand.");
			return false;
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * This method checks for the occurrence of a blackjack in either player's hands. The current 
	 * round of the game will end after returning from this function because either someone is winning 
	 * this hand or both are tying. That's why all the conditionals contain 'continueRound=false;'
	 * @param dealer
	 * @param player
	 */
	private static void checkForBlackjack(Dealer dealer, Player player) {
		boolean playerHasBlackjack = player.getHasBlackjack();
		boolean dealerHasBlackjack = dealer.getHasBlackjack();
		
		//	End round if both have blackjack. Tie situation.
		if(playerHasBlackjack && dealerHasBlackjack) {
			System.out.println("Both have 21, but both dealer and player have blackjack so tie!");
			tieSituation(player);
			continueRound=false;
		}
		//	If player has blackjack, then declare player the winner.
		else if(playerHasBlackjack) {
			System.out.println("Both have 21, but dealer doesn't have blackjack so player wins!");
			playerWon(player);
			continueRound=false;
		}
		//	This means that the player doesn't have blackjack, so the dealer will win.
		else if(dealerHasBlackjack){
			System.out.println("Both have 21, but player doesn't have blackjack so dealer wins!");
			dealerWon(dealer);
			continueRound=false;
		}
		//	Both don't have blackjack, but they both have 21, so they will tie.
		else {
			System.out.println("Both have 21, but neither one has blackjack so tie!");
			tieSituation(player);
			continueRound=false;
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * This method checks for the presence of conditions that could end a round, like a winning, losing, 
	 * or tying hands. It also checks for busts, which can result in a tie if both players bust. 
	 * @param dealer
	 * @param player
	 */
	public static void reviewHands(Dealer dealer, Player player) {
		int playerScore = player.getDefaultScore();
		int dealerScore = dealer.getDefaultScore();
		
		//	Check for either hand busting, first of all.
		if(checkForBust(dealer, player)) {
			/*	Go no further. Declare winner or tie. Round is over, so 'continueRound=false;'
			 * 	BUT game could still go on, so make no changes to 'continueGame'. 
			 */
			continueRound=false;
		} 
		//	Handles the event in which both dealer and player has 21. The current game round will stop here regardless. 
		else if(playerScore == twentyOne && dealerScore == twentyOne) {
			System.out.println("Both have 21! Now checking for blackjack.");
			checkForBlackjack(dealer, player);
		}
		//	Player wins this round
		else if(playerScore > dealerScore) {
			System.out.println("Player has the winning hand! Player wins!");
			playerWon(player);
			continueRound=false;
		}
		/*	Dealer currently has the high score, but the player can still decide to 'h' or 's',
		 *  so 'continueRound' won't be set to false, and this round can continue
		 */		
		else if(dealerScore > playerScore) {
		
			if(playerStands) {
				//Declare player the loser
				System.out.println("Player chose to stand, but the dealer has the higher score, so player loses.");
				dealerWon(dealer);
				continueRound = false;
			}
			else {
				System.out.println("The dealer currently has the higher score. You can choose to 'hit' or 'stand'");
			}		
		}
		//	This means 'dealerScore' == 'playerScore'. If the player stands, then it will result in a tie.	
		else {
			if(playerStands) {
				System.out.println("Both dealer and player have the same score. Player stands. Tie situation.");
				tieSituation(player);
				continueRound = false;
			}
			else {
				System.out.println("You both have the same score. You can choose to 'hit' or 'stand'");
			}
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * This method resets the players' scores and boolean variables to prepare for the next round of 
	 * the game. The winnings remain untouched.
	 * @param dealer
	 * @param player
	 */
	public static void resetPlayers(BlackjackPlayer dealer, BlackjackPlayer player) {
		dealer.setHasAceInHand(false);
		dealer.setHasBlackjack(false);
		dealer.setDefaultScore(0);
		dealer.setChangedAceScore(0);
		dealer.getHand().clear();
		
		player.setHasAceInHand(false);
		player.setHasBlackjack(false);
		player.setDefaultScore(0);
		player.setChangedAceScore(0);
		player.getHand().clear();
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * This method resets the deck for the next round of the game.
	 * @param deck
	 */
	public static void resetDeck(Deck deck) {
		deck.getCardValueMap().putAll(pickedCardValues);
		boolean test = deck.getSuitRanks().addAll(pickedSuitRanks);
		
		pickedCardValues.clear();
		pickedSuitRanks.clear();		
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * This method keeps the game going once the round has ended. It will reset the deck and the boolean 
	 * variables.
	 */
	public static void keepGameGoing(BlackjackPlayer dealer, Player player) {
		resetPlayers(dealer, player);
	    inputChecker = false;
	    isFirstDealHand = false;
	    playerStands = false;
	    continueRound = true;
	    resetDeck(gameDeck);
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * This method gets the player's playerDecision on whether to hit or stand.
	 * @param dealer
	 * @param player
	 */
	public static void keepRoundGoing(Dealer dealer, Player player) {
		//	Continue round loop
        while (!inputChecker) {
			System.out.println("-------------------------------------------------------------");
			System.out.println("\t\tPlayer's current score: " + player.getDefaultScore());
			System.out.println("\t\tDealer's current score: " + dealer.getDefaultScore());
			System.out.println("\t\tPress 'h' to hit ");
			System.out.println("\t\tPress 's' to stand ");
			player.showHand();
            System.out.print("\t\tWhat would you like to do next?");

            playerDecision = actionInput.nextLine();
                
                /*  
                 * Keep looping here if the input does not match the hit or stand regular 
                 * expressions I've defined here. The accepted inputs are either 'h' or 'H' 
                 * or 's' or 'S'.
                 */
    		while(!playerDecision.matches("[hH]|[sS]")) {
    			System.out.println("-------------------------------------------------------------");
    			System.out.println("Whoops! Looks like you've entered something weird. Try again!");
    			System.out.println("\t\tPress 'h' to hit ");
    			System.out.println("\t\tPress 's' to stand ");
    			player.showHand();
    	        System.out.print("\t\tWhat would you like to do next?");
    	        playerDecision = actionInput.nextLine();
    		}
    			
    		//	This block here checks if the input matches the hit or stand regular expressions.  
    		if(playerDecision.matches("[hH]")) {
    			System.out.println("-------------------------------------------------------------");
	    		System.out.println("You chose to hit!");
	    		pickCard(player);
	    		
	    		//	Check for the hand for aces AND sum up the total of values in the hand and set the player's 'defaultScore'. 
	    		checkHandForAces(player);
	    			
	    		reviewHands(dealer, player);
	    			
	    		/*	Continue asking the player for decision if there is still a chance for the player to win,
	    		 *  if he/she hasn't already won the game in 'reviewHands'.
	    		 */
	    		if (continueRound) {
	    			inputChecker = false;
	    		}
    			// Fall out of 'keepRoundGoing' loop and go back to main.
	    		else {
	    			inputChecker = true;
	    		}
            } 
    		else {
    			System.out.println("-------------------------------------------------------------");
	    		System.out.println("You chose to stand!");
	    		
	    		playerStands = true;
	    		reviewHands(dealer, player);
            	
	    		//	Change inputChecker into true to fall out of 'keepRoundGoing' loop.
                inputChecker = true;
    		}
        }	
        //	End of round loop
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * This method calculates and returns the total score of the calling player's hand.
	 * @param hand
	 * @return int
	 */
	public static int calculateTotalScore(HashMap<String, Integer> hand) {
		Integer total = 0;
		//	Iterate over the HashMap 'hand' elements and add up its values.
		for(Map.Entry<String, Integer> cardValue : hand.entrySet()) {
			total += cardValue.getValue();
		}
		return total;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * This method checks to see if the hand has an ace in it.
	 * @param hand
	 */
	public static void checkHandForAces(BlackjackPlayer player) {
		if(player.getHasAceInHand()) {
			reviewAceInHand(player);
		} else {
			player.setDefaultScore(calculateTotalScore(player.getHand()));
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * We only get this method if and only if the player has an ace in his/her current hand.
	 * @param hand
	 * @return Integer
	 */
	public static void reviewAceInHand(BlackjackPlayer player) {
		/*	Calculated like this, the total will never be 21 because the default ace value is 1 
		 * 	and the first hand won't total to 21, so this would at most be equal to 12 by default.
		 */
		
		System.out.println("Reviewing the aces in the hand.");
		
		int defaultScoreTotal = calculateTotalScore(player.getHand());
		int changedAceScoreTotal = changeAceValueScore(defaultScoreTotal);
		
		/*	
		 *  First and foremost, check if this is the first deal of the game by the count. If the hand.size() == 2,
		 *  then check if there is a possible blackjack by summing the hand total and setting it in the 'handScoreTotal'.
		 *  If this value is equal to 21, then don't do anything else and just return the value; else, provide the user
		 *  with the list of options.
		 * */
		if(player.getHand().size() == 2) {
			
			//	Check for blackjack first
			if(changedAceScoreTotal == twentyOne) {
				
				//	Indicate that this BlackjackPlayer has blackjack.
				System.out.println("Has blackjack!");
				player.setHasBlackjack(true);
				player.setDefaultScore(changedAceScoreTotal);
			} else {
				
				//	If not a blackjack, then set both the 'defaultScore' and 'changedAceScore' to be compared later.
				player.setDefaultScore(defaultScoreTotal);
				player.setChangedAceScore(changedAceScoreTotal);
				
				//	Determine which _scoreTotal is best for the player and set it to be that player's 'defaultScore'.
				chooseBestHand(player);
			}
		}
		else {
			
			//	Not the first deal of the game, so set both scores to be compared later.
			player.setDefaultScore(defaultScoreTotal);
			player.setChangedAceScore(changedAceScoreTotal);
			
			//	Determine which _scoreTotal is best for the player and set it to be that player's 'defaultScore'.
			chooseBestHand(player);
		}
	}	
/////////////////////////////////////////////////////////////////////////////////////////////////
	public static void chooseBestHand(BlackjackPlayer player) {
		int playerDefaultScore = player.getDefaultScore();
		int playerChangedAceScore = player.getChangedAceScore();

		System.out.println("The score with the ace value as 1: " + playerDefaultScore);
		System.out.println("The score with the ace value as 11: " + playerChangedAceScore);
		
		/*	If the 'playerDefaultScore' is not greater than the 'playerChangedAceScore' and
		 *  less than or equal to 21, then set it as the default score for this BlackjackPlayer
		 */
		if(playerDefaultScore > playerChangedAceScore && playerDefaultScore <= twentyOne) {
				player.setDefaultScore(playerDefaultScore);
				System.out.println("Chose the score with ace value as 1: " + playerDefaultScore);
		} 
		//	This means that the changedAceScore is closer to 21 than the defaultScore.
		else if(playerChangedAceScore > playerDefaultScore && playerChangedAceScore <= twentyOne) {
			player.setDefaultScore(playerChangedAceScore);
			System.out.println("Chose the score with ace avlue as 11: " + playerChangedAceScore);
		}
		//	Else at this point, just default.
		else {
			player.setDefaultScore(playerDefaultScore);
			System.out.println("This is the chosen score: " + playerDefaultScore);
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
}
