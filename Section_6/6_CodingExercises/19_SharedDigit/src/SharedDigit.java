public class SharedDigit {

    public static void main(String[] args) {
        System.out.println(hasSharedDigit(12, 23));
        System.out.println(hasSharedDigit(9, 99));
        System.out.println(hasSharedDigit(15, 55));
        System.out.println(hasSharedDigit(12, 43));
    }

    public static boolean hasSharedDigit(int a, int b) {
        if (a <= 10 || a >= 100 || b <= 10 || b >= 100) {
            return false;
        }

        int nextA = a;

        while (nextA > 0) {
            int aCheck = nextA % 10;
            int nextB = b;

            while (nextB > 0) {
                int bCheck = nextB % 10;
                if (aCheck == bCheck) {
                    return true;
                }
                nextB /= 10;
            }
            nextA /= 10;
        }
        return false;
    }

}
