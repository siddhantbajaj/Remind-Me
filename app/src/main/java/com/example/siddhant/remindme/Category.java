package com.example.siddhant.remindme;

/**
 * Created by ABC on 16-07-2016.
 */
public class Category {
    String Name;
    String NoOfItems;
    public Category(String Name,String NoOfItems) {
        this.Name=Name;
        this.NoOfItems=NoOfItems;

    }

    public String getName() {
        return Name;
    }

    public String getNoOfItems() {
        return NoOfItems;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setNoOfItems(String noOfItems) {
        NoOfItems = noOfItems;
    }
}
