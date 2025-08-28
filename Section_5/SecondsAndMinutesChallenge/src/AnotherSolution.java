public class AnotherSolution {
    public static void main(String[] args) {
        System.out.println(getDurationString(3945));
        System.out.println(getDurationString(65, 45));
    }

    public static String getDurationString (int seconds) {
        int minutes = seconds / 60;
        return getDurationString(minutes, seconds);
    }

    public static String getDurationString (int minutes, int seconds) {

        int hours = minutes / 60;
        // System.out.println("hours = " + hours);

        int remainingMinutes = minutes % 60;
        // System.out.println("minutes = " + minutes);
        // System.out.println("remainingMinutes = " + remainingMinutes);

        int remainingSeconds = seconds % 60;
        // System.out.println("seconds = " + seconds);

        return hours + "h " + remainingMinutes + "m " + remainingSeconds + "s";

    }

}
