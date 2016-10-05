package com.example.siddhant.remindme.Fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.example.siddhant.remindme.Adapter.DateAdapter;
import com.example.siddhant.remindme.Dates;
import com.example.siddhant.remindme.Item;
import com.example.siddhant.remindme.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DateViewFragment extends Fragment {

    contract mlistener;
    String mCategory;
    int count=0;

    public DateViewFragment() {
        // Required empty public constructor
    }
    public interface contract{
        public void setCount(int count);
        public void saveRecord(String TaskName,String subType,String ViewType);
        public void Added();
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
        View v= inflater.inflate(R.layout.fragment_date_view, container, false);
        final ExpandableListView listView=(ExpandableListView)v.findViewById(R.id.dateListView);
        listView.setTranscriptMode(ExpandableListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
       // listView.setStackFromBottom(true);
        final ArrayList<Dates>arrayList=new ArrayList<>();
        arrayList.add(new Dates(new ArrayList<String>(),"TODAY"));
        arrayList.add(new Dates(new ArrayList<String>(),"TOMORROW"));
        arrayList.add(new Dates(new ArrayList<String>(),"UPCOMING"));
        arrayList.add(new Dates(new ArrayList<String>(),"SOMEDAY"));
        for(Dates i:arrayList)
        {
            List<Item>child=Item.getAll(i.getName(),mCategory);
            for(Item j:child)
            {
                i.getChildren().add(j.getTask());
            }
        }
        final DateAdapter adapter=new DateAdapter(getActivity(),arrayList);
        listView.setAdapter(adapter);




       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
               AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
               builder.setTitle("ADD REMINDER");
               final View v1=LayoutInflater.from(getActivity()).inflate(R.layout.dialog_view,parent,false);
               final EditText txt=(EditText)v1.findViewById(R.id.edit_text);
               builder.setView(v1);
               builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       arrayList.get(position).getChildren().add(String.valueOf(txt.getText()));

                       adapter.notifyDataSetChanged();
                       mlistener.setCount(1);

                       mlistener.saveRecord(String.valueOf(txt.getText()),arrayList.get(position).getName(),"Date View");
                       mlistener.Added();

                       dialog.dismiss();
                   }
               });
               builder.create().show();
               return true;
           }
       });
        return v;
    }

}
