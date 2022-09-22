package comp1721.cwk2;

import java.util.Collections;

import comp1721.cwk2.Card.Rank;
import comp1721.cwk2.Card.Suit;

public class Shoe extends CardCollection {
  public Shoe(int decks) {
    if (decks != 8 && decks != 6) {
      throw new CardException("A shoe can only have 6 or 8 decks");
    }

    // A deck consists of 52 cards
    final int numberOfCards = decks * 52;

    while (size() < numberOfCards) {
      for (Suit suit : Suit.values()) {
        for (Rank rank : Rank.values()) {
          cards.add(new BaccaratCard(rank, suit));
        }
      }
    }
  }

  public void shuffle() {
    Collections.shuffle(cards);
  }

  public Card deal() {
    if (isEmpty()) {
      throw new CardException("Cannot deal from an empty shoe");
    }

    return cards.remove(0);
  }
}