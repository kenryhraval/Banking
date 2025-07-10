package com.kenryhraval.banking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "accounts")
public class Account implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private BigDecimal balance;

    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @Getter
    @Setter
    @Column(nullable = false)
    private boolean deleted;

    public Account() {
        this.balance = BigDecimal.ZERO;
    }

    public Account(double balance) {
        this.balance = BigDecimal.valueOf(balance);
    }

    public Account(double balance, User user) {
        this.balance = BigDecimal.valueOf(balance);
        owner = user;
        deleted = false;
    }

    public double getBalance() {
        return balance.doubleValue();
    }

    public void deposit(double amount) {
        BigDecimal bdAmount = BigDecimal.valueOf(amount);
        if (bdAmount.compareTo(BigDecimal.ZERO) > 0) {
            balance = balance.add(bdAmount);
        }
        else {
            throw new IllegalArgumentException("Can't deposit non-positive money!");
        }
    }

    public void withdraw(double amount) {
        BigDecimal bdAmount = BigDecimal.valueOf(amount);
        if (bdAmount.compareTo(BigDecimal.ZERO) > 0) {
            if (balance.compareTo(bdAmount) >= 0) {
                balance = balance.subtract(bdAmount);
            } else {
                throw new IllegalArgumentException("Insufficient funds!");
            }
        } else {
            throw new IllegalArgumentException("Can't withdraw non-positive money!");
        }
    }

    public void transferToAnother(Account another, double amount) {
        BigDecimal bdAmount = BigDecimal.valueOf(amount);
        if (bdAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Can't transfer non-positive money!");
        }

        if (this.balance.compareTo(bdAmount) < 0) {
            throw new IllegalArgumentException("Not enough money for the transfer!");
        }

        this.withdraw(amount);
        another.deposit(amount);
    }

}
