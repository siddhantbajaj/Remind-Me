package com.example.siddhant.remindme;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by ABC on 18-07-2016.
 */
@Table(name = "CategoryItems")
public class CategoryItem extends Model {
    @Column(name="CategoryName")
    public String CategoryName;
    @Column(name="Number")
    public String Number;

    public CategoryItem() {
    }

    public CategoryItem(String categoryName, String number) {
        CategoryName = categoryName;
        Number = number;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public String getNumber() {
        return Number;
    }

    public static List<CategoryItem> getAll()
    {
        return new Select().from(CategoryItem.class).execute();
    }
}
