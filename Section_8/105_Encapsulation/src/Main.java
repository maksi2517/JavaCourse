public class Main {

    public static void main(String[] args) {

//        Player player = new Player();
//        player.name = "Max";
//        player.health = 20;
//        player.weapon = "Sword";
//
//        int damage = 10;
//        player.loseHealth(damage);
//        System.out.println("Remaining health = " + player.healthRemaining());
//        player.health = 200;
//
//        player.loseHealth(11);
//        System.out.println("Remaining health = " + player.healthRemaining());


        EnhancedPlayer max = new EnhancedPlayer("Max", 200, "Sword");
        System.out.println("Initial health is " + max.healthRemaining());
    }
}
