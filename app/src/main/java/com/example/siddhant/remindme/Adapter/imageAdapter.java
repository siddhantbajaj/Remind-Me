package com.example.siddhant.remindme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.siddhant.remindme.Category;
import com.example.siddhant.remindme.R;

import java.util.ArrayList;

/**
 * Created by ABC on 28-06-2016.
 */
public class imageAdapter extends BaseAdapter {
    Context mycontext;
    ArrayList<Category>myarr;
    public imageAdapter(Context context, ArrayList<Category> objects) {

        myarr=objects;
        mycontext=context;
    }

    @Override
    public int getCount() {
        return myarr.size();
    }

    @Override
    public Object getItem(int position) {
        return myarr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        if(v==null)
        {
            v= LayoutInflater.from(mycontext).inflate(R.layout.image_layout,parent,false);
        }
        TextView Name=(TextView)v.findViewById(R.id.NameOfCategory);
        TextView No=(TextView)v.findViewById(R.id.NoOfItems);
        Name.setText(myarr.get(position).getName());
        No.setText(myarr.get(position).getNoOfItems());





        return v;
    }
}
