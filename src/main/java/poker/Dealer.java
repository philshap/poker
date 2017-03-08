package poker;

import java.util.Arrays;

/**
 * Class that builds the best hand from a deck of cards.
 */
public class Dealer {
    /** Deal cards from this deck. */
    private final Card[] deck;

    public Dealer(Card[] deck) {
        this.deck = deck;
    }

    public Hand findBestHand() {
        Hand[] bestHand = {null};
        CombineUtil.combinations(Arrays.asList(deck), Hand.HAND_SIZE).forEach(
            cards -> {
                Hand hand = new Hand(cards.toArray(new Card[cards.size()]));
                if (bestHand[0] == null || bestHand[0].compareTo(hand) < 0) {
                    bestHand[0] = hand;
                }
            }
        );
        return bestHand[0];
    }
}
