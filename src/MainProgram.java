import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class MainProgram {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<BankAccount> accounts = new ArrayList<>();

        boolean running = true;

        while (running) {
            System.out.println("\nBank Menu");
            System.out.println("(1) Create account");
            System.out.println("(2) Deposit");
            System.out.println("(3) Withdraw");
            System.out.println("(4) Transfer");
            System.out.println("(5) Balances");
            System.out.println("(6) Save to file");
            System.out.println("(7) Exit");
            System.out.println();
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter initial balance: ");
                    double initial = scanner.nextDouble();
                    accounts.add(new BankAccount(initial));
                    System.out.println("Account #" + accounts.size() + " created.");
                    break;
                case 2:
                    System.out.print("Enter account #: ");
                    int id = scanner.nextInt();
                    if (id > 0 && id <= accounts.size()) {
                        System.out.print("Enter amount to deposit: ");
                        double amount = scanner.nextDouble();
                        accounts.get(id-1).Deposit(amount);
                    } else {
                        System.out.println("Invalid account #");
                    }
                    break;
                case 3:
                    System.out.print("Enter account #: ");
                    int withdrawId = scanner.nextInt();
                    if (withdrawId > 0 && withdrawId <= accounts.size()) {
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        accounts.get(withdrawId-1).Withdraw(withdrawAmount);

                    } else {
                        System.out.println("Invalid account #");
                    }
                    break;

                case 4:
                    System.out.print("Enter source account #: ");
                    int sourceId = scanner.nextInt();
                    System.out.print("Enter destination account #: ");
                    int destinationId = scanner.nextInt();
                    if (sourceId > 0 && sourceId <= accounts.size() && destinationId > 0 && destinationId <= accounts.size()) {
                        System.out.print("Enter amount to transfer: ");
                        double transAmount = scanner.nextDouble();
                        accounts.get(sourceId-1).TransferToAnother(accounts.get(destinationId-1), transAmount);

                    } else {
                        System.out.println("Invalid account #");
                    }

                    break;
                case 5:
                    if (accounts.isEmpty()) System.out.println("No accounts to show.");
                    for (int i = 0; i < accounts.size(); i++) {
                        System.out.print("Account #" + (i + 1) +  ": ");
                        accounts.get(i).PrintBalance();
                    }
                    break;
                case 6:
                    File file = new File("output.csv");
                    try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
                        out.println("AccountID,Balance");
                        for (int i = 0; i < accounts.size(); i++) {
                            out.println((i + 1) + "," + accounts.get(i).getBalance());
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 7:
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
