package poker;

/**
 * The face value (rank) of a card.
 */
public enum FaceValue {
    DEUCE("2"),
    TREY("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K"),
    ACE("A");

    /** The JSON representation. */
    public final String representation;

    /** The user visible representation. */
    public final String displayName;

    FaceValue(String representation) {
        this.representation = representation;
        displayName = name().toLowerCase();
    }

    /**
     * Create a face value from its JSON representation
     *
     * @param jsonRepresentation the representation
     * @return the face value
     */
    public static FaceValue fromJson(String jsonRepresentation) {
        for (FaceValue faceValue : values()) {
            if (faceValue.representation.equals(jsonRepresentation)) {
                return faceValue;
            }
        }
        throw new IllegalArgumentException("Unknown face value " + jsonRepresentation);
    }

    /**
     * Get the score for this face value.
     *
     * @param place the place of this value, to support weighted scores
     * @return the score
     */
    private int getScore(int place) {
        return (ordinal() + 1) * (int) Math.pow(values().length, place);
    }

    /**
     * Create a score from a series of face values. The parameters are in order of highest to lowest weight, and the
     * score is scaled to reflect this weighing.
     *
     * @param values the face values to create a score from
     * @return a score value of these face values.
     */
    static int score(FaceValue... values) {
        int score = 0;
        for (int i = 0; i < values.length; i++) {
            FaceValue rank = values[i];
            score += rank.getScore(values.length - i);
        }
        return score;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
