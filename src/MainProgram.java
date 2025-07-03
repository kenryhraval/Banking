import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class MainProgram {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountManager manager = new AccountManager();

        boolean running = true;

        while (running) {
            System.out.println("\nBank Menu");
            System.out.println("(1) Create account");
            System.out.println("(2) Deposit");
            System.out.println("(3) Withdraw");
            System.out.println("(4) Transfer");
            System.out.println("(5) Balances");
            System.out.println("(6) Save accounts");
            System.out.println("(7) Create report (CSV)");
            System.out.println("(8) Exit");
            System.out.println();
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter initial balance: ");
                    double initial = scanner.nextDouble();
                    manager.addAccount(new BankAccount(initial));
                    System.out.println("Account #" + manager.getSize() + " created.");
                    break;
                case 2:
                    System.out.print("Enter account #: ");
                    int id = scanner.nextInt();

                    if (id > 0 && id <= manager.getSize()) {
                        System.out.print("Enter amount to deposit: ");
                        double amount = scanner.nextDouble();
                        manager.getAccount(id-1).Deposit(amount);
                    } else {
                        System.out.println("Invalid account #");
                    }
                    break;
                case 3:
                    System.out.print("Enter account #: ");
                    int withdrawId = scanner.nextInt();
                    if (withdrawId > 0 && withdrawId <= manager.getSize()) {
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        manager.getAccount(withdrawId-1).Withdraw(withdrawAmount);

                    } else {
                        System.out.println("Invalid account #");
                    }
                    break;

                case 4:
                    System.out.print("Enter source account #: ");
                    int sourceId = scanner.nextInt();
                    System.out.print("Enter destination account #: ");
                    int destinationId = scanner.nextInt();
                    if (sourceId > 0 && sourceId <= manager.getSize() && destinationId > 0 && destinationId <= manager.getSize()) {
                        System.out.print("Enter amount to transfer: ");
                        double transAmount = scanner.nextDouble();
                        manager.getAccount(sourceId-1).TransferToAnother(manager.getAccount(destinationId-1), transAmount);

                    } else {
                        System.out.println("Invalid account #");
                    }

                    break;
                case 5:
                    if (manager.getSize() == 0) System.out.println("No accounts to show.");
                    for (int i = 0; i < manager.getSize(); i++) {
                        System.out.print("Account #" + (i + 1) +  ": ");
                        manager.getAccount(i).PrintBalance();
                    }
                    break;
                case 6:
                    try {
                        manager.saveAccounts();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 7:
                    System.out.print("Enter report filename: ");
                    String filename = scanner.nextLine();
                    try {
                        manager.exportReportCsv(filename);
                        System.out.println("Report exported successfully.");
                    } catch (Exception e) {
                        System.out.println("Error exporting report: " + e.getMessage());
                    }
                    break;


                case 8:
                    running = false;
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}
