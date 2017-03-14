package poker;

/**
 * Hands are scored by the hand type (ranking) and the high card score for the hand. The score
 * also includes a description of the hand.
 */
public class HandScore implements Comparable<HandScore> {
    /** The poker hand rank. */
    public final PokerHandRank rank;
    /** A score, used if two hands have the same rank. */
    public final int matchScore;
    /** A score, used if two hands have the same rank and match score. */
    public final int highCardScore;
    /** A description of the hand. */
    public final String description;

    public HandScore(PokerHandRank rank, int matchScore, int highCardScore, String description) {
        this.rank = rank;
        this.matchScore = matchScore;
        this.highCardScore = highCardScore;
        this.description = description;
    }

    @Override
    public int compareTo(HandScore other) {
        if (rank != other.rank) {
            return other.rank.ordinal() - rank.ordinal();
        }
        if (matchScore != other.matchScore) {
            return matchScore - other.matchScore;
        }
        return highCardScore - other.highCardScore;
    }
}
