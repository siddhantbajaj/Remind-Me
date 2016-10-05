package com.example.siddhant.remindme.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.siddhant.remindme.Adapter.DateAdapter;
import com.example.siddhant.remindme.Adapter.DateSecondLevelAdapter;
import com.example.siddhant.remindme.Dates;
import com.example.siddhant.remindme.Item;
import com.example.siddhant.remindme.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    contract mlistener;
    String mCategory;

    DateSecondLevelAdapter adapter;
    public ListFragment() {
        // Required empty public constructor
    }

    public void dataChanged() {
//        adapter.notifyDataSetChanged();
    }

    public interface contract{
        public void setCount(int count);
        public void saveRecord(String TaskName,String subType,String ViewType);
    }
    public void setListener(contract listener)
    {
        mlistener=listener;
    }
    public void setCategory(String Category)
    {
        mCategory=Category;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_list, container, false);
        ExpandableListView listView=(ExpandableListView)v.findViewById(R.id.dateListView);

//        arrayList.add(new Dates(new ArrayList<String>(),"PERSONEL"));
//        arrayList.add(new Dates(new ArrayList<String>(),"TOMORROW"));
//        arrayList.add(new Dates(new ArrayList<String>(),"UPCOMING"));
//        arrayList.add(new Dates(new ArrayList<String>(),"SOMEDAY"));
        ArrayList<String> arrayList=new ArrayList<>();

         adapter=new DateSecondLevelAdapter(getActivity(),arrayList);
        List<Item>itemList=Item.getList(mCategory);
        for(Item i:itemList)
        {
            arrayList.add(i.getTask());
        }
        listView.setAdapter(adapter);
        return v;
    }

}
