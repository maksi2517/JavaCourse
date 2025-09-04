public class LeapYearComments {
    public static boolean isLeapYear(int year) {
        // Eingabe validieren: Jahr muss zwischen 1 und 9999 liegen
        if (year < 1 || year > 9999) {
            return false;
        }

        // STEP 1: Ist das Jahr durch 4 teilbar?
        if (year % 4 == 0) {
            // JA, durch 4 teilbar → Gehe zu Step 2

            // STEP 2: Ist das Jahr durch 100 teilbar?
            if (year % 100 == 0) {
                // JA, durch 100 teilbar → Gehe zu Step 3

                // STEP 3: Ist das Jahr durch 400 teilbar?
                if (year % 400 == 0) {
                    // JA, durch 400 teilbar → Gehe zu Step 4
                    return true;  // STEP 4: Das Jahr IST ein Schaltjahr
                } else {
                    // NEIN, nicht durch 400 teilbar → Gehe zu Step 5
                    return false; // STEP 5: Das Jahr ist KEIN Schaltjahr
                }
            } else {
                // NEIN, nicht durch 100 teilbar → Gehe zu Step 4
                return true;  // STEP 4: Das Jahr IST ein Schaltjahr
            }
        } else {
            // NEIN, nicht durch 4 teilbar → Gehe zu Step 5
            return false; // STEP 5: Das Jahr ist KEIN Schaltjahr
        }
    }

    public static void main(String[] args) {
        System.out.println(isLeapYear(1900));
        System.out.println(isLeapYear(2000));
        System.out.println(isLeapYear(-2001));
        System.out.println(isLeapYear(2024));
        System.out.println(isLeapYear(2025));
        System.out.println(isLeapYear(2026));
    }

}
