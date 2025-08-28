public class Main {

    public static void main(String[] args) {
        /*
        int newScore = calculateScore("Max", 500);
        System.out.println("New Score is " + newScore);

        calculateScore(75);
        calculateScore();
        */

        System.out.println("New Score is " + calculateScore("Max", 500));
        System.out.println("New Score is " + calculateScore(10));

    }

    public static int calculateScore (String playerName, int score) {
        System.out.println("Player " + playerName + " scored " + score + " points");
        return score * 1000;
    }

    public static int calculateScore (int score) {
        return calculateScore ("Anonymous", score);
    }

    public static int calculateScore () {
        System.out.println("No player name, no player score");
        return 0;
    }

    /*
    kommt error
    public static void calculateScore (){
        System.out.println("No player name, no player score");
    }
    */
}
