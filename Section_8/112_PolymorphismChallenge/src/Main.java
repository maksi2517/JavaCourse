public class Main {
    public static void main(String[] args) {

        Car car = new Car("2022 Blue Ferrari 296 GTS");
        runRace(car);

        Car ferari = new GasPoweredCar("2022 Blue Ferrari 296 GTS",
                            15.4, 6);
        runRace(ferari);

        Car tesla = new ElectricCar("2022 Red Tesla Model 3",
                            568, 75);
        runRace(tesla);

        Car ferraryHybrid = new HybridCar("2022 Black Ferrari SF90 Stradale",
                            16, 8, 8);
        runRace(ferraryHybrid);

    }

    public static void runRace(Car car) {
        car.startEngine();
        car.drive();
    }
}
