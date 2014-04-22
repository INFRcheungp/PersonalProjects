package Blackjack;
/**
 * This class represents the player for the blackjack game.
 * @author petercheung
 */

import java.util.HashMap;
import java.util.Map;

import Blackjack.BlackjackPlayer;

public class Player implements BlackjackPlayer {
	//	Representing the number of chips with which the player starts.
	private int chips = 100;
	private int defaultScore = 0;
	private int changedAceScore = 0;
	private boolean hasAceInHand;
	private boolean hasBlackjack;
	private HashMap<String, Integer> hand;

	Player(){
		this.hand = new HashMap<String, Integer>();
		this.hasAceInHand = false;
		this.hasBlackjack = false;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////
	public HashMap<String, Integer> getHand() {
		return this.hand;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean getHasBlackjack() {
		return this.hasBlackjack;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////
	public int getDefaultScore() {
		return this.defaultScore;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////		
	public int getChangedAceScore() {
		return this.changedAceScore;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean getHasAceInHand() {
		return this.hasAceInHand;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////
	public int getChips() {
		return this.chips;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////
	public void setHasBlackjack(boolean condition) {
		this.hasBlackjack = condition;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////
	public void setDefaultScore(int total) {
		this.defaultScore = total;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////		
	public void setChangedAceScore(int total) {
		this.changedAceScore = total;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////	
	public void setHasAceInHand(boolean cond) {
		this.hasAceInHand = cond;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////
	public void setChips(int amount) {
		this.chips = amount;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////		
	public int bet() {
		this.chips--;
		return 1;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////
	public void win(int winnings){
		this.chips += winnings;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////
	public void showHand(){
		System.out.println("\t\tYour current hand: ");
		System.out.print("\t\t");
		for(Map.Entry<String, Integer> card : hand.entrySet()) {
			System.out.print(" [" + card.getKey() + "]");
		}
		System.out.println("\n");
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////
}