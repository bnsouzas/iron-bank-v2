package com.buddycash.ironbank.transactions.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TransactionCreate implements Serializable {

    private UUID account;
    private TransactionType type;
    private Instant transactionAt;
    private String name;
    private String description;
    private BigDecimal price;
    private List<String> tags;

    public UUID getAccount() {
        return account;
    }

    public void setAccount(UUID account) {
        this.account = account;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Instant getTransactionAt() {
        return transactionAt;
    }

    public void setTransactionAt(Instant transactionAt) {
        this.transactionAt = transactionAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionCreate that = (TransactionCreate) o;
        return Objects.equals(account, that.account) && type == that.type && Objects.equals(transactionAt, that.transactionAt) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, type, transactionAt, name, description, price, tags);
    }

    @Override
    public String toString() {
        return "TransactionCreate{" +
                "account=" + account +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
