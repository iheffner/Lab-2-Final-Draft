package pokerBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import pokerEnums.eCardNo;
import pokerEnums.eHandStrength;
import pokerEnums.eRank;

public class Hand {
	private UUID playerID;
	@XmlElement
	private ArrayList<Card> CardsInHand;
	private ArrayList<Card> BestCardsInHand;

	@XmlElement
	private int HandStrength;
	@XmlElement
	private int HiHand;
	@XmlElement
	private int LoHand;
	@XmlElement
	private int Kicker;

	private boolean bScored = false;

	private boolean Flush;
	private boolean Straight;
	private boolean Ace;
	private static Deck dJoker = new Deck();

	public Hand() {

	}

	public void AddCardToHand(Card c) {
		if (this.CardsInHand == null) {
			CardsInHand = new ArrayList<Card>();
		}
		this.CardsInHand.add(c);
	}

	public Card GetCardFromHand(int location) {
		return CardsInHand.get(location);
	}

	public Hand(Deck d) {
		ArrayList<Card> Import = new ArrayList<Card>();
		for (int x = 0; x < 5; x++) {
			Import.add(d.drawFromDeck());
		}
		CardsInHand = Import;

	}

	public Hand(ArrayList<Card> setCards) {
		this.CardsInHand = setCards;
	}

	public ArrayList<Card> getCards() {
		return CardsInHand;
	}

	public ArrayList<Card> getBestHand() {
		return BestCardsInHand;
	}

	public void setPlayerID(UUID playerID) {
		this.playerID = playerID;
	}

	public UUID getPlayerID() {
		return playerID;
	}

	public void setBestHand(ArrayList<Card> BestHand) {
		this.BestCardsInHand = BestHand;
	}

	public int getHandStrength() {
		return HandStrength;
	}

	public int getKicker() {
		return Kicker;
	}

	public int getHighPairStrength() {
		return HiHand;
	}

	public int getLowPairStrength() {
		return LoHand;
	}

	public boolean getAce() {
		return Ace;
	}

	public static Hand EvalHand(ArrayList<Card> SeededHand) {

		Deck d = new Deck();
		Hand h = new Hand(d);
		h.CardsInHand = SeededHand;

		return h;
	}

	public void EvalHand() {
		// Evaluates if the hand is a flush and/or straight then figures out
		// the hand's strength attributes

		// Sort the cards!
		Collections.sort(CardsInHand, Card.CardRank);

		// Ace Evaluation
		if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.ACE) {
			Ace = true;
		}

