/*
create a method "printBalance" which displays the current balance to user
method for transfer balance from one bank account to another
 Optionally make the program interactive with user e.g. using Scanner
* */

public class BankAccount {
    private double balance;

    BankAccount() {
        this.balance = 0;
    }

    BankAccount(double balance) {
        this.balance = balance;
    }

    public void Deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Can`t deposit non-positive money!");
        }
    }

    public void Withdraw(double amount) {
        if (amount > 0) {
            balance -= amount;
        } else {
            System.out.println("Can`t withdraw non-positive money!");
        }
    }

    public void PrintBalance() {
        System.out.println("Current balance: $" + balance);
    }

    public void TransferToAnother(BankAccount another, double amount) {
        if (amount > 0) {
            if (this.balance >= amount) {
                this.Withdraw(amount);
                another.Deposit(amount);

            } else {
                System.out.println("Not enough money for the transfer!");
            }

        } else {
            System.out.println("Can`t transfer non-positive money!");
        }
    }



}
