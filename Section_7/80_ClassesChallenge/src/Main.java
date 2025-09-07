public class Main {
    public static void main(String[] args) {

        BankAccount my = new BankAccount();
        my.setAccountNumber("AT 12 1234 2345 2344");
        my.setName("Maksim");
        my.setBalance(10111);
        my.setPhoneNumber("+43 677 6244 7177");
        my.setEmail("maksymgulenko@gmail.com");

        my.deposit(1000);
        my.withdraw(500);


        BankAccount anotherBA = new BankAccount();
        anotherBA.setAccountNumber("DE 12 1234 2345 2344");
        anotherBA.setName("Another");
        anotherBA.setBalance(5700);
        anotherBA.setPhoneNumber("+44 8389 7383 738");
        anotherBA.setEmail("info@firma.ch");

        anotherBA.deposit(3000);
        anotherBA.withdraw(2000);
        anotherBA.withdraw(10000);


    }
}
