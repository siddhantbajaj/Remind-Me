package com.example.siddhant.remindme.Activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.siddhant.remindme.AlarmReceiver;
import com.example.siddhant.remindme.Category;
import com.example.siddhant.remindme.NotificationService;
import com.example.siddhant.remindme.R;

import java.util.GregorianCalendar;

public class ReminderActivity extends AppCompatActivity {
    CardView time;
    CardView date;
    CardView repeat;
    CardView location;
    EditText hour;
    EditText minute;
    EditText Am;
    EditText DateInView;
    EditText year;
    EditText month;
    int y=0;
    int m=0;
    int d=0;
    int h=0;
    int min=0;
    PendingIntent pi;
    GregorianCalendar calendar;
    AlarmManager am;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        date=(CardView)findViewById(R.id.card_view);
        time=(CardView)findViewById(R.id.card_view_1);
        repeat=(CardView)findViewById(R.id.card_view_2);
        location=(CardView)findViewById(R.id.card_view_3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(y==0||d==0)
                {
                    Toast.makeText(ReminderActivity.this,"First set date",Toast.LENGTH_LONG).show();
                    return;
                }
                GregorianCalendar calendar1=(GregorianCalendar) GregorianCalendar.getInstance();
                final int hour=calendar1.get(GregorianCalendar.HOUR_OF_DAY);
                final int min=calendar1.get(GregorianCalendar.MINUTE);
                TimePickerDialog timePickerDialog=new TimePickerDialog(ReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar=new GregorianCalendar();
                        calendar.set(y, m, d, hourOfDay, minute, 0);
                        am = (AlarmManager) getSystemService(ALARM_SERVICE);
                        //Intent i = new Intent(ReminderActivity.this, AlarmReceiver.class);
                        //Intent i = new Intent(ReminderActivity.this, NotificationActivity.class);
                        Intent i = new Intent(ReminderActivity.this, NotificationService.class);
                        i.putExtra("task", getIntent().getStringExtra("task"));
                       // pi = PendingIntent.getBroadcast(ReminderActivity.this, 0, i, 0);
                       // pi = PendingIntent.getActivity(ReminderActivity.this, 0, i, 0);
                        pi=PendingIntent.getService(ReminderActivity.this, 0, i, 0);

                        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);

                        Toast.makeText(ReminderActivity.this, "Alarm set", Toast.LENGTH_LONG).show();
                    }
                },hour,min,true);
                timePickerDialog.show();

//                Intent openClockIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
//                openClockIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(openClockIntent);

//                AlertDialog.Builder builder=new AlertDialog.Builder(ReminderActivity.this);
//                builder.setTitle("SET TIME");
//                final View v1= getLayoutInflater().inflate(R.layout.time_dialog_view,null);
//
//                builder.setView(v1);
//                 hour=(EditText)v1.findViewById(R.id.hour);
//                 minute=(EditText)v1.findViewById(R.id.minute);
//
//                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                         calendar=new GregorianCalendar();
//                        if(y!=0)
//                        {
//                            try {
//                                h = Integer.parseInt(String.valueOf(hour.getText()));
//                                min = Integer.parseInt(String.valueOf(minute.getText()));
//                                Log.i("Alarm", "set");
//                                calendar.set(y, m, d, h, min, 0);
//                                am = (AlarmManager) getSystemService(ALARM_SERVICE);
//                                Intent i = new Intent(ReminderActivity.this, AlarmReceiver.class);
//                                i.putExtra("task", getIntent().getStringExtra("task"));
//                                pi = PendingIntent.getBroadcast(ReminderActivity.this, 0, i, 0);
//
//                                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
//                                Toast.makeText(ReminderActivity.this, "Alarm set", Toast.LENGTH_LONG).show();
//                            }
//                            catch (NumberFormatException e)
//                            {
//                                Toast.makeText(ReminderActivity.this,"Invalid time",Toast.LENGTH_LONG).show();
//                            }
//                        }
//                        dialog.dismiss();
//                    }
//                });
//                builder.create().show();
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GregorianCalendar calendar1=(GregorianCalendar) GregorianCalendar.getInstance();
                int year=calendar1.get(GregorianCalendar.YEAR);
                int month=calendar1.get(GregorianCalendar.MONTH);
                int date=calendar1.get(GregorianCalendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(ReminderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        y=year;
                        m=monthOfYear;
                        d=dayOfMonth;
                        Toast.makeText(ReminderActivity.this, "Date Set", Toast.LENGTH_LONG).show();

                    }
                },year,month,date);
                datePickerDialog.show();
