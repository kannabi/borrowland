package com.lackhite.borrowland.server.domain;

import java.util.List;

public class LoanContainer<E> {
    private int size;
    private List<E> loans;

    public LoanContainer() {

    }

    public LoanContainer(int size, List<E> loans) {
        this.size = size;
        this.loans = loans;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<E> getLoans() {
        return loans;
    }

    public void setLoans(List<E> loans) {
        this.loans = loans;
    }
}
