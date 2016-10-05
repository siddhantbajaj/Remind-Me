package com.example.siddhant.remindme;

import android.net.Uri;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by ABC on 16-07-2016.
 */
@Table(name = "Items1")
public class Item extends Model {
    @Column(name="Category")
    public String Category;
    @Column(name="View")
    public String View_type;
    @Column(name="SubType")
    public String SubType;
    @Column(name="Task")
    public String Task;
    @Column(name="ImageUri")
    public String ImageUri;



    @Column(name="Notes")

    public String Notes;



//    public void setImageUri(Uri imageUri) {
//        ImageUri = imageUri;
//    }

    public Item() {
    }
    public String getImageUri() {
        return ImageUri;
    }

    public String getCategory() {
        return Category;
    }

    public String getView_type() {
        return View_type;
    }

    public String getSubType() {
        return SubType;
    }

    public String getTask() {
        return Task;
    }
    public String getNotes() {
        return Notes;
    }

    public Item(String category, String view_type, String subType, String task, String imageUri, String notes) {
        Category = category;
        View_type = view_type;
        SubType = subType;
        Task = task;
        ImageUri = imageUri;
        Notes = notes;
    }

    public static List<Item>getAll(String SubType, String Category)
    {
        return new Select().from(Item.class).where("SubType=?",SubType).where("Category=?",Category).execute();
    }
    public static List<Item>getList(String Category)
    {
        return new Select().from(Item.class).where("Category=?",Category).execute();
    }
    public static List<Item>getListbyTaskName(String Task)
    {
        return new Select().from(Item.class).where("Task=?",Task).execute();
    }
}
