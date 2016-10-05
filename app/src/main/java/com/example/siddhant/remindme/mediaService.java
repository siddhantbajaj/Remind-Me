package com.example.siddhant.remindme;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class mediaService extends Service {
    public mediaService() {
    }
    static MediaPlayer mMediaPlayer = new MediaPlayer();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer = MediaPlayer.create(this, alarmSound);

        mMediaPlayer.setLooping(true);
        if(intent.getAction()!=null)
        {
            stopForeground(true);
            stopSelf();
            Log.i("stop","unable");
        }
        else {
            Log.i("stop","enable");
            mMediaPlayer.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
