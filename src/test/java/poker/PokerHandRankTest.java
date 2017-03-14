package poker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class PokerHandRankTest {

    @Parameterized.Parameters(name = "{index}: hand = {0}, match = {1}")
    public static Object[][] parameters() throws Exception {
        return new Object[][] {
            {Hand.fromJsonArray("[\"3H\", \"7S\", \"3S\", \"3D\", \"AH\"]"), PokerHandRank.THREE_OF_A_KIND},
            {Hand.fromJsonArray("[\"3H\", \"3C\", \"3S\", \"3D\", \"AH\"]"), PokerHandRank.FOUR_OF_A_KIND},
            {Hand.fromJsonArray("[\"3H\", \"7S\", \"3S\", \"QD\", \"AH\"]"), PokerHandRank.ONE_PAIR},
            {Hand.fromJsonArray("[\"3H\", \"7S\", \"3S\", \"AD\", \"AH\"]"), PokerHandRank.TWO_PAIR},
            {Hand.fromJsonArray("[\"3H\", \"AS\", \"3S\", \"3D\", \"AH\"]"), PokerHandRank.FULL_HOUSE},
            {Hand.fromJsonArray("[\"3H\", \"AS\", \"2S\", \"10D\", \"KC\"]"), PokerHandRank.HIGH_CARD},
            {Hand.fromJsonArray("[\"3H\", \"7H\", \"2H\", \"JH\", \"AH\"]"), PokerHandRank.FLUSH},
            {Hand.fromJsonArray("[\"3H\", \"2S\", \"4H\", \"5D\", \"6C\"]"), PokerHandRank.STRAIGHT},
            {Hand.fromJsonArray("[\"3C\", \"2C\", \"4C\", \"5C\", \"6C\"]"), PokerHandRank.STRAIGHT_FLUSH},
            {Hand.fromJsonArray("[\"KH\", \"AH\", \"QH\", \"JH\", \"10H\"]"), PokerHandRank.ROYAL_FLUSH},
        };
    }

    @Parameterized.Parameter
    public Hand hand;

    @Parameterized.Parameter(1)
    public PokerHandRank expectedRank;

    @Test
    public void testMatch() throws Exception {
        assertEquals(expectedRank, PokerHandRank.scoreHand(hand).rank);
    }
}