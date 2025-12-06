package at.mci;

/**
 * @author andrea
 * @version 1.0
 * @see c:\andrea\programming\\units\java\Test.java  //Blöder Fehler mit \\u, wenn man nur mit einem Backslash schreibt
 */

public class MainHelloWorld {
    public static void main(String[] args) {
        System.out.print("Hell");
        System.out.println("o world");
        System.out.println("\u3070");   //Print Unicode Charackter
    }
}
