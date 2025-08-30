public class NumberOfDaysInMonth {

    public static void main(String[] args) {

        System.out.println(isLeapYear(-1600));
        System.out.println(isLeapYear(1600));
        System.out.println(isLeapYear(2017));
        System.out.println(isLeapYear(2000));

        System.out.println(getDaysInMonth(1, 2020));
        System.out.println(getDaysInMonth(2, 2020));
        System.out.println(getDaysInMonth(2, 2018));
        System.out.println(getDaysInMonth(-1, 2020));
        System.out.println(getDaysInMonth(1, -2020));

    }

    public static boolean isLeapYear(int year) {

        if (year < 1 || year > 9999) {
            return false;
        }

        return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);

    }

    public static int getDaysInMonth (int month, int year) {
        if (month < 1 || month > 12) {
            return -1;
        }

        if (year < 1 || year > 9999) {
            return -1;
        }

        switch (month) {
            case 1 -> { return 31; }
            case 2 -> {
                if (isLeapYear(year)) { return 29; }
                return 28;
            }
            case 3, 8, 5, 7, 10, 12 -> { return 31; }
            case 4, 6, 9, 11 -> { return 30; }
            default -> { return -1; }
        }

    }

}
