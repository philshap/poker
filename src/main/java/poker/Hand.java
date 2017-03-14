package poker;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A Hand is a collection of Cards, scored using poker hand rules.
 */
public class Hand implements Comparable<Hand> {
    /** The size of a hand. */
    static final int HAND_SIZE = 5;

    /** The cards in a hand. */
    private final Card[] cards;

    /** The score of a hand. */
    private final HandScore score;

    Hand(Card... cards) {
        if (cards.length != HAND_SIZE) {
            throw new IllegalArgumentException("A hand must contain " + HAND_SIZE + " cards, not " + cards.length);
        }
        this.cards = cards;
        Arrays.sort(this.cards);
        score = PokerHandRank.scoreHand(this);
    }

    static Hand fromJsonArray(String jsonArray) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return new Hand(objectMapper.readValue(jsonArray, Card[].class));
    }

    /**
     * Generate a score for this hand based only on the face values of the cards. This is used as a tie breaker
     * for hands that are the same poker hand.
     *
     * @return the score of the hand, using only card face values
     */
    int highCardScore() {
        return highCardScore(Collections.emptySet());
    }

    /**
     * Generate a score for this hand based only on the face values of the cards. This is used as a tie breaker
     * for hands that are the same poker hand.
     *
     * @param skipCards skip these cards when generating the score
     * @return the score of the hand, using only card face values
     */
    int highCardScore(Collection<Card> skipCards) {
        return Arrays.stream(cards).filter(card -> !skipCards.contains(card))
            .map(Card::getFaceValue).mapToInt(Enum::ordinal).sum();
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
                if (lastCard.faceValue.ordinal() != card.faceValue.ordinal() + 1) {
                    return false;
                }
            }
            lastCard = card;
        }
        return true;
    }

    /**
     * Create a descriptive message of the cards in this hand as 'kickers'. Cards in skipCards are omitted from the
     * message.
     *
     * @param skipCards the cards to skip
     * @return the kicker message for this hand
     */
    String createKickerMessage(Set<Card> skipCards) {
        StringBuilder message = new StringBuilder();
        int numKickers = 0;
        for (Card card : cards) {
            if (skipCards.contains(card)) {
                continue;
            }
            if (numKickers != 0) {
                message.append(" and ");
            }
            message.append(card.faceValue);
            numKickers++;
        }
        if (numKickers > 0) {
            message.append(" kicker");
            if (numKickers != 1) {
                message.append("s");
            }
            return message.toString();
        }
        return "";
    }

    /**
     * Match a number cards in this hand by face value.
     *
     * @param numCards the required number of cards to match
     * @return the set of cards that match, or null if no match is present
     */
    Set<Card> matchFaceValue(int numCards) {
        return matchFaceValue(numCards, Collections.emptySet());
    }

    /**
     * Match cards from this hand by face value. Returns the first matched set of cards, if more than one
     * matches by the required number.
     *
     * @param numCards the required number of cards to match
     * @param skipCards if a card is in this set, don't consider it for the match
     * @return the set of cards that match, or null if no match is present
     */
    Set<Card> matchFaceValue(int numCards, Collection<Card> skipCards) {
        for (Card card : cards) {
            if (skipCards.contains(card)) {
                // Skip card.
                continue;
            }
            FaceValue rank = card.faceValue;
            Set<Card> match = new HashSet<>();
            for (Card matchCard : cards) {
                if (matchCard.faceValue == rank) {
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
        return Arrays.toString(cards) + ": " + score.description;
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
        return score.compareTo(otherHand.score);
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
