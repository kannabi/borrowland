package com.lackhite.borrowland_mobile_app.borrowland.Entities;

/**
 * Created by kannabi on 20.02.17.
 */

public class ClosedLoanItem extends LoanItem {
    String closeDate;
    String closeTime;

    public ClosedLoanItem(String name, String date, String time, int dept, String id,
                          String closeDate, String closeTime){
        super(name, date, time, dept, id);

        this.closeDate = closeDate;
        this.closeTime = closeTime;
    }

    public String getCloseDate(){
        return closeDate;
    }

    public String getCloseTime(){
        return closeTime;
    }
}
