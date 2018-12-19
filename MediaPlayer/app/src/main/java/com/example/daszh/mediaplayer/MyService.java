package com.example.daszh.mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class MyService extends Service {
    public MediaPlayer mediaPlayer;
    public mybinder binder = new mybinder();
    public MyService() {
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    public class mybinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
        @Override
        public boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    } else {
                        mediaPlayer.start();
                    }
                    break;
                case 2:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.seekTo(0);
                        mediaPlayer.pause();
                    }
                    break;
            }
            return super.onTransact(code, data, reply, flags);
        }
    }
}
