import java.util.Scanner;

public class InputCalculator {
    public static void main(String[] args) {
        inputThenPrintSumAndAverage();
    }

    public static void inputThenPrintSumAndAverage () {
        Scanner scanner = new Scanner(System.in);

        int sum = 0;
        int count = 0;

        // Read ints until something that's not an int is entered
        while (scanner.hasNextInt()) {
            sum += scanner.nextDouble();
            count++;
        }

        long average = (count == 0) ? 0L : Math.round((double) sum / count);

        System.out.println("SUM = " + sum + " AVG = " + average);

        // Optional: don't close scanner if this is part of a larger app using System.in
        // scanner.close();
    }
}
