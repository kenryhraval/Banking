package com.kenryhraval.banking.config;

import com.kenryhraval.banking.model.Account;
import com.kenryhraval.banking.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AccountConfig {

    @Bean
    CommandLineRunner commandLineRunner (AccountRepository repository) {
        return args -> {
            Account account1 = new Account(100.50);

            repository.saveAll(List.of(account1));
        };


    }
}
