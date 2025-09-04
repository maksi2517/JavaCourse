public class SpeedConverter {

    public static long toMilesPerHour (double kilometersPerHour) {
        if (kilometersPerHour < 0) {
            return -1;
        }

        double milesPerHour = kilometersPerHour / 1.609;

        return Math.round(milesPerHour);

    }

    public static void printConversion (double kilometersPerHour) {
        if (kilometersPerHour < 0) {
            System.out.println("Invalid Value");
            return;
        }

        long milesPerHour = toMilesPerHour(kilometersPerHour);

        System.out.println(kilometersPerHour + " km/h = " + milesPerHour + " mi/h");
    }

    public static void main(String[] args) {
        // Test der toMilesPerHour Methode
        System.out.println("=== Testing toMilesPerHour ===");
        System.out.println("toMilesPerHour(1.5): " + SpeedConverter.toMilesPerHour(1.5));
        System.out.println("toMilesPerHour(10.25): " + SpeedConverter.toMilesPerHour(10.25));
        System.out.println("toMilesPerHour(-5.6): " + SpeedConverter.toMilesPerHour(-5.6));
        System.out.println("toMilesPerHour(25.42): " + SpeedConverter.toMilesPerHour(25.42));
        System.out.println("toMilesPerHour(75.114): " + SpeedConverter.toMilesPerHour(75.114));

        System.out.println("\n=== Testing printConversion ===");
        SpeedConverter.printConversion(1.5);
        SpeedConverter.printConversion(10.25);
        SpeedConverter.printConversion(-5.6);
        SpeedConverter.printConversion(25.42);
        SpeedConverter.printConversion(75.114);
    }
}
