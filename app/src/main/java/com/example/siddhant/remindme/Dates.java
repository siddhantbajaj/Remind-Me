package com.example.siddhant.remindme;

import java.util.ArrayList;

/**
 * Created by ABC on 16-07-2016.
 */
public class Dates {
    ArrayList<String>children=new ArrayList<>();
    String name;

    public Dates(ArrayList<String> children, String name) {
        this.children = children;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getChildren() {
        return children;
    }
}
