package notSolved;

public class MainComparision {
    public static void main(String[] args) {
        //Unterschiedliche Objekte
        /*
        Integer a, b;
        a=129;
        b=129;
        */

        //Hier ist es das selbe Objekt, da die Zahl in einem Byte Platz hat
        Integer a, b;
        a=29;
        b=29;

        System.out.println(a==b);   //Adressen der Objekte werden verglichen
    }
}
