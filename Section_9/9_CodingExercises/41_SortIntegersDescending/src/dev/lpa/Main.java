package dev.lpa;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int[] myIntegers = getIntegers(5);
        System.out.println(Arrays.toString(myIntegers));
        int[] sorted = sortIntegers(myIntegers);
        System.out.println(Arrays.toString(sorted));
        printArray(sorted);


    }

    public static int[] getIntegers(int size) {
        Scanner scanner = new Scanner(System.in);
        int[] values = new int[size];
        for (int i = 0; i < size; i++) {
            values[i] = scanner.nextInt();
        }
        return values;
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println("Element " + i + " contents " + array[i]);
        }
    }

    public static int[] sortIntegers(int[] array) {
        int[] sorted = new int[array.length];

        // copy
        for (int i = 0; i < array.length; i++) {
            sorted[i] = array[i];
        }

        // bubble sort in descending order
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < sorted.length - 1; i++) {
                if (sorted[i] < sorted[i + 1]) {
                    int temp = sorted[i];
                    sorted[i] = sorted[i + 1];
                    sorted[i + 1] = temp;
                    swapped = true;
                }
            }
        } while (swapped);

        return sorted;
    }


}
