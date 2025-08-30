public class Main {

    public static void main(String[] args) {

        char charValue = 'E';
        switch (charValue) {
            case 'A':
                System.out.println("Letter '" + charValue + "' is a NATO word: Able");
                break;
            case 'B':
                System.out.println("Letter '" + charValue + "' is a NATO word: Baker");
                break;
            case 'C':
                System.out.println("Letter '" + charValue + "' is a NATO word: Charlie");
                break;
            case 'D':
                System.out.println("Letter '" + charValue + "' is a NATO word: Dog");
                break;
            case 'E':
                System.out.println("Letter '" + charValue + "' is a NATO word: Easy");
                break;
            default:
                System.out.println("Letter '" + charValue + "' was not found in the switch.)");

        }
    }

}
