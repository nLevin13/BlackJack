package blackjack;

import cardgame.Card;

public class DealerPlayer extends Player
{

	public DealerPlayer() {
		super();
		super._name = "dealer";
	}

	public DealerPlayer(Card card1, Card card2) {
		super("dealer", card1, card2);
	}

	@Override
	public void hit(Card card) {
		if (_state == "canHit") {
			_hand.hit(card);
			if (_hand.getScore() > 21)
				_state = "bust";
			else if (_hand.getScore() >= 18) {
				_state = "stand"; // dealer must stand at 18
			}
		} else
			throw new RuntimeException("Can't hit. Object state: " + _state);
	}
}
