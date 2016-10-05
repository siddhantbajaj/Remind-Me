package com.example.siddhant.remindme.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siddhant.remindme.Category;
import com.example.siddhant.remindme.Fragment.DateViewFragment;
import com.example.siddhant.remindme.Fragment.ListFragment;
import com.example.siddhant.remindme.Fragment.PriorityFragment;
import com.example.siddhant.remindme.Item;
import com.example.siddhant.remindme.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class TabbedActivity extends AppCompatActivity implements DateViewFragment.contract,PriorityFragment.contract,ListFragment.contract{

    TabLayout tabLayout;
    TabLayout mtabLayout;
    SharedPreferences sp;
    TextView toolbarText;
    DateViewFragment obj=new DateViewFragment();
    PriorityFragment obj1=new PriorityFragment();
    ListFragment obj2=new ListFragment();
    TabLayout itemTabs;

    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "MainActivity";
    Uri selectedImageUri;
    private static final int CAMERA_REQUEST = 1888;

    @Override
    public void Added() {
        obj2.dataChanged();

    }

    @Override
    public void setCount( int count) {
        SharedPreferences.Editor editor=sp.edit();
        int output=count+sp.getInt(getIntent().getStringExtra("name"),0);
        editor.putInt(getIntent().getStringExtra("name"),output);
        editor.commit();
    }

    @Override
    public void saveRecord(String TaskName, String subType, String ViewType) {
        Item item=new Item();
        item.SubType=subType;
        item.View_type=ViewType;
        item.Task=TaskName;
        item.Category=String.valueOf(toolbarText.getText());
        item.ImageUri="";
        item.Notes="NO NOTES";
        item.save();
    }

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);


        sp=getSharedPreferences("Remind Me", Context.MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
         toolbarText=(TextView) this.findViewById(R.id.toolbar_text);
        toolbarText.setText(getIntent().getStringExtra("name"));
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));



        tabLayout.setupWithViewPager(mViewPager);
        //setupTabIcons();
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
        mViewPager.setOffscreenPageLimit(0);
        Intent i=new Intent();
        setResult(1);

        itemTabs=(TabLayout)findViewById(R.id.tabs);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
    public void onTabClick(TabLayout.Tab tab,TabLayout tabLayout)
    {
        mtabLayout=tabLayout;
        if(tab.getTag().equals("note"))
        {
            Intent i=new Intent();
            i.setClass(TabbedActivity.this,NotesActivity.class);
            startActivityForResult(i,2);

        }
        else if(tab.getTag().equals("image"))
        {
            //final AlertDialog.Builder builder=new AlertDialog.Builder(TabbedActivity.this);
            final AlertDialog builder = new AlertDialog.Builder(this).create();
            builder.setTitle("ADD PHOTO");
            final View v1= getLayoutInflater().inflate(R.layout.dialog_view_1,null);

            builder.setView(v1);
            TextView choose=(TextView)v1.findViewById(R.id.choose);
            TextView camera=(TextView)v1.findViewById(R.id.takePhoto);

            choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                    builder.dismiss();


                }
            });
            camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    builder.dismiss();
                }
            });

            builder.show();
        }
        else if(tab.getTag().equals("list"))
        {
            Intent i=new Intent();
            i.putExtra("task",String.valueOf(mtabLayout.getTag()));
            i.setClass(TabbedActivity.this,listActivity.class);
            List<Item>list=Item.getListbyTaskName(String.valueOf(mtabLayout.getTag()));
            for(Item j:list)
            {
                if(j.getImageUri()!=null)
                {
                    String ImageUri=j.getImageUri();

                    i.putExtra("imageUri", ImageUri);
                }
                i.putExtra("Notes",j.getNotes());
            }

            startActivity(i);
        }
        else if(tab.getTag().equals("time"))
        {
            Intent i=new Intent();
            i.putExtra("task",String.valueOf(mtabLayout.getTag()));
            i.setClass(TabbedActivity.this,ReminderActivity.class);
            startActivity(i);
        }
        else if(tab.getTag().equals("done"))
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(TabbedActivity.this);
            builder.setTitle("Delete task");
            builder.setMessage("Are you sure you want to delete this task?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    List<Item>list=Item.getListbyTaskName(String.valueOf(mtabLayout.getTag()));
                    for(Item i:list)
                    {
                        i.delete();
                        setCount(-1);
                        Toast.makeText(TabbedActivity.this,"Task deleted from Database",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();



        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK) {if (requestCode == SELECT_PICTURE) {// Get the url from
            Toast.makeText(TabbedActivity.this,"Image set",Toast.LENGTH_LONG).show();
          selectedImageUri = data.getData();
            List<Item>list=Item.getListbyTaskName(String.valueOf(mtabLayout.getTag()));
            for(Item i:list)
            {
                i.ImageUri=selectedImageUri.toString();

                i.save();
            }
        
         if (null != selectedImageUri) {
             // Get the path from the UriString
             String path = getPathFromURI(selectedImageUri);
             Log.i(TAG, "Image Path : " + path);// Set the image in
              }}
        }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(TabbedActivity.this,"Image set",Toast.LENGTH_LONG).show();
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            //imageView.setImageBitmap(photo);
            Uri test=getImageUri(TabbedActivity.this,photo);
            List<Item>list=Item.getListbyTaskName(String.valueOf(mtabLayout.getTag()));
            for(Item i:list)
            {
                i.ImageUri=test.toString();

                i.save();
            }

            if (null != test) {
                // Get the path from the UriString
                String path = getPathFromURI(test);
                Log.i(TAG, "Image Path : " + path);// Set the image in
            }}


        if(requestCode==2)
        {
            Toast.makeText(TabbedActivity.this,"Notes set",Toast.LENGTH_LONG).show();
            String Notes=data.getStringExtra("notes");
            List<Item>list=Item.getListbyTaskName(String.valueOf(mtabLayout.getTag()));
            for(Item i:list)
            {
                i.Notes=Notes;

                i.save();
            }
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String getPathFromURI(Uri contentUri)
    {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst())
        {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_add_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_add_white_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_add_white_24dp);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:


                    obj.setListener(TabbedActivity.this);
                    obj.setCategory(String.valueOf(toolbarText.getText()));
                    return obj;

                case 1:


                    obj1.setListener(TabbedActivity.this);
                    obj1.setCategory(String.valueOf(toolbarText.getText()));
                    return obj1;

                case 2:

                    obj2.setListener(TabbedActivity.this);
                    obj2.setCategory(String.valueOf(toolbarText.getText()));
                    return obj2;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Date View";
                case 1:
                    return "Priority";
                case 2:
                    return "List View";
            }
            return null;
        }
    }
}
