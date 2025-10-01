public class Main {
    public static void main(String[] args) {

        Car car = new Car("2022 Blue Ferrari 296 GTS");
        runRace(car);

        Car ferari = new GasPoweredCar("2022 Blue Ferrari 296 GTS",
                            15.4, 6);
        runRace(ferari);
    }

    public static void runRace(Car car) {
        car.startEngine();
        car.drive();
    }
}
