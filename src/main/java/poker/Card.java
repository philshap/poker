package poker;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

/**
 * A card in a deck. A card is represented by its face value and suit.
 */
public class Card implements Comparable<Card> {
    /** The face value. */
    final FaceValue faceValue;
    /** The suit. */
    final Suit suit;

    Card(FaceValue faceValue, Suit suit) {
        this.faceValue = faceValue;
        this.suit = suit;
    }

    public FaceValue getFaceValue() {
        return faceValue;
    }

    @JsonCreator
    Card(String jsonData) {
        this(FaceValue.fromJson(jsonData.substring(0, jsonData.length() - 1)),
            Suit.fromJson(jsonData.substring(jsonData.length() - 1)));
    }

    @Override
    public String toString() {
        return faceValue + " of " + suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        return faceValue == card.faceValue && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(faceValue, suit);
    }

    @Override
    public int compareTo(Card o) {
        int rankCompare = o.faceValue.ordinal() - faceValue.ordinal();
        if (rankCompare != 0) {
            return rankCompare;
        }

        return o.suit.ordinal() - suit.ordinal();
    }
}
