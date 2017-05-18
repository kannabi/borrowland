package com.lackhite.borrowland.server.domain;


import org.springframework.data.annotation.Id;

public class ActiveLoan {
    @Id
    private Long id;

    private Long partId;
    private String partName;

    private Long sum;

    private String date;
    private String time;

    public ActiveLoan() {

    }

    public ActiveLoan(Long partId, String partName, Long sum, String date, String time) {
        this.partId = partId;
        this.partName = partName;
        this.sum = sum;
        this.date = date;
        this.time = time;
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
}
