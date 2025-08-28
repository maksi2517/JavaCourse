public class CleanMethod {
    public static void main(String[] args) {
        System.out.println(getDurationString(-3945));
        System.out.println(getDurationString(-65, 45));
        System.out.println(getDurationString(65, 145));
        System.out.println(getDurationString(65, 45));
        System.out.println(getDurationString(3945));
    }

    public static String getDurationString (int seconds) {

        if (seconds < 0) {
            return "Invalid data of seconds (" + seconds + "), must be a positive integer value";
        }

        int minutes = seconds / 60;
        seconds %= 60;
        return getDurationString(minutes, seconds);
    }

    public static String getDurationString (int minutes, int seconds) {
        if (minutes < 0) {
            return "Invalid data of seconds (" + minutes + "), must be a positive integer value";
        }

        if (seconds < 0 || seconds > 59) {
            return "Invalid data of seconds (" + seconds + "), must be a positive integer value";
        }

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
