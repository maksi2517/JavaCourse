public class Main {

    public static void main(String[] args) {

        int range = 4;
        while(range <= 20) {
            range++;
            if (!isEvenNumber(range)) {
                continue;
            }
            System.out.println(range);
        }

    }

    public static boolean isEvenNumber(int number) {
        return number % 2 == 0;
    }

}
