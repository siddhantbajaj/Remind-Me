package com.example.siddhant.remindme.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.siddhant.remindme.Adapter.imageAdapter;
import com.example.siddhant.remindme.Category;
import com.example.siddhant.remindme.CategoryItem;
import com.example.siddhant.remindme.Item;
import com.example.siddhant.remindme.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    GridView gridView;
    final ArrayList<Category>arr =new ArrayList<>();
    com.example.siddhant.remindme.Adapter.imageAdapter imageAdapter;
    SharedPreferences sp;
    public  void setText(ArrayList<Category>arr)
    {

        for(Category i:arr)
        {
            int count=sp.getInt(i.getName(),-1);
            if(count!=-1&&count!=0)
            i.setNoOfItems(""+count+" Items");
            if(count==0)
                i.setNoOfItems("No Items");

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp=getSharedPreferences("Remind Me", Context.MODE_PRIVATE);
        gridView=(GridView) findViewById(R.id.gridview_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Category All=new Category("ALL","No Items");
        Category Personal=new Category("PERSONAL","No Items");
        Category MoviesTowatch=new Category("MOVIES TO WATCH","No Items");
        Category SCHOOL=new Category("SCHOOL","No Items");
        Category WORK=new Category("WORK","No Items");
        Category GROCERYLIST=new Category("GROCERY LIST","No Items");
        Category ShowsTowatch=new Category("SHOWS TO WATCH","No Items");
        Category Meetings=new Category("MEETINGS","No Items");

        arr.add(All);
        arr.add(Personal);
        arr.add(MoviesTowatch);
        arr.add(SCHOOL);
        arr.add(WORK);
        arr.add(GROCERYLIST);
        arr.add(ShowsTowatch);
        arr.add(Meetings);
        List<CategoryItem>list=CategoryItem.getAll();
        for(CategoryItem i:list)
        {
            arr.add(new Category(i.getCategoryName(),i.getNumber()));
        }


//        arr.add(GROCERYLIST);
//        arr.add(GROCERYLIST);
         imageAdapter=new imageAdapter(this,arr);
        setText(arr);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent();
                i.setClass(MainActivity.this,TabbedActivity.class);
                i.putExtra("name",arr.get(position).getName());
                startActivityForResult(i,1);
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete category");
                builder.setMessage("Are you sure you want to delete this category?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<CategoryItem>list=CategoryItem.getAll();
                        for(CategoryItem i:list)
                        {
                            if(arr.get(position).getName().equals(i.getCategoryName()))
                            {
                                i.delete();
                                Toast.makeText(MainActivity.this,"Category deleted from database",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Sorry this is a blah category",Toast.LENGTH_LONG).show();
                            }
                        }
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                return true;
            }
        });

        //PRE
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("ADD CATEGORY");
                final View v1= getLayoutInflater().inflate(R.layout.dialog_view,null);
                final EditText txt=(EditText)v1.findViewById(R.id.edit_text);
                builder.setView(v1);
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CategoryItem item=new CategoryItem();
                        item.CategoryName=String.valueOf(txt.getText());
                        item.Number="No Items";
                        item.save();
                       arr.add(new Category(String.valueOf(txt.getText()),"No Items"));
                        setText(arr);
                        imageAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setText(arr);
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
