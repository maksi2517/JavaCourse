public class Challenge {
    public static void main(String[] args) {
        double x = 20.00d;
        double y = 80.00d;
        double sum = (x + y) * 100;
        System.out.println("Summe = " + sum);
        double theRemainder = sum % 40.00d;
        System.out.println("Remainder = " + theRemainder);
        boolean isNoReminder = (theRemainder == 0.00d) ? true : false;
        System.out.println("isNoReminder = " + isNoReminder);
        if (!isNoReminder) {
            System.out.println("got some reminder");
        }
    }
}
