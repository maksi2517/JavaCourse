package notSolved;

public class MainInfinitLoop {
    public static void main(String[] args) {
        int i = 1;
        int j = 1;

        while(i <= j && j <= i && i != j) {
            System.out.println(".");
        }
    }
}
