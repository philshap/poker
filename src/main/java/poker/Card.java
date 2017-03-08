package poker;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

/**
 * A card in a deck. A card is represented by its rank (face value) and suit.
 */
public class Card implements Comparable<Card> {
    public final CardRank cardRank;
    public final Suit suit;

    public Card(CardRank cardRank, Suit suit) {
        this.cardRank = cardRank;
        this.suit = suit;
    }

    @JsonCreator
    public Card(String jsonData) {
        this(CardRank.fromJson(jsonData.substring(0, jsonData.length() - 1)),
            Suit.fromJson(jsonData.substring(jsonData.length() - 1)));
    }

    @Override
    public String toString() {
        return cardRank.visibleName + " of " + suit.visibleName;
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
        return cardRank == card.cardRank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardRank, suit);
    }

    @Override
    public int compareTo(Card o) {
        int rankCompare = o.cardRank.ordinal() - cardRank.ordinal();
        if (rankCompare != 0) {
            return rankCompare;
        }

        return o.suit.ordinal() - suit.ordinal();
    }
}
