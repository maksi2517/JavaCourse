public class Main {
    public static void main(String[] args) {

        Employee tim = new Employee("Maksim", "16/08/1994", "01/01/2020");
        System.out.println(tim);
        System.out.println("Age = " +tim.getAge());
        System.out.println("Pay = " + tim.collectPay());

        Employee joe = new Employee("Joe", "19/08/1990", "03/03/2020");
        System.out.println(joe);

    }


}
