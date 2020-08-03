package com.skshazena.summativeassessment;

import java.util.Scanner;

/**
 * Healthy Hearts - This program takes a person's age and determines the maximum
 * heart rate in beats per minute. It also produces a target range for their
 * heart rate. All outputs are integers with no decimal places.
 *
 * @author Shazena
 * Apr 24, 2020
 */
public class HealthyHearts {

    public static void main(String[] args) {
        int age, maxHR, targetHRMinInt, targetHRMaxInt;

        Scanner inputReader = new Scanner(System.in);

        System.out.print("What is your age? ");
        age = inputReader.nextInt();

        maxHR = 220 - age;

        targetHRMinInt = (int) Math.round(0.50f * maxHR);
        targetHRMaxInt = (int) Math.round(0.85f * maxHR);

        System.out.println("Your maximum heart rate should be " + maxHR + " beats per minute");
        System.out.println("Your target HR Zone is " + targetHRMinInt + " - " + targetHRMaxInt + " beats per minute");

    }//inferring data integrity, restrict age

}
