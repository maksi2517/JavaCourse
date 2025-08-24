public class MethodChallenge {

    public static void main(String[] args) {

        int highScorePosition = calculateHighScorePosition(1500);
        displayHighScoreScore("Max",highScorePosition);

        highScorePosition = calculateHighScorePosition(999);
        displayHighScoreScore("Bob",highScorePosition);

        highScorePosition = calculateHighScorePosition(499);
        displayHighScoreScore("Percy",highScorePosition);

        highScorePosition = calculateHighScorePosition(99);
        displayHighScoreScore("Gilbert",highScorePosition);

        highScorePosition = calculateHighScorePosition(-1000);
        displayHighScoreScore("James",highScorePosition);

    }

    public static void displayHighScoreScore(String playerName, int highScorePosition) {
        System.out.println(playerName + " managed to get into position " + highScorePosition + " on the high score list");
    }

    public static int calculateHighScorePosition (int playerScore) {
        int position = 4;
        if (playerScore >= 1000) {
            position = 1;
        } else if (playerScore >= 500) {
            position = 2;
        } else if (playerScore >= 100) {
            position = 3;
        }
        return position;

    }


}
