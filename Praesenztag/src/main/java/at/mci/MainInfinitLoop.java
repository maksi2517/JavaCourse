package at.mci;

public class MainInfinitLoop {
    public static void main(String[] args) {
        Integer i = new Integer(1);
        Integer j = new Integer(1);

        while(i <= j && j <= i && i != j) {
            System.out.println(".");
        }
    }
}
