public class NumberToWords {
    public static void main(String[] args) {
        System.out.println(getDigitCount(0));
        System.out.println(getDigitCount(123));
        System.out.println(getDigitCount(-12));
        System.out.println(getDigitCount(5200));
        System.out.println("-----------------");
        System.out.println(reverse(-121));
        System.out.println(reverse(1212));
        System.out.println(reverse(1234));
        System.out.println(reverse(100));
        System.out.println("-----------------");
        numberToWords(-121);
        numberToWords(1212);
        numberToWords(1234);
        numberToWords(1000);

    }

    public static void numberToWords (int n) {
        if (n < 0) {
            System.out.println("Invalid Value");
            return;
        }

        if (n == 0) {
            System.out.println("Zero");
            return;
        }

        int originalDigits = getDigitCount(n);
        int reversed = reverse(n);
        int reversedDigits = getDigitCount(reversed);

        int temp = reversed;
        while (temp != 0) {
            int digit = temp % 10;
            switch (digit) {
                case 0 -> System.out.print("Zero ");
                case 1 -> System.out.print("One ");
                case 2 -> System.out.print("Two ");
                case 3 -> System.out.print("Three ");
                case 4 -> System.out.print("Four ");
                case 5 -> System.out.print("Five ");
                case 6 -> System.out.print("Six ");
                case 7 -> System.out.print("Seven ");
                case 8 -> System.out.print("Eight ");
                case 9 -> System.out.print("Nine ");
                default -> System.out.println("Invalid Value");
            }
            temp /= 10;
        }

        for (int i = 0; i < originalDigits - reversedDigits; i++) {
            System.out.print("Zero ");
        }

        System.out.println(" ");
    }

    public static int reverse(int x) {
        int result = 0;
        while (x != 0) {
            result = result * 10 + x % 10;
            x /= 10;
        }
        return result;
    }

    public static int getDigitCount(int n) {
        if (n < 0) {
            return -1;
        }
        if (n == 0) {
            return 1;
        }
        int count = 0;
        while (n != 0) {
            count++;
            n /= 10;
        }
        return count;
    }

}