		// Flush Evaluation
		if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand.get(eCardNo.SecondCard.getCardNo())
				.getSuit()
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand
						.get(eCardNo.ThirdCard.getCardNo()).getSuit()
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getSuit()
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getSuit()) {
			Flush = true;
		} else {
			Flush = false;
		}

		// five of a Kind

		if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand.get(eCardNo.FifthCard.getCardNo())
				.getRank()) {
			ScoreHand(eHandStrength.FiveOfAKind, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0,
					0);
		}

		// Straight Evaluation
		else if (Ace) {
			// Looks for Ace, King, Queen, Jack, 10
			if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == eRank.KING
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.QUEEN
					&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == eRank.JACK
					&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TEN) {
				Straight = true;
				// Looks for Ace, 2, 3, 4, 5
			} else if (CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TWO
					&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == eRank.THREE
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.FOUR
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == eRank.FIVE) {
				Straight = true;
			} else {
				Straight = false;
			}
			// Looks for straight without Ace
		} else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
				.getRank() == CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank().getRank() + 1
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
						.getRank() == CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank().getRank() + 2
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
						.getRank() == CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank().getRank() + 3
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
						.getRank() == CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank().getRank() + 4) {
			Straight = true;
		} else {
			Straight = false;
		}

		// Evaluate Royal Flush
		if (Straight == true && Flush == true && CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TEN
				&& Ace) {
			ScoreHand(eHandStrength.RoyalFlush, 0, 0, 0);
		}

		// Straight Flush
		else if (Straight == true && Flush == true) {
			ScoreHand(eHandStrength.StraightFlush, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(),
					0, 0);
		}

		// Four of a Kind
		else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.FourthCard.getCardNo()).getRank()
				|| CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			// Find the kicker card
			int thiskicker;
			if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
					.get(eCardNo.FourthCard.getCardNo()).getRank()) {
				thiskicker = CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank().getRank();
			} else {
				thiskicker = CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank();
			}
			ScoreHand(eHandStrength.FourOfAKind, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0,
					thiskicker);
		}

		// Full House
		else if ((CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.ThirdCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank())
				|| (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.SecondCard.getCardNo()).getRank()
						&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand
								.get(eCardNo.FifthCard.getCardNo()).getRank())) {

			// Figure out which position three of a kind and pair are in, set
			// high and low card value
			int highnum;
			int lownum;

			if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
					.get(eCardNo.ThirdCard.getCardNo()).getRank()) {
				highnum = CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank();
				lownum = CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank().getRank();
			} else {
				highnum = CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank().getRank();
				lownum = CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank();
			}

			ScoreHand(eHandStrength.FullHouse, highnum, lownum, 0);
		}

		// Flush
		else if (Flush == true && Straight == false) {
			ScoreHand(eHandStrength.Flush, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0, 0);
		}

		// Straight
		else if (Straight == true && Flush == false) {
			ScoreHand(eHandStrength.Straight, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0, 0);
		}

		// Three of a Kind
		else if ((CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.ThirdCard.getCardNo()).getRank()
				|| CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank())
				|| CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()) {

			// Logic: the card in the third position will always be in the three
			// of a kind, so ThirdCard = HighCard
			int highnum = CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank().getRank();

			// Figure out the position of the Three of a Kind, set kicker
			int thiskicker;
			if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
					.get(eCardNo.ThirdCard.getCardNo()).getRank()) {
				thiskicker = CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank().getRank();
			} else
				thiskicker = CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank();

			ScoreHand(eHandStrength.ThreeOfAKind, highnum, 0, thiskicker);
		}

		// Two Pair
		else if (((CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.SecondCard.getCardNo()).getRank()
				&& (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank()
						|| CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand
								.get(eCardNo.FifthCard.getCardNo()).getRank()))
				|| (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.ThirdCard.getCardNo()).getRank()
						&& (CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand
								.get(eCardNo.FifthCard.getCardNo()).getRank())))
				& !bScored) {
			// Logic: High pair always includes second card and low pair always
			// includes fourth card
			int highnum = CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank().getRank();
			int lownum = CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank().getRank();
			int thiskicker;

			// Find the kicker card
			if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
					.get(eCardNo.SecondCard.getCardNo()).getRank()) {
				if (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank())
					thiskicker = CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank().getRank();
				else
					thiskicker = CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank().getRank();
			} else
				thiskicker = CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank();

			ScoreHand(eHandStrength.TwoPair, highnum, lownum, thiskicker);

		}

		// Pair
		else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.SecondCard.getCardNo()).getRank()
				|| CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.ThirdCard.getCardNo()).getRank()
				|| CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank()
				|| CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			int highnum;
			int thiskicker;

			// Find which position the pair is in, and choose highnum and kicker
			// based on that
			if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
					.get(eCardNo.SecondCard.getCardNo()).getRank()) {
				highnum = CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank();
				thiskicker = CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank().getRank();
			} else {
				thiskicker = CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank();
				if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.ThirdCard.getCardNo()).getRank())
					highnum = CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank().getRank();
				else if (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank())
					highnum = CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank().getRank();
				else
					highnum = CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank().getRank();
			}
			ScoreHand(eHandStrength.Pair, highnum, 0, thiskicker);
		}

		// High Card
		// I'll give you this one :)
		else {
			ScoreHand(eHandStrength.HighCard, CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank().getRank(), 0,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank().getRank());
		}
	}

	private void ScoreHand(eHandStrength hST, int HiHand, int LoHand, int Kicker) {
		this.HandStrength = hST.getHandStrength();
		this.HiHand = HiHand;
		this.LoHand = LoHand;
		this.Kicker = Kicker;
		this.bScored = true;

	}

	/**
	 * Custom sort to figure the best hand in an array of hands
	 */
	public static Comparator<Hand> HandRank = new Comparator<Hand>() {

		public int compare(Hand h1, Hand h2) {

			int result = 0;

			result = h2.getHandStrength() - h1.getHandStrength();

			if (result != 0) {
				return result;
			}

			result = h2.getHighPairStrength() - h1.getHighPairStrength();
			if (result != 0) {
				return result;
			}

			result = h2.getLowPairStrength() - h1.getLowPairStrength();
			if (result != 0) {
				return result;
			}

			result = h2.getKicker() - h1.getKicker();
			if (result != 0) {
				return result;
			}

			return 0;
		}
	};
}
