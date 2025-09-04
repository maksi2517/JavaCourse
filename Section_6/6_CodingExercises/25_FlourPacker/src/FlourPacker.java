public class FlourPacker {
    public static void main(String[] args) {
        System.out.println(canPack(1, 0, 4));
        System.out.println(canPack(1, 0, 5));
        System.out.println(canPack(0, 5, 5));
        System.out.println(canPack(2, 2, 11));
        System.out.println(canPack(-3, 2, 12));
    }

    public static boolean canPack (int bigCount, int smallCount, int goal) {
        if (bigCount < 0 || smallCount < 0 || goal < 0) {
            return false;
        }

        if (((bigCount * 5) + smallCount) < goal) {
            return false;
        }

        int maxBigBagsWeCanUse = goal / 5;
        int bigBagsToUse;

        if (bigCount >= maxBigBagsWeCanUse) {
            bigBagsToUse = maxBigBagsWeCanUse;
        } else {
            bigBagsToUse = bigCount;
        }

        int remainder = goal - (bigBagsToUse * 5);

        return smallCount >= remainder;
    }
}
