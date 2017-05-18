package com.lackhite.borrowland_mobile_app.borrowland.Entities;

/**
 * Created by kannabi on 16.02.17.
 */

public class LoanItem {
    private String name;
    private String id;
    private String date;
    private String time;
    private int dept;

    public LoanItem(String name, String date, String time, int dept, String id){
        this.name = name;
        this.date = date;
        this.time = time;
        this.dept = dept;
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public String getId (){
        return id;
    }

    public int getDebt(){
        return dept;
    }

    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }
}
