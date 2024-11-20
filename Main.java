import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
            Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Failed to establish a database connection!");
        } else {
            System.out.println("Connected to the database successfully!");
        }
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        BankingOperations banking = new BankingOperations();
        while (true) {
            System.out.println("\n--- Banking System ---");
            System.out.println("1. Register");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter account number: ");
                    String accountNumber = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    banking.registerUser(name, accountNumber, password);
                    break;
                case 2:
                    System.out.print("Enter account number: ");
                    String depositAccount = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    double depositAmount = scanner.nextDouble();
                    banking.deposit(depositAccount, depositAmount);
                    break;
                case 3:
                    System.out.print("Enter account number: ");
                    String withdrawAccount = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    banking.withdraw(withdrawAccount, withdrawAmount);
                    break;
                case 4:
                    System.out.print("Enter account number: ");
                    String balanceAccount = scanner.nextLine();
                    banking.checkBalance(balanceAccount);
                    break;
                case 5:
                    System.out.println("Thank you for using the Banking System!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again!");
            }
        }
    }
}
