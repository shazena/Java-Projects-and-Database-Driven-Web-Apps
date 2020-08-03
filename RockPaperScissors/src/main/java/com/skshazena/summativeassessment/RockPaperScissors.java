package com.skshazena.summativeassessment;

import java.util.Random;
import java.util.Scanner;

/**
 * Rock Paper Scissors Game - You can play between 1 and 10 rounds and then
 * repeat this process if you'd like.
 * Blog Post written about my process at shazena.wordpress.com
 *
 * @author Shazena
 * Apr 23, 2020. Edits made on Apr 24, 2020
 */
public class RockPaperScissors {

    /**
     * Rock Paper Scissors Main Method - This function controls the majority of
     * the game play. It references other methods which can be found below.
     *
     * @param args
     */
    public static void main(String[] args) {

        int rounds, userChoice, compChoice, whoWon;
        int tieCount;
        int userWins;
        int compWins;
        String again, roundResult, finalWinnerResult;

        Scanner inputReader = new Scanner(System.in);

        System.out.println("WELCOME TO ROCK, PAPER, SCISSORS!\n");
        //This loop allows the game to be repeated at the end until the loop is broken out of
        while (true) {
            tieCount = 0;
            userWins = 0;
            compWins = 0;

            //Ask for the user input
            System.out.println("How many rounds would you like to play? ");
            System.out.print("Enter a number between 1 and 10: ");
            rounds = Integer.parseInt(inputReader.nextLine());

            //check if it is between 1 and 10. 
            //If not, print error message and end the game. 
            if (!(rounds >= 1 && rounds <= 10)) {
                System.out.println("Error. That is not a valid option.");
                break;
            }

            //begin game
            for (int i = 0; i < rounds; i++) {
                //run method to collect user choice
                userChoice = receiveUserChoice();
                //run method to generate computer choice
                compChoice = genCompChoice();
                //run method to check who won and to report the winner
                whoWon = validity(userChoice, compChoice);
                //tracks the number of ties, user wins, and computer wins
                switch (whoWon) {
                    case 0:
                        tieCount++;
                        break;
                    case 1:
                        userWins++;
                        break;
                    case 2:
                        compWins++;
                        break;
                    default:
                        break;
                }
                //run method to determine round winner
                roundResult = whoWonMessage(whoWon);
                //display round winner on screen
                System.out.println("Result of this round: " + roundResult);
            }

            //display the overall game stats and final winner
            System.out.println("Here are the results... ");
            System.out.println("Number of Ties: " + tieCount);
            System.out.println("Number of User Wins: " + userWins);
            System.out.println("Number of Computer Wins: " + compWins);

            //run method to get overall winner of all rounds
            finalWinnerResult = finalWinner(tieCount, userWins, compWins);
            //display overall winner on screen
            System.out.println(">>>FINAL RESULT: " + finalWinnerResult);

            //ask user if they want to play the game again, if not quit the game.
            System.out.println("Would you like to play again? (y/n)");
            again = inputReader.nextLine();

            //if user does not want to play again, end the game using break;
            if (!(again.equals("y"))) {
                System.out.println("\nThanks for playing!");
                break;
            }

        }//end of play game while-loop
    }//end of main method

    /**
     * Method receiveUserChoice - This method asks the user to enter a number 1,
     * 2, or 3. If they enter a number not between these values, they are asked
     * to try again until a valid entry is received.
     *
     * @return {int} 1, 2, 3 for Rock, Paper, or Scissors, respectively
     */
    public static int receiveUserChoice() {
        Scanner inputReader = new Scanner(System.in);
        boolean isValid = false;
        int result;
        do {
            System.out.println("What would you like to play?");
            System.out.println("Enter 1 for Rock.");
            System.out.println("Enter 2 for Paper.");
            System.out.println("Enter 3 for Scissors.");
            result = Integer.parseInt(inputReader.nextLine());
            if (result == 1 || result == 2 || result == 3) {
                isValid = true;
            } else {
                System.out.println("\nPlease enter 1, 2, or 3.\n");
            }
        } while (!isValid);
        return result;
    }//end of receiveUserChoice method

    /**
     * Method genCompChoice - This method returns a number 1, 2, or 3 based on a
     * random number generator. This represents the computer's choice in the
     * game.
     *
     * @return {int} 1, 2, 3 for Rock, Paper, or Scissors, respectively
     */
    public static int genCompChoice() {
        Random rand = new Random();
        int result = rand.nextInt(3) + 1;
        //System.out.println(result);
        return result;
    }//end of genCompChoice method

    /**
     * Method validity - This method takes in the user input and the computer's
     * input and compares them to determine the winner of the round.
     *
     * @param user {int} 1, 2, 3 for Rock, Paper, or Scissors, respectively
     * @param comp {int} 1, 2, 3 for Rock, Paper, or Scissors, respectively
     * @return {int} 0 (tie), 1 (user wins), or 2(comp wins) that is sent to
     * another method to be interpreted to a string (whoWonMessage()).
     */
    public static int validity(int user, int comp) {
        int result = 4;
        if (user == comp) {
            result = 0;//tie
        } else if ((user == 1 && comp == 3) || (user == 2 && comp == 1) || (user == 3 && comp == 2)) {
            result = 1;//user wins
        } else if ((user == 1 && comp == 2) || (user == 2 && comp == 3) || (user == 3 && comp == 1)) {
            result = 2;//computer wins
        }
        return result;
    }//end of validity method

    /**
     * Method whoWonMessage - This method takes in an integer, 0, 1, or 2, and
     * interprets this as a "tie," a "user win," or a "computer win." It returns
     * a String that is then used to display to the user. It is used for both
     * "translating" the winner of the round as well as the winner of the
     * overall game.
     *
     * @param a {int} 0, 1, or 2
     * @return {String} tie, user wins, or comp wins
     */
    public static String whoWonMessage(int a) {
        String result = "";
        if (a == 0) {
            result = "Tie\n";
        } else if (a == 1) {
            result = "User Wins!\n";
        } else if (a == 2) {
            result = "Computer Wins!\n";
        }
        return result;
    }//end of whoWon method //change to switch statement, easier to maintain, and read

    /**
     * Method finalWinner - This method takes in the total number of ties after
     * all the rounds, the number of times the user won, the number of times the
     * computer won. It processes this and then sends a value, 0, 1, or 2, to
     * the whoWonMessage method. When the String is returned, this method also
     * turns the String into all capitals so that the Final Winner can look
     * different from the round winner. This method returns that string.
     *
     * @param ties {int} number of ties in the game
     * @param user {int} number of user wins in the game
     * @param comp {int} number of computer wins in the game
     * @return {String} Final Round Winner in ALL CAPS
     */
    public static String finalWinner(int ties, int user, int comp) {
        int result = 4;
        String winner;
        if (user > comp) {
            result = 1;
        } else if (comp > user) {
            result = 2;
        } else if (comp == user) {
            result = 0;
        }
        winner = whoWonMessage(result).toUpperCase();
        return winner;
    }//end of finalWinner method //can remove condition for last if else // can catch other situations

}//end of RockPaperScissors class
