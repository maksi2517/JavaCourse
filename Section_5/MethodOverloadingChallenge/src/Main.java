public class Main {
    public static void main(String[] args) {

        System.out.println(convertToCentimeters(5, 8));
        System.out.println(convertToCentimeters(69));
    }

    public static double convertToCentimeters (int heightInches) {
        return heightInches * 2.54;
    }

    public static double convertToCentimeters (int heightFeet, int remainingInches) {
        // return ((heightFeet * 12) + remainingInches) * 2.54;
        // return convertToCentimeters((heightFeet * 12) + remainingInches);

        int feetToInches = heightFeet * 12;
        int totalInches = feetToInches + remainingInches;
        double result = convertToCentimeters(totalInches);
        System.out.println(heightFeet + "ft and " + remainingInches + " in = " + result + " cm");
        return result;
    }



}
