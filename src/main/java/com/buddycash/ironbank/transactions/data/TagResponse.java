package com.buddycash.ironbank.transactions.data;

import java.util.Objects;
import java.util.UUID;

public class TagResponse {
    private UUID id;
    private UUID account;
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagResponse that = (TagResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(account, that.account) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, name);
    }

    @Override
    public String toString() {
        return "TagResponse{" +
                "id=" + id +
                ", account=" + account +
                ", name='" + name + '\'' +
                '}';
    }
}
