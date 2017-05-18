package com.lackhite.borrowland.server.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;
import java.util.Stack;

public class User {
    @Id
    private Long id;

    private String name;
    @DBRef
    private List<ActiveLoan> activeLoans = new Stack<>();
    @DBRef
    private List<ClosedLoan> closedLoans = new Stack<>();

    public User() {

    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<ActiveLoan> getActiveLoans() {
        return activeLoans;
    }

    public void setActiveLoans(List<ActiveLoan> activeLoans) {
        this.activeLoans = activeLoans;
    }

    public List<ClosedLoan> getClosedLoans() {
        return closedLoans;
    }

    public void setClosedLoans(List<ClosedLoan> closedLoans) {
        this.closedLoans = closedLoans;
    }
}
