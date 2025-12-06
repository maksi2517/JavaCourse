package notSolved;

public class MainShort {
    public static void main(String[] args) {
        short x = 0;
        int i = 100000;

        //Java nimmt bei numerischen Sachen immer automatisch int
        //x = x + i;

        short l = 0;
        int m = 100000;

        //Funktioniert, da:
        //E | op E2 => E1 := (E1.type) E1 + E2 hier wird implizit gecastet

        l += m;

        System.out.println(l);
    }
}
