import com.kenryhraval.banking.account.Account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void depositIncreasesBalance() {
        Account account = new Account();
        account.deposit(10.0);
        assertEquals(10.0, account.getBalance());
    }

    @Test
    void withdrawDecreasesBalance() {
        Account account = new Account(200.0);
        account.withdraw(50.0);
        assertEquals(150.0, account.getBalance());
    }

    @Test
    void transferMovesMoneyBetweenAccounts() {
        Account source = new Account(300.0);
        Account destination = new Account(100.0);

        source.transferToAnother(destination, 50.0);

        assertAll(
            () -> assertEquals(250.0, source.getBalance()),
            () -> assertEquals(150.0, destination.getBalance())
        );
    }

    @Test
    void transferFailsIfNotEnoughFunds() {
        Account source = new Account(30.0);
        Account destination = new Account(50.0);

        source.transferToAnother(destination, 100.0);

        // balances should remain unchanged
        assertAll(
            () -> assertEquals(30.0, source.getBalance()),
            () -> assertEquals(50.0, destination.getBalance())
        );
    }

    @Test
    void transferNegativeAmountDoesNotChangeBalances() {
        Account source = new Account(100.0);
        Account destination = new Account(200.0);

        source.transferToAnother(destination, -50.0);

        assertAll(
            () -> assertEquals(100.0, source.getBalance()),
            () -> assertEquals(200.0, destination.getBalance())
        );
    }

    @Test
    void depositNegativeAmountThrowsException() {
        Account account = new Account(50.0);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-20.0));
    }

    @Test
    void withdrawNegativeAmountThrowsException() {
        Account account = new Account(200.0);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-50.0));
    }
}