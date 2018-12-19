package com.example.daszh.mediaplayer;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.util.Consumer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Emitter;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    MyService myserv;
    MediaPlayer mediaPlayer;
    ImageView disk;
    ObjectAnimator oa;
    SeekBar progress;
    TextView currenttime;
    TextView totaltime;
    TextView title;
    TextView singer;
    //Thread thread;
    Disposable disposable;
    Observable observable;
    Observer observer = new Observer() {
        @Override
        public void onSubscribe(Disposable d) {
            disposable = d;
        }

        @Override
        public void onNext(Object o) {
            progress.setProgress(mediaPlayer.getCurrentPosition());
            currenttime.setText(formattime(mediaPlayer.getCurrentPosition()));
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
                disposable = null;
            }
        }
    };
    /*Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress.setProgress(msg.what);
            currenttime.setText(formattime(msg.what));
        }
    };*/

    private String formattime(int length) {
        Date date = new Date(length);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        return simpleDateFormat.format(date);
    }



    /*class updateseekbar implements Runnable {
        @Override
        public void run() {
            while (mediaPlayer != null && mediaPlayer.isPlaying()) {
                handler.sendEmptyMessage(mediaPlayer.getCurrentPosition());
                try {
                    Thread.sleep(80);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }*/

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myserv = ((MyService.mybinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myserv = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disk = findViewById(R.id.profile_image);
        progress = findViewById(R.id.bar);
        currenttime = findViewById(R.id.currenttime);
        totaltime = findViewById(R.id.totaltime);

        title = findViewById(R.id.title);
        singer = findViewById(R.id.ins);
        myserv = new MyService();
        mediaPlayer = myserv.mediaPlayer;
        Intent in = new Intent(this, MyService.class);
        bindService(in, serviceConnection, BIND_AUTO_CREATE);
        try {
            mediaPlayer.reset();
            mediaPlayer = MediaPlayer.create(this, R.raw.music);
        } catch (Exception e) {
            e.printStackTrace();
        }
        oa = ObjectAnimator.ofFloat(disk, "rotation", 0f, 360f);
        oa.setDuration(20000);
        oa.setInterpolator(new LinearInterpolator());
        oa.setRepeatCount(-1);
        oa.setRepeatMode(ValueAnimator.RESTART);
        oa.start();
        play();
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    public void play() {
        try {
            mediaPlayer.start();
            progress.setMax(mediaPlayer.getDuration());
            totaltime.setText(formattime(mediaPlayer.getDuration()));
            //thread = new Thread(new updateseekbar());
            //thread.start();
            observable = Observable.create(new ObservableOnSubscribe() {
                @Override
                public void subscribe(ObservableEmitter e) throws Exception {
                    while (mediaPlayer.isPlaying()) {
                        try {
                            Thread.sleep(80);
                        } catch (Exception ee) {
                            ee.printStackTrace();
                            if (!e.isDisposed()) {
                                e.onError(ee);
                            }
                        }
                        if ((disposable != null && !disposable.isDisposed()) || disposable == null) {
                            e.onNext(mediaPlayer.getCurrentPosition());
                        }
                    }
                    e.onComplete();
                }
            });
            observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
            progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress);
                    }
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        unbindService(serviceConnection);
        super.onDestroy();
    }

    public void onbackclick(View view) {
        unbindService(serviceConnection);
        Intent in = new Intent(MainActivity.this, MyService.class);
        stopService(in);

        try {
            MainActivity.this.finish();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickoption(View view) {
        ImageButton im = findViewById(R.id.operation);
        if (mediaPlayer.isPlaying()) {
            //thread.interrupt();
            im.setBackgroundResource(R.drawable.play);
            mediaPlayer.pause();
            oa.pause();
        } else {
            im.setBackgroundResource(R.drawable.pause);
            play();
            //thread = new Thread(new updateseekbar());
            //thread.start();
            if (!oa.isPaused()) {
                oa = ObjectAnimator.ofFloat(disk, "rotation", 0f, 360f);
                oa.setDuration(20000);
                oa.setInterpolator(new LinearInterpolator());
                oa.setRepeatCount(-1);
                oa.setRepeatMode(ValueAnimator.RESTART);
                oa.start();
            } else {
                oa.resume();
            }

        }
    }

    public void stop(View view) {
        //thread.interrupt();
        mediaPlayer.pause();
        mediaPlayer.seekTo(0);
        ImageButton im = findViewById(R.id.operation);
        im.setBackgroundResource(R.drawable.play);
        currenttime.setText(formattime(0));
        oa.end();
        progress.setProgress(0);
    }

    public void selectmusic(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent in) {
        if (resultcode == Activity.RESULT_OK ) {
            ImageButton im = findViewById(R.id.operation);
            im.setBackgroundResource(R.drawable.play);
            progress.setProgress(0);
            currenttime.setText(formattime(0));

            oa.end();
            mediaPlayer.release();
            Uri uri = in.getData();
            try {
                mediaPlayer = MediaPlayer.create(this, uri);
                if (mediaPlayer.isPlaying()) mediaPlayer.pause();
                //mediaPlayer.setDataSource(String.valueOf(uri));
            }catch (Exception e) {
                e.printStackTrace();
            }
            totaltime.setText(formattime(mediaPlayer.getDuration()));
            progress.setMax(mediaPlayer.getDuration());
            MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
            try {
                metadataRetriever.setDataSource(this, uri);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            String t = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            if (null != t) title.setText(t);
            String a = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            if (a != null) singer.setText(a);
            byte[] data = metadataRetriever.getEmbeddedPicture();
            //if (data != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                disk.setImageBitmap(bmp);
            //}
        }
    }



}
