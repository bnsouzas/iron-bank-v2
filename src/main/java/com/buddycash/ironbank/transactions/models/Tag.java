package com.buddycash.ironbank.transactions.models;

import com.buddycash.ironbank.transactions.data.TransactionCreate;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    private UUID account;
    private String name;
    @ManyToMany(mappedBy = "tags")
    private Set<Transaction> transactions;

    public Tag() {

    }
    public Tag(UUID account, String tagName) {
        this.setAccount(account);
        this.setName(tagName);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAccount() {
        return account;
    }

    public void setAccount(UUID account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
