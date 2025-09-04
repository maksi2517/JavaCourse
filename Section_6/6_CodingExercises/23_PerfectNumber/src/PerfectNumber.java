public class PerfectNumber {

    public static void main(String[] args) {
        System.out.println(isPerfectNumber(1));
        System.out.println(isPerfectNumber(6));
        System.out.println(isPerfectNumber(28));
        System.out.println(isPerfectNumber(5));
        System.out.println(isPerfectNumber(9));
        System.out.println(isPerfectNumber(-1));
    }

    public static boolean isPerfectNumber(int num) {
        if (num < 1) {
            return false;
        }

        int sum = 0;
        for (int i = 1; i < num; i++) {
            if (num % i == 0) {
                sum += i;
            }
        }
        return sum == num;
    }

}
