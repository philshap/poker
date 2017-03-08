package poker;

/**
 * The face value of a card.
 */
public enum CardRank {
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


    public final String representation;

    public final String visibleName;

    CardRank(String representation) {
        this.representation = representation;
        this.visibleName = name().toLowerCase();
    }

    public static CardRank fromJson(String jsonRepresentation) {
        for (CardRank cardRank : values()) {
            if (cardRank.representation.equals(jsonRepresentation)) {
                return cardRank;
            }
        }
        throw new IllegalArgumentException("Unknown match " + jsonRepresentation);
    }
}
