package dev.lpa;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int count = readIntegers();
        System.out.println("Count is: " + count);
        System.out.println("=".repeat(20));

        int[] array = readElements(count);
        System.out.println(Arrays.toString(array));
        System.out.println("=".repeat(20));


        int min = findMin(array);
        System.out.println("Minimum element is: " + min);
        System.out.println("=".repeat(20));


    }

    private static int readIntegers() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a elements number: ");
        int count = scanner.nextInt();

        return count;
    }

    private static int[] readElements(int count) {
        Scanner scanner = new Scanner(System.in);
        int[] elements = new int[count];
        System.out.println("Enter " + count + " elements: ");
        for (int i = 0; i < count; i++) {
            elements[i] = scanner.nextInt();
        }
        return elements;
    }

    private static int findMin(int[] array) {

        int min = array[0];
        for (int el : array) {
            if (el < min) {
                min = el;
            }
        }
        return min;
    }



}
