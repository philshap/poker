package poker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class DealerTest {

    @Test
    public void testFindBestHand() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Card[] deck = objectMapper.readValue("[\"3H\", \"7S\", \"3S\", \"QD\", \"AH\", \"3D\", \"4S\"]", Card[].class);
        Dealer dealer = new Dealer(deck);
        Hand hand = Hand.fromJsonArray("[\"3H\", \"3S\", \"3D\", \"AH\", \"QD\"]");
        assertEquals(hand, dealer.findBestHand());

        System.out.println("==== #3 ====");
        System.out.println("From the deck of cards: " + Arrays.toString(deck));
        System.out.println("The best hand is " + hand + ", which is a " + HandRank.rankHand(hand));
    }
}