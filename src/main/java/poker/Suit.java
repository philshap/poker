package poker;

/**
 * The suit of a card.
 */
public enum Suit {
    HEART("H", "hearts"),
    CLUB("C", "clubs"),
    DIAMOND("D", "diamonds"),
    SPADE("S", "spades");

    /** The JSON representation. */
    public final String representation;

    /** The user visible representation. */
    public final String displayName;

    Suit(String representation, String displayName) {
        this.representation = representation;
        this.displayName = displayName;
    }

    /**
     * Create a suit from its JSON representation
     *
     * @param jsonRepresentation the representation
     * @return the suit
     */
    public static Suit fromJson(String jsonRepresentation) {
        for (Suit suit : values()) {
            if (suit.representation.equals(jsonRepresentation)) {
                return suit;
            }
        }
        throw new IllegalArgumentException("Unknown suit " + jsonRepresentation);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
