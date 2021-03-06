package poker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HandTest {

    private static final String JSON_TEST_HAND = "[\"JH\", \"4C\", \"4S\", \"JC\", \"9H\"]";
    private static final Hand EXPECTED_HAND =
        new Hand(new Card(FaceValue.JACK, Suit.HEART), new Card(FaceValue.FOUR, Suit.CLUB),
            new Card(FaceValue.FOUR, Suit.SPADE), new Card(FaceValue.JACK, Suit.CLUB), new Card(FaceValue.NINE, Suit.HEART));

    @Test
    public void testFromJson() throws Exception {
        Hand hand = Hand.fromJsonArray(JSON_TEST_HAND);
        assertEquals(EXPECTED_HAND, hand);

        System.out.println("==== #1 ====");
        System.out.println("The JSON text " + JSON_TEST_HAND);
        System.out.println("Produced the hand " + hand);
    }

    @Test
    public void testCompareHighCards() throws Exception {
        Hand hand1 = Hand.fromJsonArray("[\"JH\", \"4C\", \"3S\", \"KC\", \"9H\"]");
        Hand hand2 = Hand.fromJsonArray("[\"JH\", \"4C\", \"3S\", \"JC\", \"9H\"]");
        assertEquals(-1, (int) Math.signum(hand1.compareTo(hand2)));
    }

    @Test
    public void testCompareHighCardsOnePair() throws Exception {
        Hand hand1 = Hand.fromJsonArray("[\"AH\", \"AC\", \"3S\", \"2C\", \"4H\"]");
        Hand hand2 = Hand.fromJsonArray("[\"KH\", \"KC\", \"10S\", \"JC\", \"QH\"]");
        assertEquals(1, (int) Math.signum(hand1.compareTo(hand2)));
    }

    @Test
    public void testCompare() throws Exception {
        Hand fullHouse = Hand.fromJsonArray("[\"JH\", \"4C\", \"4S\", \"JC\", \"JD\"]");
        Hand onePair = Hand.fromJsonArray("[\"JH\", \"4C\", \"3S\", \"JC\", \"9H\"]");
        assertEquals(1, (int) Math.signum(fullHouse.compareTo(onePair)));
        assertEquals(-1, (int) Math.signum(onePair.compareTo(fullHouse)));

        Hand fullHouseHighCard = Hand.fromJsonArray("[\"JH\", \"5C\", \"5S\", \"JC\", \"JD\"]");
        assertEquals(1, (int) Math.signum(fullHouseHighCard.compareTo(fullHouse)));

        Hand threePair = Hand.fromJsonArray("[\"JH\", \"4C\", \"5S\", \"JC\", \"JD\"]");
        Hand threePairHighCard = Hand.fromJsonArray("[\"JH\", \"6C\", \"5S\", \"JC\", \"JD\"]");
        assertEquals(1, (int) Math.signum(threePairHighCard.compareTo(threePair)));
    }

    @Test
    public void testFindBestHand() throws Exception {
        Hand fullHouse = Hand.fromJsonArray("[\"JH\", \"4C\", \"4S\", \"JC\", \"JD\"]");
        Hand onePair = Hand.fromJsonArray("[\"JH\", \"4C\", \"3S\", \"JC\", \"9H\"]");
        Hand threePair = Hand.fromJsonArray("[\"JH\", \"4C\", \"5S\", \"JC\", \"JD\"]");
        assertEquals(fullHouse, Hand.findBestHand(onePair, fullHouse, threePair));

        System.out.println("==== #2 ====");
        System.out.println("Given the hands:");
        System.out.println("  " + fullHouse);
        System.out.println("  " + onePair);
        System.out.println("  " + threePair);
        Hand bestHand = Hand.findBestHand(onePair, fullHouse, threePair);
        System.out.println("The best hand is " + bestHand);
    }
}
