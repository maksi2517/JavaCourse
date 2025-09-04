public class Main {

    public static void main(String[] args) {

        int range = 4;
        int sumEven = 0, sumOdd = 0;
        int count = 0;
        while(range <= 20) {
            range++;
            if (!isEvenNumber(range)) {
                sumOdd += range;
                continue;
            }

            if (count != 5) {
                //System.out.println("Even number " + range);
                sumEven += range;
                count++;
            }
        }

        System.out.println("Sum Even is " + sumEven);
        System.out.println("Sum Odd is " + sumOdd);

    }

    public static boolean isEvenNumber(int number) {
        return number % 2 == 0;
    }

}
