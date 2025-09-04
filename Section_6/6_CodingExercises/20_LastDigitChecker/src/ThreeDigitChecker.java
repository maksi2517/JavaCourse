public class ThreeDigitChecker {
    public static void main(String[] args) {

        System.out.println(hasSameLastDigit(41, 22, 71));
        System.out.println(hasSameLastDigit(23, 32, 42));
        System.out.println(hasSameLastDigit(9, 99, 999));

        System.out.println(isValid(10));
        System.out.println(isValid(468));
        System.out.println(isValid(1051));

    }

    public static boolean hasSameLastDigit (int a, int b, int c) {
        if (!isValid(a) || !isValid(b) || !isValid(c)) {
            return false;
        }
        int nextA = a;

        while (nextA > 0) {
            int aCheck = nextA % 10;
            int nextB = b;

            while (nextB > 0) {
                int bCheck = nextB % 10;
                int nextC = c;

                while (nextC > 0) {
                    int cCheck = nextC % 10;
                    if (aCheck == bCheck || aCheck == cCheck || bCheck == cCheck) {
                        return true;
                    }
                    nextC /= 10;
                }
                nextB /= 10;
            }
            nextA /= 10;
        }
        return false;
    }


    public static boolean isValid (int a) {
        return a >= 10 && a <= 1000;
    }
}
