package com.example.siddhant.remindme.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.siddhant.remindme.R;

public class listActivity extends AppCompatActivity {
    AppCompatImageView imgView;
    TextView Notes;
    TextView Task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Notes=(TextView)findViewById(R.id.taskNotes);
        Task=(TextView)findViewById(R.id.TaskText);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgView=(AppCompatImageView)findViewById(R.id.taskImage);
        imgView.setImageResource(R.drawable.blah);
        if(getIntent().getStringExtra("imageUri")!=null) {
            Uri ImageUri = Uri.parse(getIntent().getStringExtra("imageUri"));


            if (ImageUri != null)
                imgView.setImageURI(ImageUri);
        }
        Notes.setText(getIntent().getStringExtra("Notes"));
        Task.setText(getIntent().getStringExtra("task"));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            super.onBackPressed();

        }
        return true;
    }

}
