package com.example.daszh.myapplication;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Random;

@TargetApi(28)
public class staticrcvr extends BroadcastReceiver {
    private static final String STATICACTION = "com.exanple.daszh.myapplication.MyStaticFilter";

    @Override
    public void onReceive(Context c, Intent i) {
        //Toast.makeText(c, "wtffffff", Toast.LENGTH_SHORT).show();

                Bundle b = i.getExtras();
                NotificationManager nm = (NotificationManager) c.getSystemService(c.NOTIFICATION_SERVICE);

                NotificationChannel nc = new NotificationChannel("c1", "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
                nm.createNotificationChannel(nc);
                Notification.Builder bdr = new Notification.Builder(c, "c1");


        if (i.getAction().equals(STATICACTION)) {
            Intent ji = new Intent(c, DetailActivity.class);
            ji.putExtra("foodName", b.getString("Foodname"));
            PendingIntent pj = PendingIntent.getActivity(c, 1, ji, PendingIntent.FLAG_UPDATE_CURRENT);

                bdr.setContentTitle("今日推荐")
                        .setContentText(b.getString("Foodname"))
                        .setSmallIcon(R.drawable.empty_star)
                        .setAutoCancel(true)
                        .setChannelId("c1")
                        .setContentIntent(pj)
                        .setTicker("今日推荐");
        } else {
            Intent ji = new Intent(c, NewMainActivity.class);
            ji.putExtra("Tocol", true);
            PendingIntent pj = PendingIntent.getActivity(c, 1, ji, PendingIntent.FLAG_CANCEL_CURRENT);

            bdr.setContentTitle("已收藏")
                    .setContentText(b.getString("Foodname"))
                    .setSmallIcon(R.drawable.empty_star)
                    .setAutoCancel(true)
                    .setChannelId("c1")
                    .setContentIntent(pj)
                    .setTicker("已收藏");
        }
        Notification ntf = bdr.build();
        nm.notify(0, ntf);

    }
}
