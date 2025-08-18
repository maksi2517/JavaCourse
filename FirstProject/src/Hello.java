public class Hello {

    public static void main(String[] args) {
        System.out.println("Hello, Max");

        boolean isAlien = false;
        if (isAlien == false) {
            System.out.println("It is not an alien!");
            System.out.println("And I'm scared of aliens");
        }

        int topScore = 80;
        if (topScore >= 100) {
            System.out.println("You got the high score!");
        }

        int secondTopScore = 95;
        if ((topScore > secondTopScore) && (topScore < 100)) {
            System.out.println("Geater than second top score and less than 100");
        }

        if ((topScore > 90) || (secondTopScore <= 90)) {
            System.out.println("Either or both of the conditions are true");
        }

        int newValue = 50;
        if (newValue == 50) {
            System.out.println("This is true");
        }

        boolean isCar = false;
        if (!isCar) {
            System.out.println("Car is true");
        }

        String makeOfCar = "Volkswagen";
        boolean isDomestic = makeOfCar == "Volkswagen" ? false : true;

        if (isDomestic) {
            System.out.println("this car is domestic to our country");
        }

        int ageOfClient = 20;
        String ageText = ageOfClient >= 18 ? "Over 18" : "Under 18";
        System.out.println("Our client is " + ageText);

        String s = (isDomestic) ? "This car is domestic" : "This car is not domestic";

        System.out.println(s);


    }
}
