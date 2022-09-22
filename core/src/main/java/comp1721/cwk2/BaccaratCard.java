package comp1721.cwk2;

public class BaccaratCard extends Card {
  public BaccaratCard(Rank r, Suit s) {
    super(r, s);
  }

  @Override
  public int value() {
    final int cardValue = super.value();
    
    // The picture cards and cards with value of 10 are worth 0 points
    if (cardValue == 10) {
      return 0;
    } 
    else {
      return cardValue;
    }
  }
}