public class Main {

    public static void main(String[] args) {

        System.out.println(sumDigits(1234));
        System.out.println(sumDigits(-125));
        System.out.println(sumDigits(4));
        System.out.println(sumDigits(1111111111));
        System.out.println(sumDigits(1));
        System.out.println(sumDigits(2));
        System.out.println(sumDigits(3));

    }

    public static int sumDigits(int number) {
        if (number < 0) {
            return -1;
        }

        int sum = 0;
        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }

}
