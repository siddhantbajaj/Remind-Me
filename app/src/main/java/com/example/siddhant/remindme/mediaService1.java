package com.example.siddhant.remindme;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class mediaService1 extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_FOO = "com.example.siddhant.remindme.action.FOO";
    public static final String ACTION_BAZ = "com.example.siddhant.remindme.action.BAZ";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "com.example.siddhant.remindme.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "com.example.siddhant.remindme.extra.PARAM2";

    public mediaService1() {
        super("mediaService1");
    }
    MediaPlayer mMediaPlayer=new MediaPlayer();


    @Override
    protected void onHandleIntent(Intent intent) {
        mediaService.mMediaPlayer.stop();
        NotificationService.mNotificationManager.cancel(1);
      stopService(new Intent(this,mediaService.class));
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//
//        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        mMediaPlayer = MediaPlayer.create(this, alarmSound);
//
//        mMediaPlayer.setLooping(true);
//        if(intent.getAction()!=null)
//        {
//           // stopForeground(true);
//            mMediaPlayer.stop();
//            Log.i("stop","unable");
//        }
//        else {
//            Log.i("stop","enable");
//            mMediaPlayer.start();
//        }
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
}
