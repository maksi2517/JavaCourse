public class BarkingDog {
    public static boolean shouldWakeUp (boolean bark, int hourOfDay) {
        if (hourOfDay < 0 || hourOfDay > 23) {
            return false;
        }

        // NUR aufwachen wenn BEIDE Bedingungen erfüllt sind:
        // 1. Der Hund bellt (bark = true)
        // 2. Es ist die richtige Zeit (< 8 oder > 22)
        return bark && (hourOfDay < 8 || hourOfDay > 22);
    }

    public static void main(String[] args) {
        shouldWakeUp(true, 1);
        shouldWakeUp(true, 4);
        shouldWakeUp(false, 2);
        shouldWakeUp(true, 8);
        shouldWakeUp(true, -1);
    }
}
