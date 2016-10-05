package com.example.siddhant.remindme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.siddhant.remindme.CustExpListview;
import com.example.siddhant.remindme.Dates;
import com.example.siddhant.remindme.R;

import java.util.ArrayList;

/**
 * Created by ABC on 16-07-2016.
 */

public class DateAdapter extends BaseExpandableListAdapter {
    ArrayList<Dates> myarr;
    Context mcontext;

        public DateAdapter(Context obj, ArrayList<Dates> arr) {
            mcontext=obj;
            myarr=arr;


        }
    @Override
    public int getGroupCount() {
        return myarr.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        int count= myarr.get(groupPosition).getChildren().size();
        return 1;

    }

    @Override
    public Object getGroup(int groupPosition) {
       return myarr.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return  myarr.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v=convertView;
        if(v==null)
        {
            v= LayoutInflater.from(mcontext).inflate(R.layout.date_group_view,parent,false);
        }
        TextView date=(TextView)v.findViewById(R.id.dateText);
        date.setText(myarr.get(groupPosition).getName());
        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CustExpListview SecondLevelexplv = new CustExpListview(mcontext);

        DateSecondLevelAdapter secondLevelAdapter=new DateSecondLevelAdapter(mcontext,myarr.get(groupPosition).getChildren());
        SecondLevelexplv.setAdapter(secondLevelAdapter);
        //SecondLevelexplv.setGroupIndicator(null);
        return SecondLevelexplv;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
