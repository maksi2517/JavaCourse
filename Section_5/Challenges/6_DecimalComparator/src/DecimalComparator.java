public class DecimalComparator {
    public static boolean areEqualByThreeDecimalPlaces (double number1, double number2) {
        long number1Rounded = (long) (number1 * 1000);
        long number2Rounded = (long) (number2 * 1000);

        return number1Rounded == number2Rounded;
    }

    public static void main(String[] args) {
        System.out.println(areEqualByThreeDecimalPlaces(-3.1756, -3.175));
        System.out.println(areEqualByThreeDecimalPlaces(3.175, 3.176));
        System.out.println(areEqualByThreeDecimalPlaces(3.0, 3.0));
        System.out.println(areEqualByThreeDecimalPlaces(-3.123, 3.123));
        System.out.println(areEqualByThreeDecimalPlaces(55555553333.13, 55555553333.123));
    }
}
