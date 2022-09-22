package comp1721.cwk2;

import java.util.List;
import java.util.ArrayList;

public class BaccaratHand extends CardCollection {
  public BaccaratHand() {

  }

  @Override
  public int value() {
    final int points = super.value();

    // If total value is greater than 9, return units digit of the total value.
    if (points > 9) {
      return points % 10;
    }

    return points;
  }

  public boolean isNatural() {
    final int points = value();

    if (points == 8 || points == 9) {
      return true;
    } 
    
    return false;
}

  @Override
  public String toString() {
    List<String> stringRepresentations = new ArrayList<>();

    for (Card card : cards) {
      stringRepresentations.add(card.toString());
    }

    return String.join(" ", stringRepresentations);
  }
}