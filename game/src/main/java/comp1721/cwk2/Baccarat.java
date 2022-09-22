package comp1721.cwk2;

import java.util.Scanner;

public class Baccarat {
  private static BaccaratHand playerHand ;
  private static BaccaratHand bankerHand;
  private static Shoe shoe;

  private static int playerScore;
  private static int bankerScore;
  private static int ties;

  private static int currentRound;

  /**
   * Displays the contents and value of player's and banker's hand
   */
  private static void displayGameStatus() {
    final String currentPlayerHand = String.format("Player: %s = %d%n", playerHand.toString(), 
                                                                        playerHand.value());

    final String currentBankerHand = String.format("Banker: %s = %d%n", bankerHand.toString(), 
                                                                        bankerHand.value());

    System.out.print(String.format("%s%s", currentPlayerHand, currentBankerHand));
  }

  /**
   * Displays the number of player wins, banker wins and tied games
   */
  private static void displayScoreBoard() {
    final String roundsPlayed = String.format("%d rounds played%n", currentRound);

    final String playerResult = String.format("%d player wins%n", playerScore);
    final String bankerResult = String.format("%d banker wins%n", bankerScore);
    final String tiesResult = String.format("%d ties%n", ties);

    final String scoreBoard = String.format("%n%s%s%s%s", roundsPlayed, playerResult, 
                                                          bankerResult, tiesResult);

    System.out.print(scoreBoard);
  }

  /**
   * Deals third card to player
   * Prints a relevant message to the console
   * 
   * @throws CardException if player's hand does not already have a size of 2
   */
  private static void playerDrawsThirdCard() {
    if (playerHand.size() != 2) {
      throw new CardException("Cannot deal third card to a hand that does not contain two cards");
    }

    System.out.println("Dealing third card to player...");
    playerHand.add(shoe.deal());
  }

  /**
   * Deals third card to banker
   * Prints a relevant message to the console
   * 
   * @throws CardException if banker's hand does not already have a size of 2
   */
  private static void bankerDrawsThirdCard() {
    if (bankerHand.size() != 2) {
      throw new CardException("Cannot deal third card to a hand that does not contain two cards");
    }

    System.out.println("Dealing third card to banker...");
    bankerHand.add(shoe.deal());
  }

  /**
   * Deals a third card if a hand qualifies according to the tableau of drawing rules
   *  
   * @return true if a third card has been dealt, false otherwise
   */
  private static boolean consultDrawingRules() {
    if (playerHand.value() <= 5) {
      playerDrawsThirdCard();

      final int playerThirdCardValue = playerHand.cards.get(2).value();

      if (bankerHand.value() <= 2) {
        bankerDrawsThirdCard();
      } 
      else {
        switch (bankerHand.value()) {
          case 3:
            if (playerThirdCardValue != 8) {
              bankerDrawsThirdCard();
            }
            break;

          case 4:
            if (playerThirdCardValue >= 2 && playerThirdCardValue <= 7) {
              bankerDrawsThirdCard();
            }
            break;

          case 5:
            if (playerThirdCardValue >= 4 && playerThirdCardValue <= 7) {
              bankerDrawsThirdCard();
            }
            break;

          case 6:
            if (playerThirdCardValue == 6 || playerThirdCardValue == 7) {
              bankerDrawsThirdCard();
            }
            break;
        }
      }
      return true;
    } 
    else if (bankerHand.value() <= 5) {
      bankerDrawsThirdCard();
      return true;
    }
    
    return false;
  }

  /**
   * Increments player's score
   * Prints a relevant message to console
   */
  private static void playerWins() {
    System.out.println("Player win!");
    playerScore++;
  }

  /**
   * Increments banker's score
   * Prints a relevant message to console
   */
  private static void bankerWins() {
    System.out.println("Banker win!");
    bankerScore++;
  }

  /**
   * Increments the counter for tied games
   * Prints a relevant message to console
   */
  private static void gameTied() {
    System.out.println("Game tied!");
    ties++;
  }

  /**
   * Plays a single round of Punto Banco Baccarat
   */
  private static void playRound() {
    // Adding two cards to both hands
    for (int i = 0; i < 2; ++i) {
      playerHand.add(shoe.deal());
      bankerHand.add(shoe.deal());
    }

    displayGameStatus();

    if (playerHand.isNatural() || bankerHand.isNatural()) {
      if (bankerHand.value() > playerHand.value()) {
        bankerWins();
        return;
      } 
      else if (playerHand.value() > bankerHand.value()) {
        playerWins();
        return;
      } 
      else {
        gameTied();
        return;
      }
    } 

    final boolean thirdCardDrawn = consultDrawingRules();

    // Show game status again, with the newly drawn cards
    if (thirdCardDrawn) {
      displayGameStatus();
    }

    if (playerHand.value() > bankerHand.value()) {
      playerWins();
    } 
    else if (bankerHand.value() > playerHand.value()) {
      bankerWins();
    } 
    else {
      gameTied();
    }
  }

  /**
   * Checks if provided string (input) qualifies as a valid input
   * 
   * @param input
   * @return true if input is valid, false otherwise
   */
  private static boolean validateInput(String input) {
    final String[] validInputs = { "yes", "y", "no", "n" };

    for (String validInput : validInputs) {
      if (input.equalsIgnoreCase(validInput)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Prompts user, asking if they wish to play another round
   * 
   * Keeps prompting user until a valid input has been entered
   * 
   * @return false if user enters a negative response, true otherwise
   */
  private static boolean askUser() {
    String userInput;

    // Closing Scanner also closes System.in stream
    // Hence, closing Scanner is not possible when used multiple times
    final Scanner input = new Scanner(System.in);

    while (true) {
      System.out.print("Another round? (y/n): ");
      userInput = input.next();

      if (validateInput(userInput) == true) {
        break;
      } 
      else {
        System.out.println();
        System.out.println("Invalid input! Please try again.");
      }
    }

    if (userInput.equalsIgnoreCase("n") || userInput.equalsIgnoreCase("no")) {
      return false;
    }

    return true;
  }


  public static void main(String[] args) {
    boolean interactiveMode = false;

    if (args.length == 1 && args[0].equals("-i")) {
      interactiveMode = true;
    } 
    else if (args.length >= 1) {
      System.err.println("Error: invalid command line argument!");
      System.err.println("To run program in default mode provide no command line arguments");
      System.err.println("To run program in interactive mode use -i as command line argument");
      System.exit(1);
    }

    boolean keepPlaying = true;
    
    try {
      /**
       * Ideally either a input from the user or a command line argument should be
       * used to specify the number of decks. However, this is not part of the CW2
       * specification and hence, the number of decks is hardcoded instead.
       */
      final int numberOfDecks = 8;
      shoe = new Shoe(numberOfDecks);
      shoe.shuffle();

      playerHand = new BaccaratHand();
      bankerHand = new BaccaratHand();

      do {
        currentRound++;
        System.out.printf("%nRound %d%n", currentRound);

        playRound();

        playerHand.discard();
        bankerHand.discard();

        if (interactiveMode && shoe.size() >= 6) {
          keepPlaying = askUser();
        }

      } while (shoe.size() >= 6 && keepPlaying);
    }
    catch (Exception error) {
      System.err.printf("Error: %s%n", error.getMessage());
      System.exit(2);
    }

    displayScoreBoard();
  }

}
