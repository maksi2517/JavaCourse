package solved;

public class MainByte {
    public static void main(String[] args) {
        System.out.println((byte) -1);
        System.out.println((char) (byte) -1);   //char sind unsigned Integer in Java
        //In Java sind alle numerischen Datentypen signed, außer char
        System.out.println((int) (char) (byte) -1);
        System.out.println((int) (char) -1);
    }
}
