package com.example.siddhant.remindme.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.siddhant.remindme.Activity.NotesActivity;
import com.example.siddhant.remindme.Activity.TabbedActivity;
import com.example.siddhant.remindme.R;

import java.util.ArrayList;

/**
 * Created by ABC on 16-07-2016.
 */
public class DateSecondLevelAdapter extends BaseExpandableListAdapter {
  Context mcontext;
    ArrayList<String>myarr;

    public DateSecondLevelAdapter(Context mcontext, ArrayList<String> myarr) {
        this.mcontext = mcontext;
        this.myarr = myarr;
    }

    @Override
    public int getGroupCount() {
        return myarr.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
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
        View v= LayoutInflater.from(mcontext).inflate(R.layout.date_child_view,parent,false);
        TextView textView=(TextView)v.findViewById(R.id.dateTask);
        textView.setText(myarr.get(groupPosition));
        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v= LayoutInflater.from(mcontext).inflate(R.layout.tab_layout,parent,false);
        final TabLayout tabLayout=(TabLayout) v.findViewById(R.id.tabs);


        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_alarm_off_black_24dp).setTag("cancel"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_done_black_24dp).setTag("done"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_event_note_black_24dp).setTag("note"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_timer_black_24dp).setTag("time"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_add_a_photo_black_24dp).setTag("image"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_list_black_24dp).setTag("list"));
        tabLayout.setTag(myarr.get(groupPosition));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                ((TabbedActivity) mcontext).onTabClick(tab,tabLayout);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
