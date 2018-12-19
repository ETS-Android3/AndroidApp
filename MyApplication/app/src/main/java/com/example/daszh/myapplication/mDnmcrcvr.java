package com.example.daszh.myapplication;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

public class mDnmcrcvr extends BroadcastReceiver {
    String wdgfood;
    @Override
    public void onReceive(Context c, Intent i) {
        //if (i.getAction().equals("DNMCFLTR")) {
            Bundle b = i.getExtras();
            RemoteViews views = new RemoteViews(c.getPackageName(), R.layout.new_app_widget);
            wdgfood = b.getString("FN");
            views.setTextViewText(R.id.appwidget_text, wdgfood);
            Intent ii = new Intent(c, NewMainActivity.class);
            ii.putExtra("Tocol", true);
            PendingIntent pi = PendingIntent.getActivity(c, 1, ii, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.appwidget_text, pi);
            views.setOnClickPendingIntent(R.id.wdgimg, pi);
            AppWidgetManager.getInstance(c).updateAppWidget(new ComponentName(c.getApplicationContext(), NewAppWidget.class), views);
       // }
    }
}
