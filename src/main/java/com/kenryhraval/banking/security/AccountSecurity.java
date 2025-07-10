package com.kenryhraval.banking.security;

import com.kenryhraval.banking.model.Account;
import com.kenryhraval.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("accountSecurity")
public class AccountSecurity {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountSecurity(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean isAccountOwner(Long accountId, Authentication authentication) {
        System.out.println("isAccountOwner called: user=" + authentication.getName() + ", accountId=" + accountId);
        String username = authentication.getName();
        Account account = accountRepository.findById(accountId).orElse(null);
        return (account != null) && account.getOwner().getUsername().equals(username);
    }
}
