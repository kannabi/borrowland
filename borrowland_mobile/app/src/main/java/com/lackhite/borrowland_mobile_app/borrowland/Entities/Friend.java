package com.lackhite.borrowland_mobile_app.borrowland.Entities;

/**
 * Created by kannabi on 25.02.17.
 */

public class Friend {
    private String name;
    private int id;

    public Friend(String name, int id){
        this.name = name;
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }
}

