package pokerBase;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pokerEnums.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Hand_Test {

	ArrayList<Card> royalFlushCards  = new ArrayList(
			Arrays.asList(new Card(eSuit.HEARTS,eRank.ACE),
					new Card(eSuit.HEARTS,eRank.KING),
					new Card(eSuit.HEARTS,eRank.QUEEN),
					new Card(eSuit.HEARTS,eRank.JACK),
					new Card(eSuit.HEARTS,eRank.TEN)));
	Hand testRoyalFlushHand = new Hand(royalFlushCards);
	
	ArrayList<Card> straightFlushCards  = new ArrayList(
			Arrays.asList(new Card(eSuit.HEARTS,eRank.EIGHT),
					new Card(eSuit.HEARTS,eRank.SEVEN),
					new Card(eSuit.HEARTS,eRank.FOUR),
					new Card(eSuit.HEARTS,eRank.FIVE),
					new Card(eSuit.HEARTS,eRank.SIX)));
	Hand testStraightFlushHand = new Hand(straightFlushCards);
	
	ArrayList<Card> fourKindCards  = new ArrayList(
			Arrays.asList(new Card(eSuit.HEARTS,eRank.THREE),
					new Card(eSuit.CLUBS,eRank.THREE),
					new Card(eSuit.HEARTS,eRank.QUEEN),
					new Card(eSuit.SPADES,eRank.THREE),
					new Card(eSuit.DIAMONDS,eRank.THREE)));
	Hand testFourHand = new Hand(fourKindCards);
	
	ArrayList<Card> fullHouseCards = new ArrayList(
			Arrays.asList(new Card(eSuit.HEARTS, eRank.KING),
					new Card(eSuit.CLUBS, eRank.KING),
					new Card(eSuit.DIAMONDS, eRank.KING),
					new Card(eSuit.DIAMONDS, eRank.FIVE),
					new Card(eSuit.CLUBS, eRank.FIVE)));
		Hand testFullHouse = new Hand(fullHouseCards);
	
	ArrayList<Card> flushCards  = new ArrayList(
			Arrays.asList(new Card(eSuit.HEARTS,eRank.THREE),
					new Card(eSuit.HEARTS,eRank.NINE),
					new Card(eSuit.HEARTS,eRank.QUEEN),
					new Card(eSuit.HEARTS,eRank.ACE),
					new Card(eSuit.HEARTS,eRank.TEN)));
	Hand testFlushHand = new Hand(flushCards);
	
	ArrayList<Card> straightCards = new ArrayList(
			Arrays.asList(new Card(eSuit.HEARTS, eRank.SIX),
					new Card(eSuit.CLUBS, eRank.FIVE),
					new Card(eSuit.DIAMONDS, eRank.SEVEN),
					new Card(eSuit.HEARTS, eRank.FOUR),
					new Card(eSuit.CLUBS, eRank.EIGHT)));
		Hand testStraight = new Hand(straightCards);
	
	ArrayList<Card> threeOfAKindCards = new ArrayList(
			Arrays.asList(new Card(eSuit.HEARTS, eRank.THREE),
					new Card(eSuit.CLUBS, eRank.THREE),
					new Card(eSuit.DIAMONDS, eRank.FIVE),
					new Card(eSuit.DIAMONDS, eRank.THREE),
					new Card(eSuit.CLUBS, eRank.SEVEN)));
			Hand testThreeOfAKind = new Hand(threeOfAKindCards);
		
	ArrayList<Card> twoPairCards  = new ArrayList(
			Arrays.asList(new Card(eSuit.HEARTS,eRank.THREE),
					new Card(eSuit.SPADES,eRank.QUEEN),
					new Card(eSuit.HEARTS,eRank.QUEEN),
					new Card(eSuit.SPADES,eRank.THREE),
					new Card(eSuit.DIAMONDS,eRank.FOUR)));
	Hand testTwoPairHand = new Hand(twoPairCards);
	
	ArrayList<Card> onePairCards = new ArrayList(
			Arrays.asList(new Card(eSuit.HEARTS, eRank.SIX),
					new Card(eSuit.CLUBS, eRank.KING),							
					new Card(eSuit.DIAMONDS, eRank.SEVEN),
					new Card(eSuit.DIAMONDS, eRank.FOUR),
					new Card(eSuit.CLUBS, eRank.SIX)));
		Hand testOnePair = new Hand(onePairCards);
	
	ArrayList<Card> highCardCards  = new ArrayList(
			Arrays.asList(new Card(eSuit.DIAMONDS,eRank.TWO),
					new Card(eSuit.CLUBS,eRank.KING),
					new Card(eSuit.HEARTS,eRank.THREE),
					new Card(eSuit.HEARTS,eRank.SIX),
					new Card(eSuit.HEARTS,eRank.FIVE)));
	Hand testHighCardHand = new Hand(highCardCards);
	
	@BeforeClass
	//public static void setUpBeforeClass() throws Exception {
		// = {new Card(eSuit.HEARTS,eRank.ACE),new Card(eSuit.HEARTS,eRank.ACE),new Card(eSuit.HEARTS,eRank.ACE),new Card(eSuit.HEARTS,eRank.ACE),new Card(eSuit.HEARTS,eRank.ACE)};

	//}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRoyalFlush() {
		testRoyalFlushHand.EvalHand();
		
		assertTrue(testRoyalFlushHand.getHandStrength()==100);
	}
	
	@Test
	public void testStraightFlush() {
		testStraightFlushHand.EvalHand();
		
		assertTrue(testStraightFlushHand.getHandStrength()==90);
	}
	
	@Test
	public void testFourOfAKind() {
		testFourHand.EvalHand();
		
		assertTrue(testFourHand.getHandStrength()==80);
		assertTrue(testFourHand.getKicker()==12);
	}
	
	@Test
	public void TestFullHouse() {
		testFullHouse.EvalHand();
		assertTrue(testFullHouse.getHandStrength() == 70);	
	}
	
	@Test
	public void testFlush() {
		testFlushHand.EvalHand();
		
		assertTrue(testFlushHand.getHandStrength()==60);
	}
	
	@Test
	public void testStraight(){
		testStraight.EvalHand();
		assertTrue(testStraight.getHandStrength() == 50);
	}
	
	@Test
	public void testThreeKind(){
		testThreeOfAKind.EvalHand();
		assertTrue(testThreeOfAKind.getHandStrength() == 40);
		assertTrue(testThreeOfAKind.getKicker() == 7);
	}
	
	@Test
	public void testTwoPair() {
		testTwoPairHand.EvalHand();
		
		assertTrue(testTwoPairHand.getHandStrength()==30);
		assertTrue(testTwoPairHand.getLowPairStrength()==3);
		assertTrue(testTwoPairHand.getHighPairStrength()==12);
		assertTrue(testTwoPairHand.getKicker()==4);
	}
	
	@Test
	public void testOnePair(){
		testOnePair.EvalHand();
		assertTrue(testOnePair.getHandStrength() == 20);
		assertTrue(testOnePair.getHighPairStrength() == 6);
		assertTrue(testOnePair.getKicker() == 13);
	}
	
	@Test
	public void testHighCard() {
		testHighCardHand.EvalHand();
		
		assertTrue(testHighCardHand.getHandStrength()==10);
		assertTrue(testHighCardHand.getHighPairStrength()==13);
		assertTrue(testHighCardHand.getKicker()==6);
	}
}
