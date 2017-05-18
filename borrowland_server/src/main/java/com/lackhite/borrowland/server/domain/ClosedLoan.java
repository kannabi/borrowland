package com.lackhite.borrowland.server.domain;

import org.springframework.data.annotation.Id;

import java.util.Date;

import static com.lackhite.borrowland.server.StaticDateFormat.*;

public class ClosedLoan {
    @Id
    private Long id;

    private Long partId;
    private String partName;

    private Long sum;

    private String date;
    private String time;

    private String closingDate;
    private String closingTime;

    public ClosedLoan() {

    }

    public ClosedLoan(Long partId, String partName, Long sum, String date, String time, String closingDate, String closingTime) {
        this.partId = partId;
        this.partName = partName;
        this.sum = sum;
        this.date = date;
        this.time = time;
        this.closingDate = closingDate;
        this.closingTime = closingTime;
    }

    public ClosedLoan(ActiveLoan loan) {
        Date date = new Date();

        this.id = loan.getId();

        this.partId = loan.getPartId();
        this.partName = loan.getPartName();
        this.sum = loan.getSum();
        this.date = loan.getDate();
        this.time = loan.getTime();

        this.closingDate = dateFormat.format(date);
        this.closingTime = timeFormat.format(date);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPartId() {
        return partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }
}
