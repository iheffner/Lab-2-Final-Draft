package pokerBase;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pokerEnums.eRank;
import pokerEnums.eSuit;

public class Deck_Test {

	Deck testDeck;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testDeck = new Deck();
	}

	@After
	public void tearDown() throws Exception {
		testDeck = null;
	}

	@Test
	public void TestFullDeck() {
		//Make sure 52 cards are dealt (in two ways)
		assertTrue(testDeck.getCards().size() == 52);
		assertTrue(testDeck.getTotalCards() == 52);
	}
	

}
