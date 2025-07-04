package logic;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountManager manager = new AccountManager();

    @GetMapping("/{id}")
    public double getBalance(@PathVariable int id) {
        BankAccount account = manager.getAccount(id);
        if (account == null) throw new RuntimeException("Account not found");
        return account.getBalance();
    }

    @PostMapping("/create")
    public int createAccount(@RequestParam double initialBalance) {
        return manager.addAccount(new BankAccount(initialBalance));
    }


    @PostMapping("/{id}/deposit")
    public void deposit(@PathVariable int id, @RequestParam double amount) {
        BankAccount account = manager.getAccount(id);
        if (account == null) throw new RuntimeException("Account not found");
        account.deposit(amount);
    }

    @PostMapping("/{id}/withdraw")
    public void withdraw(@PathVariable int id, @RequestParam double amount) {
        BankAccount account = manager.getAccount(id);
        if (account == null) throw new RuntimeException("Account not found");
        account.withdraw(amount);
    }
}
