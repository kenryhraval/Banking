package com.kenryhraval.banking.repository;

import com.kenryhraval.banking.model.Account;
import com.kenryhraval.banking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByOwner(User owner);
}
