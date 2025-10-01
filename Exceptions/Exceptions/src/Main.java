import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Eine Exception ist ein Fehler, der während der Laufzeit eines Programms auftritt.

/*
        int i = 0;
        System.out.println(5 / i);
*/

/*
        Random random = null;
        System.out.println((random.nextInt(10)));
*/


/*

        int[] arr = {1, 23, 576, 83, 91479};

        for (int i = 0; i < 7; i++) {
            System.out.println(arr[i]);
        }
*/

/*

        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        System.out.println(number);
*/

/*

        Scanner scanner = new Scanner(System.in);
        try {
            int number = scanner.nextInt();
            System.out.println(number);
        } catch (InputMismatchException exception) {
            System.out.println("Bitte eine Zahl eingeben!");
        } finally {  // finally wird immer ausgeführt, egal ob eine Exception aufgetreten ist oder nicht
            scanner.close();
            System.out.println("Scanner geschlossen");
        }
*/

        ExceptionThrower exceptionThrower = new ExceptionThrower();
        try {
            exceptionThrower.divide();
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Falsche Eingabe, bitte eine Zahl eingeben!");
        } catch (Exception e) {
            System.out.println("Ein Fehler ist aufgetreten: ");
        }




    }
}
