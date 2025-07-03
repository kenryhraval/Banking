import java.io.Serializable;

public class BankAccount implements Serializable {
    private double balance;

    BankAccount() {
        this.balance = 0;
    }

    BankAccount(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Can`t deposit non-positive money!");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0) {
            balance -= amount;
        } else {
            System.out.println("Can`t withdraw non-positive money!");
        }
    }

    public void printBalance() {
        System.out.println("Current balance: $" + balance);
    }

    public double getBalance() {
        return balance;
    }

    public void transferToAnother(BankAccount another, double amount) {
        if (amount > 0) {
            if (this.balance >= amount) {
                this.withdraw(amount);
                another.deposit(amount);

            } else {
                System.out.println("Not enough money for the transfer!");
            }

        } else {
            System.out.println("Can`t transfer non-positive money!");
        }
    }


}
