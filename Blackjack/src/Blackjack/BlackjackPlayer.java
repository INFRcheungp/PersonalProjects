package Blackjack;
/**
 * Interface that represents a blackjack player.
 * @author petercheung
 */

import java.util.HashMap;

public interface BlackjackPlayer {
	void setHasBlackjack(boolean cond);
	void setDefaultScore(int total);
	void setChangedAceScore(int total);
	void setHasAceInHand(boolean cond);
	int getDefaultScore();
	int getChangedAceScore();
	boolean getHasBlackjack();
	boolean	getHasAceInHand();
	HashMap<String, Integer> getHand();
}