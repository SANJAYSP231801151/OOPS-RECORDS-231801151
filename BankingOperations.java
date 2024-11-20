import java.sql.*;
//import java.util.Scanner;

public class BankingOperations {
    private Connection connection;

    public BankingOperations() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void registerUser(String name, String accountNumber, String password) {
        try {
            String query = "INSERT INTO Users (Name, AccountNumber, Password) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, accountNumber);
            stmt.setString(3, password);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("User registered successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error in registration: " + e.getMessage());
        }
    }

    public void deposit(String accountNumber, double amount) {
        try {
            String updateBalance = "UPDATE Users SET Balance = Balance + ? WHERE AccountNumber = ?";
            PreparedStatement stmt = connection.prepareStatement(updateBalance);
            stmt.setDouble(1, amount);
            stmt.setString(2, accountNumber);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                String logTransaction = "INSERT INTO Transactions (AccountNumber, TransactionType, Amount) VALUES (?, 'Deposit', ?)";
                PreparedStatement logStmt = connection.prepareStatement(logTransaction);
                logStmt.setString(1, accountNumber);
                logStmt.setDouble(2, amount);
                logStmt.executeUpdate();

                System.out.println("Deposit successful!");
            } else {
                System.out.println("Account not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error in deposit: " + e.getMessage());
        }
    }

    public void withdraw(String accountNumber, double amount) {
        try {
            String checkBalance = "SELECT Balance FROM Users WHERE AccountNumber = ?";
            PreparedStatement balanceStmt = connection.prepareStatement(checkBalance);
            balanceStmt.setString(1, accountNumber);
            ResultSet rs = balanceStmt.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble("Balance");
                if (currentBalance >= amount) {
                    String updateBalance = "UPDATE Users SET Balance = Balance - ? WHERE AccountNumber = ?";
                    PreparedStatement stmt = connection.prepareStatement(updateBalance);
                    stmt.setDouble(1, amount);
                    stmt.setString(2, accountNumber);
                    stmt.executeUpdate();

                    String logTransaction = "INSERT INTO Transactions (AccountNumber, TransactionType, Amount) VALUES (?, 'Withdraw', ?)";
                    PreparedStatement logStmt = connection.prepareStatement(logTransaction);
                    logStmt.setString(1, accountNumber);
                    logStmt.setDouble(2, amount);
                    logStmt.executeUpdate();

                    System.out.println("Withdrawal successful!");
                } else {
                    System.out.println("Insufficient balance!");
                }
            } else {
                System.out.println("Account not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error in withdrawal: " + e.getMessage());
        }
    }

    public void checkBalance(String accountNumber) {
        try {
            String query = "SELECT Balance FROM Users WHERE AccountNumber = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Your current balance is: " + rs.getDouble("Balance"));
            } else {
                System.out.println("Account not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error in checking balance: " + e.getMessage());
        }
    }
}