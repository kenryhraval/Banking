import java.util.ArrayList;
import java.util.Scanner;

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
            System.out.println("(6) Exit");
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
                        accounts.get(id).Deposit(amount);
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
                        accounts.get(withdrawId).Withdraw(withdrawAmount);

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
                        accounts.get(sourceId).TransferToAnother(accounts.get(destinationId), transAmount);

                    } else {
                        System.out.println("Invalid account #");
                    }

                    break;
                case 5:
                    if (accounts.size() == 0) System.out.println("No accounts to show.");
                    for (int i = 0; i < accounts.size(); i++) {
                        System.out.print("Account #" + (i + 1) +  ": ");
                        accounts.get(i).PrintBalance();
                    }
                    break;
                case 6:
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
