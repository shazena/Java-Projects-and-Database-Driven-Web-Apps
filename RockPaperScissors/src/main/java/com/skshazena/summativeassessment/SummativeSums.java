package com.skshazena.summativeassessment;

/**
 * Summative Sums - This program lists three arrays and has a method that is
 * used to find the sum of the elements in those arrays
 *
 * @author Shazena
 * Apr 24, 2020
 */
public class SummativeSums {

    public static void main(String[] args) {
        int[] array1 = {1, 90, -33, -55, 67, -16, 28, -55, 15};
        int[] array2 = {999, -60, -77, 14, 160, 301};
        int[] array3 = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130,
            140, 150, 160, 170, 180, 190, 200, -99};
        
        int sumOfArray1 = sumArrayElements(array1);
        int sumOfArray2 = sumArrayElements(array2);
        int sumOfArray3 = sumArrayElements(array3);
        
        System.out.println("#1 Array Sum: " + sumOfArray1);
        System.out.println("#2 Array Sum: " + sumOfArray2);
        System.out.println("#3 Array Sum: " + sumOfArray3);
    }

    /**
     * Method sumArrayElements - This method takes in an array of integers and
     * then returns the sum of all these integers.
     *
     * @param array {int[]} array of integers
     * @return {int} sum of the elements in the array
     */
    public static int sumArrayElements(int[] array) {
        int sum = 0;
        
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        
        return sum;
    }
}
