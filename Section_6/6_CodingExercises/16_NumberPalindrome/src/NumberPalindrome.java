public class NumberPalindrome {
    public static void main(String[] args) {

        System.out.println(isPalindrome(-1221));
        System.out.println(isPalindrome(707));
        System.out.println(isPalindrome(11212));

    }

    public static boolean isPalindrome(int num) {
        int lastDigit = 0;
        int reverseNumber = 0;
        int x = num;
        while (x != 0) {
            lastDigit = x % 10;
            reverseNumber = reverseNumber * 10 + lastDigit;
            x = x / 10;
        }
        return num == reverseNumber;
    }


}
