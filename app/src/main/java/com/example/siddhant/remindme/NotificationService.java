package com.example.siddhant.remindme;

import android.Manifest;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.example.siddhant.remindme.Activity.MainActivity;
import com.example.siddhant.remindme.Activity.TabbedActivity;
import com.example.siddhant.remindme.Activity.listActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.List;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class NotificationService extends IntentService implements  GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleApiClient.ConnectionCallbacks {
    int mId=1;
    static NotificationManager mNotificationManager;
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_FOO = "com.example.siddhant.remindme.action.FOO";
    public static final String ACTION_BAZ = "com.example.siddhant.remindme.action.BAZ";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "com.example.siddhant.remindme.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "com.example.siddhant.remindme.extra.PARAM2";

    public NotificationService() {
        super("NotificationService");
    }
    static GoogleApiClient mclient;
    static Intent intent1;

    @Override
    public void onStart(Intent intent, int startId) {

        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        //mclient.disconnect();
        super.onDestroy();
    }
  static Location desiredLocation=new Location("locationA");
    @Override
    protected void onHandleIntent(Intent intent) {
       // Toast.makeText(NotificationActivity.this,"alarm",Toast.LENGTH_LONG).show();
        if(intent.getAction()!=null)
        {
            intent1=intent;
            Log.i("result1","inside");
            double latitude=intent.getDoubleExtra("latitude",-1);
            double longitude=intent.getDoubleExtra("longitude",-1);
            desiredLocation.setLatitude(latitude);
            desiredLocation.setLongitude(longitude);

            mclient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mclient.connect();

        }
        else {
            Intent dismissIntent = new Intent(this, mediaService1.class);
            dismissIntent.setAction("dismiss");
            PendingIntent piDismiss = PendingIntent.getService(this, 0, dismissIntent, 0);

            Intent snoozeIntent = new Intent(this, mediaService1.class);
            snoozeIntent.setAction("snooze");
            PendingIntent piSnooze = PendingIntent.getService(this, 0, snoozeIntent, 0);
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_timer_black_24dp)
                            .setContentTitle("Remind Me")
                            .setContentText(intent.getStringExtra("task"))
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText("hmm"))
                            .addAction(R.drawable.ic_cancel_black_24dp,
                                    "Dismiss", piDismiss)
                            .addAction(R.drawable.ic_snooze_black_24dp,
                                    "Snooze", piSnooze);

// Creates an explicit intent for an Activity in your app
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            mBuilder.setSound(alarmSound);
            startService(new Intent(this, mediaService.class));
//        MediaPlayer mMediaPlayer = new MediaPlayer();
//        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        mMediaPlayer = MediaPlayer.create(this, alarmSound);
//
//        mMediaPlayer.setLooping(true);
//        mMediaPlayer.start();
            Notification notification = mBuilder.build();

            long[] vibrate = {500, 500, 500, 500, 500, 500, 500, 500, 500};
            notification.vibrate = vibrate;
            notification.flags |= Notification.FLAG_INSISTENT;
            //notification.defaults |= Notification.DEFAULT_VIBRATE;
            Intent resultIntent = new Intent(this, listActivity.class);
            resultIntent.putExtra("task", intent.getStringExtra("task"));


            List<Item> list = Item.getListbyTaskName(intent.getStringExtra("task"));
            for (Item j : list) {
                if (j.getImageUri() != null) {
                    String ImageUri = j.getImageUri();

                    resultIntent.putExtra("imageUri", ImageUri);
                }
                resultIntent.putExtra("Notes", j.getNotes());
            }
// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
            mNotificationManager.notify(mId, notification);
        }
    }



    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if(!checkPermission())
            return;
        Location lastlocation = LocationServices.FusedLocationApi.getLastLocation(mclient);
        if(lastlocation==null)
        {

            return;
        }
        double la=lastlocation.getLatitude();
        double lo=lastlocation.getLongitude();
        startLocationUpdate();




    }

    private void startLocationUpdate() {
        final LocationRequest lr=LocationRequest.create().setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(2000);
        if(!checkPermission())
            return;
        LocationServices.FusedLocationApi.requestLocationUpdates(mclient,lr,NotificationService.this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    Location bestlocation=null;
    @Override
    public void onLocationChanged(Location location) {
        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        PendingIntent pi1=PendingIntent.getService(this, 0, intent1, 0);
        int i=0;
        if(bestlocation==null||bestlocation.getAccuracy()>location.getAccuracy())
        {
            bestlocation=location;
            i++;
        }
        if(bestlocation.getAccuracy()<100||i>10){
            i=0;
            LocationServices.FusedLocationApi.removeLocationUpdates(mclient,this);
            if( bestlocation.distanceTo(desiredLocation)<3000)
        {
            Log.i("result","won");
            alarmManager.cancel(pi1);
            mclient.disconnect();
            ////////FOR NOTIFICATION
            Intent dismissIntent = new Intent(this, mediaService1.class);
            dismissIntent.setAction("dismiss");
            PendingIntent piDismiss = PendingIntent.getService(this, 0, dismissIntent, 0);

            Intent snoozeIntent = new Intent(this, mediaService1.class);
            snoozeIntent.setAction("snooze");
            PendingIntent piSnooze = PendingIntent.getService(this, 0, snoozeIntent, 0);
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_timer_black_24dp)
                            .setContentTitle("Remind Me")
                            .setContentText(intent1.getStringExtra("task"))
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(intent1.getStringExtra("task")))
                            .addAction(R.drawable.ic_cancel_black_24dp,
                                    "Dismiss", piDismiss)
                            .addAction(R.drawable.ic_snooze_black_24dp,
                                    "Snooze", piSnooze);

// Creates an explicit intent for an Activity in your app
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            mBuilder.setSound(alarmSound);
            startService(new Intent(this, mediaService.class));

            Notification notification = mBuilder.build();

            long[] vibrate = {500, 500, 500, 500, 500, 500, 500, 500, 500};
            notification.vibrate = vibrate;
            notification.flags |= Notification.FLAG_INSISTENT;
            //notification.defaults |= Notification.DEFAULT_VIBRATE;
            Intent resultIntent = new Intent(this, listActivity.class);
            resultIntent.putExtra("task", intent1.getStringExtra("task"));


            List<Item> list = Item.getListbyTaskName(intent1.getStringExtra("task"));
            for (Item j : list) {
                if (j.getImageUri() != null) {
                    String ImageUri = j.getImageUri();

                    resultIntent.putExtra("imageUri", ImageUri);
                }
                resultIntent.putExtra("Notes", j.getNotes());
            }

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

            stackBuilder.addParentStack(MainActivity.class);

            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(mId, notification);
        }

        }

    }


    public boolean checkPermission()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        }
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
