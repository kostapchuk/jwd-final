package com.epam.jwd_final.web.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class User extends AbstractEntity {

    private static final long serialVersionUID = 3489038301033270987L;

    private final String name;
    private final String password;
    private final BigDecimal balance;
    private final Role role;

    public User(Integer id, String name, String password, BigDecimal balance, Role role) {
        super(id);
        this.name = name;
        this.password = password;
        this.balance = balance;
        this.role = role;
    }

    public User(String name, String password, BigDecimal balance, Role role) {
        this(null, name, password, balance, role);
    }

    public User(String name, String password) {
        this(name, password, BigDecimal.ZERO, Role.CLIENT);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(password, user.password) &&
                Objects.equals(balance, user.balance) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, balance, role);
    }
}
