package com.buddycash.ironbank.transactions.data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class TransactionResponse {
    private UUID id;
    private UUID account;
    private TransactionType type;
    private Instant transactionAt;
    private String name;
    private String description;
    private BigDecimal price;
    private Set<TagResponse> tags;

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

    public Set<TagResponse> getTags() {
        return tags;
    }

    public void setTags(Set<TagResponse> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionResponse that = (TransactionResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(account, that.account) && type == that.type && Objects.equals(transactionAt, that.transactionAt) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, type, transactionAt, name, description, price, tags);
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "id=" + id +
                ", account=" + account +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
