/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

/**
 *
 * @author aryan bhardwaj, abdul basith
 * Date - June 30 , 2023
 */
import java.util.ArrayList;
import java.util.Scanner;

public class UnoGame {
    
    public static void main(String[] args) {
        ArrayList<UnoCard> playerDeck = new ArrayList<UnoCard>();
        ArrayList<UnoCard> computerDeck = new ArrayList<UnoCard>();
        int gameResult; // 0 - undecided; 1 - player wins; -1 - computer wins.
        Scanner userInput;
        UnoCard topCard; 
        int chosenIndex; // Index of chosen card for both player and computer
        String selectedColor; // Mainly used for wild cards

        gameLoop:
        while (true) {
            playerDeck.clear(); // card on top of the "pile"
            computerDeck.clear();
            gameResult = 0;
            topCard = new UnoCard();
            selectedColor = topCard.color;

            System.out.println("\nWelcome to Uno! setting up decks...");
            drawCards(7, playerDeck);
            drawCards(7, computerDeck);

            // Game Turns
            for (boolean playersTurn = true; gameResult == 0; playersTurn ^= true) {
                chosenIndex = 0;
                System.out.println("\nThe top card is: " + topCard.getFace());

                if (playersTurn) {
                    // Player's Turn
                    System.out.println("Your turn! Your options:");
                    for (int i = 0; i < playerDeck.size(); i++) {
                        System.out.print(String.valueOf(i + 1) + ". " +
                                ((UnoCard) playerDeck.get(i)).getFace() + "\n");
                    }
                    System.out.println(String.valueOf(playerDeck.size() + 1) + ". " + "Draw a card" + "\n" +
                            String.valueOf(playerDeck.size() + 2) + ". " + "Quit");

                    // Repeatedly prompt until valid input
                    do {
                        System.out.print("\nEnter the number: ");
                        userInput = new Scanner(System.in);
                    } while (!userInput.hasNextInt());
                    chosenIndex = userInput.nextInt() - 1;

                    // Taking action
                    if (chosenIndex == playerDeck.size()) {
                        drawCards(1, playerDeck);
                    } else if (chosenIndex == playerDeck.size() + 1) {
                        break gameLoop;
                    } else if (((UnoCard) playerDeck.get(chosenIndex)).canPlace(topCard, selectedColor)) {
                        topCard = (UnoCard) playerDeck.get(chosenIndex);
                        playerDeck.remove(chosenIndex);
                        selectedColor = topCard.color;
                        // Execute special card actions
                        if (topCard.value >= 10) {
                            playersTurn = false; // Skip the next turn

                            switch (topCard.value) {
                                case 12: // Draw 2
                                    System.out.println("Drawing 2 cards for the computer...");
                                    drawCards(2, computerDeck);
                                    break;

                                case 13: case 14: // Wild cards
                                    do {
                                        System.out.print("\nChoose the color you want: ");
                                        userInput = new Scanner(System.in);
                                    } while (!userInput.hasNext("R..|r..|G....|g....|B...|b...|Y.....|y....."));
                                    if (userInput.hasNext("R..|r..")) {
                                        selectedColor = "Red";
                                    } else if (userInput.hasNext("G....|g....")) {
                                        selectedColor = "Green";
                                    } else if (userInput.hasNext("B...|b...")) {
                                        selectedColor = "Blue";
                                    } else if (userInput.hasNext("Y.....|y.....")) {
                                        selectedColor = "Yellow";
                                    }
                                    System.out.println("You chose " + selectedColor);
                                    if (topCard.value == 14) {
                                        System.out.println("Drawing 4 cards for the computer...");
                                        drawCards(4, computerDeck);
                                    }
                                    break;
                            }
                        }
                    } else {
                        System.out.println("Invalid choice. Turn skipped.");
                    }
                } else {
                    // Computer's Turn
                    System.out.println("My turn! I have " + String.valueOf(computerDeck.size()) +
                            " cards left!" + ((computerDeck.size() == 1) ? "...Uno!" : ""));
                    chosenIndex = findValidCard(computerDeck, topCard, selectedColor);

                    if (chosenIndex == computerDeck.size()) {
                        System.out.println("I don't have any playable cards. Drawing a card...");
                        drawCards(1, computerDeck);
                    } else {
                        topCard = (UnoCard) computerDeck.get(chosenIndex);
                        computerDeck.remove(chosenIndex);
                        selectedColor = topCard.color;
                        System.out.println("I choose " + topCard.getFace() + "!");

                        // Execute special card actions
                        if (topCard.value >= 10) {
                            playersTurn = true; // Skip the next turn

                            switch (topCard.value) {
                                case 12: // Draw 2
                                    System.out.println("Drawing 2 cards for you...");
                                    drawCards(2, playerDeck);
                                    break;

                                case 13: case 14: // Wild cards
                                    do {
                                        selectedColor = getRandomColor();
                                    } while (selectedColor.equals("none"));
                                    System.out.println("The new color is " + selectedColor);
                                    if (topCard.value == 14) {
                                        System.out.println("Drawing 4 cards for you...");
                                        drawCards(4, playerDeck);
                                    }
                                    break;
                            }
                        }
                    }

                    // Check if decks are empty
                    if (playerDeck.size() == 0) {
                        gameResult = 1;
                    } else if (computerDeck.size() == 0) {
                        gameResult = -1;
                    }
                }
            } // Game Turns loop end

            // Display Results
            if (gameResult == 1) {
                System.out.println("Congratulations! You win!");
            } else {
                System.out.println("Oops! You lost this time.");
            }

            System.out.print("\nPlay again? ");
            userInput = new Scanner(System.in);
            if (userInput.next().toLowerCase().contains("n")) {
                break;
            }
        } // Game loop end

        System.out.println("Thank you for playing!");
    }

    public static void drawCards(int numCards, ArrayList<UnoCard> deck) {
        for (int i = 0; i < numCards; i++) {
            deck.add(new UnoCard());
        }
    }

    public static int findValidCard(ArrayList<UnoCard> deck, UnoCard topCard, String color) {
        for (int i = 0; i < deck.size(); i++) {
            if (((UnoCard) deck.get(i)).canPlace(topCard, color)) {
                return i;
            }
        }
        return deck.size();
    }

    public static String getRandomColor() {
        String[] colors = {"Red", "Green", "Blue", "Yellow"};
        return colors[(int) (Math.random() * colors.length)];
    }
}
    
