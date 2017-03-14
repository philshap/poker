package poker;

import java.util.Collections;
import java.util.Set;

/**
 * Poker hand rankings, in decreasing order of value.
 */
// Each hand rank generates a score when a hand is matched to that rank. The score can be used to compare two
// hands with the same poker rank. The score is created by using the face value of the cards. If a score needs to
// represent more than face value (such as in a full house) the higher counting face value is stored at a higher
// "place", where each place can store a single rank.
public enum PokerHandRank {
    ROYAL_FLUSH() {
        @Override
        HandScore score(Hand hand) {
            if (hand.matchSuit() && hand.matchStraight() && hand.highCard().faceValue == FaceValue.ACE) {
                return new HandScore(this, 0, 0, "a Royal Flush");
            }
            return null;
        }

    },
    STRAIGHT_FLUSH() {
        @Override
        HandScore score(Hand hand) {
            if (hand.matchSuit() && hand.matchStraight()) {
                FaceValue highCard = hand.highCard().faceValue;
                return new HandScore(this, highCard.ordinal(), 0, "Straight Flush, " + highCard + " high");
            }
            return null;
        }

    },
    FOUR_OF_A_KIND() {
        @Override
        HandScore score(Hand hand) {
            Set<Card> cards = hand.matchFaceValue(4);
            if (cards != null) {
                FaceValue fourOfAKindRank = cards.iterator().next().faceValue;
                return new HandScore(this, FaceValue.score(fourOfAKindRank), hand.highCardScore(cards),
                    "Four of a Kind, " + fourOfAKindRank + " with " + hand.createKickerMessage(cards));
            }
            return null;
        }

    },
    FULL_HOUSE() {
        @Override
        HandScore score(Hand hand) {
            Set<Card> threeOfAKind = hand.matchFaceValue(3);
            if (threeOfAKind != null) {
                Set<Card> twoOfAKind = hand.matchFaceValue(2, threeOfAKind);
                if (twoOfAKind != null) {
                    FaceValue threeOfAKindRank = threeOfAKind.iterator().next().faceValue;
                    FaceValue twoOfAKindRank = twoOfAKind.iterator().next().faceValue;
                    int score = FaceValue.score(threeOfAKindRank, twoOfAKindRank);
                    return new HandScore(this, score, 0,
                        "Full house, " + threeOfAKindRank + "s over " + twoOfAKindRank + "s");
                }
            }
            return null;
        }

    },
    FLUSH() {
        @Override
        HandScore score(Hand hand) {
            if (hand.matchSuit()) {
                return new HandScore(this, hand.highCardScore(), 0, "a Flush, " + hand.highCard().faceValue + " high");
            }
            return null;
        }

    },
    STRAIGHT() {
        @Override
        HandScore score(Hand hand) {
            if (hand.matchStraight()) {
                return new HandScore(this, hand.highCard().faceValue.ordinal(), 0,
                    "a Straight, " + hand.highCard().faceValue + " high");
            }
            return null;
        }

    },
    THREE_OF_A_KIND() {
        @Override
        HandScore score(Hand hand) {
            Set<Card> cards = hand.matchFaceValue(3);
            if (cards != null) {
                FaceValue threeOfAKindRank = cards.iterator().next().faceValue;
                return new HandScore(this, FaceValue.score(threeOfAKindRank), hand.highCardScore(cards),
                    "Three of a Kind, " + threeOfAKindRank + "s, with " + hand.createKickerMessage(cards));
            }
            return null;
        }

    },
    TWO_PAIR() {
        @Override
        HandScore score(Hand hand) {
            Set<Card> matchCardsOne = hand.matchFaceValue(2);
            if (matchCardsOne != null) {
                Set<Card> matchCardsTwo = hand.matchFaceValue(2, matchCardsOne);
                if (matchCardsTwo != null) {
                    FaceValue faceValueOne = matchCardsOne.iterator().next().faceValue;
                    FaceValue faceValueTwo = matchCardsTwo.iterator().next().faceValue;
                    matchCardsOne.addAll(matchCardsTwo);
                    int score = FaceValue.score(faceValueOne, faceValueTwo);
                    return new HandScore(this, score, hand.highCardScore(matchCardsOne),
                        "Two Pair, " + faceValueOne + "s over " + faceValueTwo + "s with " +
                            hand.createKickerMessage(matchCardsOne));
                }
            }
            return null;
        }

    },
    ONE_PAIR() {
        @Override
        HandScore score(Hand hand) {
            Set<Card> cards = hand.matchFaceValue(2);
            if (cards != null) {
                FaceValue pairRank = cards.iterator().next().faceValue;
                return new HandScore(this, FaceValue.score(pairRank), hand.highCardScore(cards),
                    "One Pair, " + pairRank + "s, with " + hand.createKickerMessage(cards));
            }
            return null;
        }

    },
    HIGH_CARD() {
        @Override
        HandScore score(Hand hand) {
            int score = hand.highCardScore();
            return new HandScore(this, score, 0, "High Card, with " + hand.createKickerMessage(
                Collections.emptySet()));
        }

    };

    /**
     * Given a hand, generate its score based on poker hand rankings.
     *
     * @param hand the hand
     * @return the hand's score
     */
    static HandScore scoreHand(Hand hand) {
        for (PokerHandRank handRank : values()) {
            HandScore score = handRank.score(hand);
            if (score != null) {
                return score;
            }
        }
        throw new UnsupportedOperationException("Could not score hand " + hand);
    }

    /**
     * Given a Hand, create a score for the hand.
     *
     * @param hand the hand to score
     * @return the hand's score
     */
    abstract HandScore score(Hand hand);
}
