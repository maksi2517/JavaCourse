import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int currentYear = 2025;

        try {
            System.out.println(getInputFromConsole(currentYear));
        } catch (NullPointerException e) {
            System.out.println(getInputFromScanner(currentYear));
        }


    }

    public static String getInputFromConsole (int currentYear){

        String name = System.console().readLine("Enter your name: ");
        System.out.println("Hi " + name + ", Thanks for smth");

        String dateOfBirth = System.console().readLine("Enter your birth year: ");
        int age = currentYear - Integer.parseInt(dateOfBirth);

        return "So you're " + age + " years old!";
    }

    public static String getInputFromScanner (int currentYear){

        Scanner scanner = new Scanner(System.in);

//        String name = System.console().readLine("Enter your name: ");
        System.out.println("Hi, what's your name?");
        String name = scanner.nextLine();

        System.out.println("Hi, " + name + ", Thanks for smth");

//        String dateOfBirth = System.console().readLine("Enter your birth year: ");
        System.out.println("What year were you burn?");

        boolean valodDOB = false;
        int age = 0;
        do {
            System.out.println("Enter a year of birth >= " +
                    (currentYear - 125) + " and <= " + currentYear);
            try {
                age = checkDate(currentYear, scanner.nextLine());
                valodDOB = age < 0 ? false : true;
            } catch (NumberFormatException badUserData) {
                System.out.println("Characters nor allowed!!! try again");
            }
        } while (!valodDOB);

        return "So you're " + age + " years old!";
    }

    public static int checkDate (int currentYear, String dateOfBirth){

        int dob = Integer.parseInt(dateOfBirth);
        int minimumYear = currentYear - 125;

        if ((dob < minimumYear) || (dob > currentYear)){
            return -1;
        }

        return (currentYear - dob);
    }



}
