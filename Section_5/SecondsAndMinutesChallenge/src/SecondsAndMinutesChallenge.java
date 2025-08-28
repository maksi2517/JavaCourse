/*
* my solution
* */

public class SecondsAndMinutesChallenge {

    public static void main(String[] args) {

        getDurationString(5867);
        getDurationString(-5867);
        getDurationString(-65, 23);
        getDurationString(65, 123);

    }

    public static void getDurationString (int seconds) {
        if (!(seconds >= 0)) {
            System.out.println("Invalid input Seconds");
            return;
        }

        int minutes = seconds / 60;
        seconds = seconds - (minutes * 60);

        getDurationString(minutes, seconds);

    }

    public static void getDurationString (int minutes, int seconds) {
        if (!(minutes >= 0)) {
            System.out.println("Invalid input Minutes");
            return;
        }
        if (!(seconds >= 0 && seconds < 60)) {
            System.out.println("Invalid input Seconds");
            return;
        }

        int hours = minutes / 60;
        minutes = minutes - (hours * 60);

        System.out.println(hours + "h " + minutes + "m " + seconds + "s");

    }
}