//                Intent intent = new Intent(Intent.ACTION_EDIT);
//                intent.setType("vnd.android.cursor.item/event");
//                intent.putExtra("title", "Some title");
//                intent.putExtra("description", "Some description");
//                intent.putExtra("beginTime", 1000);
//                intent.putExtra("endTime", 10000);
//                startActivity(intent);
//                AlertDialog.Builder builder=new AlertDialog.Builder(ReminderActivity.this);
//                builder.setTitle("SET DATE");
//                final View v1= getLayoutInflater().inflate(R.layout.date_dialog_view,null);
//
//                builder.setView(v1);
//                year=(EditText)v1.findViewById(R.id.year);
//                month=(EditText)v1.findViewById(R.id.month);
//                DateInView=(EditText)v1.findViewById(R.id.date);
//                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//
//                        try {
//                            y = Integer.parseInt(String.valueOf(year.getText()));
//                            m = Integer.parseInt(String.valueOf(month.getText()));
//                            d = Integer.parseInt(String.valueOf(DateInView.getText()));
//                            Toast.makeText(ReminderActivity.this, "Date Set", Toast.LENGTH_LONG).show();
//                        }
//                        catch (NumberFormatException e)
//                        {
//                            Toast.makeText(ReminderActivity.this,"Invalid date",Toast.LENGTH_LONG).show();
//                        }
//                        dialog.dismiss();
//                    }
//                });
//                builder.create().show();
            }
        });
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pi==null||calendar==null||am==null)
                {
                    Toast.makeText(ReminderActivity.this,"First set date and time",Toast.LENGTH_LONG).show();
                    return;
                }
                final AlertDialog builder = new AlertDialog.Builder(ReminderActivity.this).create();
                builder.setTitle("Recurring Reminder");
                final View v1= getLayoutInflater().inflate(R.layout.repeat_reminder,null);

                builder.setView(v1);
                TextView yearly=(TextView)v1.findViewById(R.id.yearly);
                TextView monthly=(TextView)v1.findViewById(R.id.monthly);
                TextView daily=(TextView)v1.findViewById(R.id.daily);
                TextView weekly=(TextView)v1.findViewById(R.id.weekly);
                yearly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),365*24*3600*1000,pi);
                        Toast.makeText(ReminderActivity.this,"Recurring Reminder set",Toast.LENGTH_LONG).show();
                        builder.dismiss();
                    }
                });
                monthly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),30*24*3600*1000,pi);
                        Toast.makeText(ReminderActivity.this,"Recurring Reminder set",Toast.LENGTH_LONG).show();
                        builder.dismiss();
                    }
                });
                weekly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),7*24*3600*1000,pi);
                        Toast.makeText(ReminderActivity.this,"Recurring Reminder set",Toast.LENGTH_LONG).show();
                        builder.dismiss();
                    }
                });
                daily.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*3600*1000,pi);
                        Toast.makeText(ReminderActivity.this,"Recurring Reminder set",Toast.LENGTH_LONG).show();
                        builder.dismiss();
                    }
                });



                builder.show();
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ReminderActivity.this,LocationActivity.class);
                i.putExtra("task", getIntent().getStringExtra("task"));
                startActivityForResult(i,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1)
        {
            if(resultCode==2)
            {
                AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
                Intent i = new Intent(ReminderActivity.this, NotificationService.class);
                i.putExtra("task", getIntent().getStringExtra("task"));
                i.setAction("location");
                i.putExtra("latitude",data.getDoubleExtra("latitude",-1));
                i.putExtra("longitude",data.getDoubleExtra("longitude",-1));

               PendingIntent pi1=PendingIntent.getService(ReminderActivity.this, 0, i, 0);

                am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+5000,10000,pi1);
                Toast.makeText(ReminderActivity.this,"Location reminder set",Toast.LENGTH_LONG).show();

            }
        }
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
