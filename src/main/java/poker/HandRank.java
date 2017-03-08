package poker;

import java.util.Set;

/**
 * Poker hand rankings, in decreasing order of value.
 */
public enum HandRank {
    ROYAL_FLUSH("Royal Flush") {
        @Override
        boolean match(Hand hand) {
            return hand.matchSuit() && hand.matchStraight() && hand.highCard().cardRank == CardRank.ACE;
        }

        @Override
        public int compareSameHand(Hand hand, Hand otherHand) {
            // Two royal flushes have the same ranking.
            return 0;
        }
    },
    STRAIGHT_FLUSH("Straight Flush") {
        @Override
        boolean match(Hand hand) {
            return hand.matchSuit() && hand.matchStraight();
        }

        @Override
        public int compareSameHand(Hand hand, Hand otherHand) {
            return hand.highCard().compareTo(otherHand.highCard());
        }
    },
    FOUR_OF_A_KIND("Four of a Kind") {
        @Override
        boolean match(Hand hand) {
            return matchOfAKind(hand, 4);
        }

        @Override
        public int compareSameHand(Hand hand, Hand otherHand) {
            return compareOfAKind(hand, otherHand, 4);
        }
    },
    FULL_HOUSE("Full House") {
        @Override
        boolean match(Hand hand) {
            Set<Card> threeOfAKind = hand.matchRank(3);
            return threeOfAKind != null && hand.matchRank(2, threeOfAKind) != null;
        }

        @Override
        public int compareSameHand(Hand hand, Hand otherHand) {
            Set<Card> matchCardsOne = hand.matchRank(3);
            Set<Card> otherMatchCardsOne = otherHand.matchRank(3);
            int compareOne = compareFirstCardRank(matchCardsOne, otherMatchCardsOne);
            if (compareOne != 0) {
                return compareOne;
            }
            Set<Card> matchCardsTwo = hand.matchRank(2, matchCardsOne);
            Set<Card> otherMatchCardsTwo = otherHand.matchRank(2, otherMatchCardsOne);
            return compareFirstCardRank(matchCardsTwo, otherMatchCardsTwo);
        }
    },
    FLUSH("Flush") {
        @Override
        boolean match(Hand hand) {
            return hand.matchSuit();
        }

        @Override
        public int compareSameHand(Hand hand, Hand otherHand) {
            return Hand.compareHighCards(hand, otherHand);
        }
    },
    STRAIGHT("Straight") {
        @Override
        boolean match(Hand hand) {
            return hand.matchStraight();
        }

        @Override
        public int compareSameHand(Hand hand, Hand otherHand) {
            return hand.highCard().cardRank.compareTo(otherHand.highCard().cardRank);
        }
    },
    THREE_OF_A_KIND("Three of a Kind") {
        @Override
        boolean match(Hand hand) {
            return matchOfAKind(hand, 3);
        }

        @Override
        public int compareSameHand(Hand hand, Hand otherHand) {
            return compareOfAKind(hand, otherHand, 3);
        }
    },
    TWO_PAIR("Two Pair") {
        @Override
        boolean match(Hand hand) {
            Set<Card> matchCardsOne = hand.matchRank(2);
            return matchCardsOne != null && hand.matchRank(2, matchCardsOne) != null;
        }

        @Override
        public int compareSameHand(Hand hand, Hand otherHand) {
            Set<Card> matchCardsOne = hand.matchRank(2);
            Set<Card> otherMatchCardsOne = otherHand.matchRank(2);
            int compareOne = compareFirstCardRank(matchCardsOne, otherMatchCardsOne);
            if (compareOne != 0) {
                return compareOne;
            }
            Set<Card> matchCardsTwo = hand.matchRank(2, matchCardsOne);
            Set<Card> otherMatchCardsTwo = otherHand.matchRank(2, otherMatchCardsOne);
            int compareTwo = compareFirstCardRank(matchCardsTwo, otherMatchCardsTwo);
            if (compareTwo != 0) {
                return compareTwo;
            }
            return Hand.compareHighCards(hand, otherHand);
        }
    },
    ONE_PAIR("One Pair") {
        @Override
        boolean match(Hand hand) {
            return matchOfAKind(hand, 2);
        }

        @Override
        public int compareSameHand(Hand hand, Hand otherHand) {
            return compareOfAKind(hand, otherHand, 2);

        }
    },
    HIGH_CARD("High Card") {
        @Override
        boolean match(Hand hand) {
            return true;
        }

        @Override
        public int compareSameHand(Hand hand, Hand otherHand) {
            return Hand.compareHighCards(hand, otherHand);
        }
    };

    public final String visibleName;

    HandRank(String visibleName) {
        this.visibleName = visibleName;
    }

    /**
     * Given a hand, generate its rank.
     *
     * @param hand the hand
     * @return the hand's rank
     */
    static HandRank rankHand(Hand hand) {
        for (HandRank handRank : values()) {
            if (handRank.match(hand)) {
                return handRank;
            }
        }
        return HIGH_CARD;
    }

    /**
     * Compare two hands that have the same ranking. Use high cards to determine higher ranked hand.
     *
     * @param hand first hand to compare
     * @param otherHand second hand to compare
     * @return -N, 0, N, if hand one is less than, equal to, or greater than hand two.
     */
    public abstract int compareSameHand(Hand hand, Hand otherHand);

    /**
     * Given a hand, try to match it to this ranking.
     *
     * @param hand the hand to match
     * @return true if the hand matches this ranking
     */
    abstract boolean match(Hand hand);

    static private int compareFirstCardRank(Set<Card> matchCardsOne, Set<Card> otherMatchCardsOne) {
        return matchCardsOne.iterator().next().cardRank.compareTo(otherMatchCardsOne.iterator().next().cardRank);
    }

    static private boolean matchOfAKind(Hand hand, int numCards) {
        return hand.matchRank(numCards) != null;
    }

    static private int compareOfAKind(Hand hand, Hand otherHand, int numCards) {
        Set<Card> matchCards = hand.matchRank(numCards);
        Set<Card> otherMatchCards = otherHand.matchRank(numCards);
        int rankCompare = compareFirstCardRank(matchCards, otherMatchCards);
        if (rankCompare != 0) {
            return rankCompare;
        }
        return Hand.compareHighCards(hand, otherHand);
    }

    @Override
    public String toString() {
        return visibleName;
    }
}
