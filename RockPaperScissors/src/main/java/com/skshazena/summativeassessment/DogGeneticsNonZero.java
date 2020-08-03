package com.skshazena.summativeassessment;

import java.util.Random;
import java.util.Scanner;

/**
 * Dog Genetics - This program creates a random "DNA" test for your dog.
 *
 * @author Shazena
 * Apr 24, 2020 Edits made on Apr 26, 2020
 */
public class DogGeneticsNonZero {

    public static void main(String[] args) {
        String name;
        int[] probs;
        probs = new int[5];
        int complete = 100, placeholder = 0, offset;

        Scanner inputReader = new Scanner(System.in);
        Random rand = new Random();

        System.out.print("What is your dog's name? ");
        name = inputReader.nextLine();

        for (int i = 0; i < probs.length - 1; i++) {
            do {
                offset = rand.nextInt(complete);
                placeholder = rand.nextInt(complete - offset);
                probs[i] = placeholder;
                System.out.println("This is the value " + i + " " + probs[i]);
            } while (probs[i] == 0);
            complete -= placeholder;
        }

        //This last value in the array gets assigned the remainder so that the 
        //total always adds to 100%
        probs[probs.length - 1] = complete;

        System.out.println("Well then, I have this highly reliable report on "
                + name + "'s prestigious background right here. \n");
        System.out.println(name + " is: \n");
        System.out.println(probs[0] + "% St. Bernard");
        System.out.println(probs[1] + "% Chihuahua");
        System.out.println(probs[2] + "% Dramatic RedNosed Asian Pug");
        System.out.println(probs[3] + "% Common Cur");
        System.out.println(probs[4] + "% King Doberman");

        System.out.println("\n Wow, that's QUITE the dog!");

    }
}
