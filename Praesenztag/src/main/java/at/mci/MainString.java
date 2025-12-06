package at.mci;

public class MainString {
    public static void main(String[] args) {
        //Leerzeichen wichtig! Ansonsten wird Operatorkette als eigenener Operator interpretier
        String myStrangeString = + 1 + - - + - - + + + + - 1 + " ";
        System.out.print(myStrangeString);
    }
}
