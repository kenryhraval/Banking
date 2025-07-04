package test;

import main.BankAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BankAccountTest {

    @Test
    void depositIncreasesBalance() {
        BankAccount account = new BankAccount();
        account.deposit(10.0);
        assertEquals(10.0, account.getBalance());
    }

    @Test
    void depositNegativeAmountDoesNotChangeBalance() {
        BankAccount account = new BankAccount(50.0);
        account.deposit(-20.0);
        assertEquals(50.0, account.getBalance());
    }

    @Test
    void withdrawDecreasesBalance() {
        BankAccount account = new BankAccount(200.0);
        account.withdraw(50.0);
        assertEquals(150.0, account.getBalance());
    }

    @Test
    void withdrawNegativeAmountDoesNotChangeBalance() {
        BankAccount account = new BankAccount(200.0);
        account.withdraw(-50.0);
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void transferMovesMoneyBetweenAccounts() {
        BankAccount source = new BankAccount(300.0);
        BankAccount destination = new BankAccount(100.0);

        source.transferToAnother(destination, 50.0);

        assertAll(
            () -> assertEquals(250.0, source.getBalance()),
            () -> assertEquals(150.0, destination.getBalance())
        );
    }

    @Test
    void transferFailsIfNotEnoughFunds() {
        BankAccount source = new BankAccount(30.0);
        BankAccount destination = new BankAccount(50.0);

        source.transferToAnother(destination, 100.0);

        // balances should remain unchanged
        assertAll(
            () -> assertEquals(30.0, source.getBalance()),
            () -> assertEquals(50.0, destination.getBalance())
        );
    }

    @Test
    void transferNegativeAmountDoesNotChangeBalances() {
        BankAccount source = new BankAccount(100.0);
        BankAccount destination = new BankAccount(200.0);

        source.transferToAnother(destination, -50.0);

        assertAll(
            () -> assertEquals(100.0, source.getBalance()),
            () -> assertEquals(200.0, destination.getBalance())
        );

    }
}