public class Main {
    public static void main(String[] args) {
        System.out.println(convertToCentimeters(5, 8));
    }

    public static double convertToCentimeters (int heightInches) {
        return heightInches * 2.54;
    }

    public static double convertToCentimeters (int heightFeet, int remainingInches) {
        int heightInInches = (heightFeet * 12) + remainingInches;
        System.out.println(heightInInches);
        return convertToCentimeters(heightInInches);
    }



}
