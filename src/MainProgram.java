import java.util.Scanner;

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
                    int newId = manager.addAccount(new BankAccount(initial));
                    System.out.println("Account #" + newId + " created.");
                    break;
                case 2:
                    System.out.print("Enter account #: ");
                    int depositId = scanner.nextInt();

                    BankAccount depositAccount = manager.getAccount(depositId);

                    if (depositAccount == null) System.out.println("Invalid account #");
                    else {
                        System.out.print("Enter amount to deposit: ");
                        double amount = scanner.nextDouble();
                        depositAccount.deposit(amount);
                    }
                    break;
                case 3:
                    System.out.print("Enter account #: ");
                    int withdrawId = scanner.nextInt();

                    BankAccount withdrawAccount = manager.getAccount(withdrawId);

                    if (withdrawAccount == null) System.out.println("Invalid account #");
                    else {
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        withdrawAccount.withdraw(withdrawAmount);

                    }
                    break;

                case 4:
                    System.out.print("Enter source account #: ");
                    int sourceId = scanner.nextInt();
                    System.out.print("Enter destination account #: ");
                    int destinationId = scanner.nextInt();

                    BankAccount sourceAccount = manager.getAccount(sourceId);
                    BankAccount destinationAccount = manager.getAccount(destinationId);
                    if (sourceAccount == null || destinationAccount == null) System.out.println("Invalid account #");
                    else {
                        System.out.print("Enter amount to transfer: ");
                        double transmitAmount = scanner.nextDouble();
                        sourceAccount.transferToAnother(destinationAccount, transmitAmount);

                    }

                    break;

                case 5:
                    manager.printBalances();
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
