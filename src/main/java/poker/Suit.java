package poker;

public enum Suit {
    HEART("H", "hearts"),
    CLUB("C", "clubs"),
    DIAMOND("D", "diamonds"),
    SPADE("S", "spades");

    public final String representation;

    public final String visibleName;

    Suit(String representation, String visibleName) {
        this.representation = representation;
        this.visibleName = visibleName;
    }

    public static Suit fromJson(String jsonRepresentation) {
        for (Suit suit : values()) {
            if (suit.representation.equals(jsonRepresentation)) {
                return suit;
            }
        }
        throw new IllegalArgumentException("Unknown suit " + jsonRepresentation);
    }
}
