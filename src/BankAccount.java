import java.io.Serializable;
import java.math.BigDecimal;

public class BankAccount implements Serializable {
    private BigDecimal balance;

    BankAccount() {
        this.balance = BigDecimal.ZERO;
    }

    BankAccount(double balance) {
        this.balance = BigDecimal.valueOf(balance);
    }

    public void deposit(double amount) {
        BigDecimal bdAmount = BigDecimal.valueOf(amount);
        if (bdAmount.compareTo(BigDecimal.ZERO) > 0) {
            balance = balance.add(bdAmount);
        }
        else System.out.println("Can`t deposit non-positive money!");
    }

    public void withdraw(double amount) {
        BigDecimal bdAmount = BigDecimal.valueOf(amount);
        if (bdAmount.compareTo(BigDecimal.ZERO) > 0) {
            balance = balance.subtract(bdAmount);
        } else {
            System.out.println("Can`t withdraw non-positive money!");
        }
    }

    public void printBalance() {
        System.out.println(String.format("Current balance: $%.2f", balance.doubleValue()));
    }

    public double getBalance() {
        return balance.doubleValue();
    }

    public void transferToAnother(BankAccount another, double amount) {
        BigDecimal bdAmount = BigDecimal.valueOf(amount);
        if (bdAmount.compareTo(BigDecimal.ZERO) > 0) {
            if (this.balance.compareTo(bdAmount) >= 0) {
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
