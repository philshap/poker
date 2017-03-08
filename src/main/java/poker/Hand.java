package poker;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A Hand is a collection of Cards.
 */
public class Hand implements Comparable<Hand> {
    static final int HAND_SIZE = 5;

    private final Card[] cards;

    Hand(Card... cards) {
        if (cards.length != HAND_SIZE) {
            throw new IllegalArgumentException("A hand must contain " + HAND_SIZE + " cards, not " + cards.length);
        }
        this.cards = cards;
        Arrays.sort(this.cards);
    }

    static Hand fromJsonArray(String jsonArray) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return new Hand(objectMapper.readValue(jsonArray, Card[].class));
    }

    Card highCard() {
        // Cards are sorted so the high card is always first.
        return cards[0];
    }

    /**
     * Match the cards in this hand by suit. All cards in the hand must match.
     *
     * @return true if this hand matches
     */
    boolean matchSuit() {
        Suit suit = null;
        for (Card matchCard : cards) {
            if (suit != null && matchCard.suit != suit) {
                return false;
            }
            suit = matchCard.suit;
        }
        return true;
    }

    /**
     * Find a straight in this hand. All cards in the hand must be in the straight.
     *
     * @return true if this hand matches
     */
    boolean matchStraight() {
        // Cards are stored in order, so we just need to make sure they're sequential.
        Card lastCard = null;
        for (Card card : cards) {
            if (lastCard != null) {
                if (lastCard.cardRank.ordinal() != card.cardRank.ordinal() + 1) {
                    return false;
                }
            }
            lastCard = card;
        }
        return true;
    }


    /**
     * Match a number cards in this hand by rank (face value).
     *
     * @param numCards the required number of cards to match
     * @return the set of cards that match, or null if no match is present
     */
    Set<Card> matchRank(int numCards) {
        return matchRank(numCards, Collections.emptySet());
    }

    /**
     * Match a number cards in this hand by rank (face value).
     *
     * @param numCards the required number of cards to match
     * @param skip if a card is in this set, don't consider it for the match
     * @return the set of cards that match, or null if no match is present
     */
    Set<Card> matchRank(int numCards, Set<Card> skip) {
        for (Card card : cards) {
            if (skip.contains(card)) {
                // Skip already matched card.
                continue;
            }
            CardRank rank = card.cardRank;
            Set<Card> match = new HashSet<>();
            for (Card matchCard : cards) {
                if (matchCard.cardRank == rank) {
                    match.add(matchCard);
                    if (match.size() == numCards) {
                        return match;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return Arrays.toString(cards);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hand hand = (Hand) o;
        return Arrays.equals(cards, hand.cards);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(cards);
    }

    @Override
    public int compareTo(Hand otherHand) {
        HandRank ranking = HandRank.rankHand(this);
        HandRank otherRanking = HandRank.rankHand(otherHand);
        if (ranking == otherRanking) {
            return ranking.compareSameHand(this, otherHand);
        }
        return otherRanking.ordinal() - ranking.ordinal();
    }

    static int compareHighCards(Hand hand, Hand otherHand) {
        for (int i = 0; i < hand.cards.length; i++) {
            int compare = hand.cards[i].cardRank.compareTo(otherHand.cards[i].cardRank);
            if (compare != 0) {
                return compare;
            }
        }
        return 0;
    }

    /**
     * Given a list of hands, return the best one.
     *
     * @param hands the hands to compare
     * @return the best hand.
     */
    static Hand findBestHand(Hand... hands) {
        Arrays.sort(hands);
        // Best hand is the last one in the list.
        return hands[hands.length - 1];
    }
}
