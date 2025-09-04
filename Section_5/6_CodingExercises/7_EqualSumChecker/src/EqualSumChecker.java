public class EqualSumChecker {
    public static boolean hasEqualSum(int a, int b, int c) {
        if (a + b == c) {
            return true;
        }
        return false;

        // or Method 2:
        // return a + b == c;
    }

    public static void main(String[] args) {
        System.out.println(hasEqualSum(1, 2, 3));
        System.out.println(hasEqualSum(1, 1, 1));
        System.out.println(hasEqualSum(1, 1, 2));
        System.out.println(hasEqualSum(1, -1, 0));
    }
}
