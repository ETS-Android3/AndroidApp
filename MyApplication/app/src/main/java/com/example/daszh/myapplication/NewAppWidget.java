package com.example.daszh.myapplication;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;


/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    static String wdgfood;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

        RemoteViews updtview = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        Intent i = new Intent(context, DetailActivity.class);
        i.putExtra("foodName", wdgfood);
        PendingIntent pi = PendingIntent.getActivity(context, 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
        updtview.setOnClickPendingIntent(R.id.wdgimg, pi);
        updtview.setOnClickPendingIntent(R.id.appwidget_text, pi);
        ComponentName me = new ComponentName(context, NewAppWidget.class);
        appWidgetManager.updateAppWidget(me, updtview);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them


        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            RemoteViews updtview = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
            Intent i = new Intent(context, DetailActivity.class);
            i.putExtra("foodName", wdgfood);
            PendingIntent pi = PendingIntent.getActivity(context, 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
            updtview.setOnClickPendingIntent(R.id.wdgimg, pi);
            updtview.setOnClickPendingIntent(R.id.appwidget_text, pi);
            ComponentName me = new ComponentName(context, NewAppWidget.class);
            appWidgetManager.updateAppWidget(me, updtview);
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        AppWidgetManager awm = AppWidgetManager.getInstance(context);
        // Enter relevant functionality for when the first widget is created
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        Intent ii = new Intent(context, NewMainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 1, ii, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.wdgimg, pi);
        views.setOnClickPendingIntent(R.id.appwidget_text, pi);


        ComponentName me = new ComponentName(context, NewAppWidget.class);
        awm.updateAppWidget(me, views);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context c, Intent i) {
        super.onReceive(c, i);
        AppWidgetManager awm = AppWidgetManager.getInstance(c);
        Bundle b = i.getExtras();
        if (i.getAction().equals("com.example.daszh.myapplication.MyWStaticFilter")) {
            RemoteViews views = new RemoteViews(c.getPackageName(), R.layout.new_app_widget);
            wdgfood = b.getString("FN");
            String out = "今日推荐  " + wdgfood;
            views.setTextViewText(R.id.appwidget_text, out);
            Intent ii = new Intent(c, DetailActivity.class);
            ii.putExtra("foodName", wdgfood);
            PendingIntent pi = PendingIntent.getActivity(c, 1, ii, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.wdgimg, pi);
            views.setOnClickPendingIntent(R.id.appwidget_text, pi);
            ComponentName me = new ComponentName(c, NewAppWidget.class);
            awm.updateAppWidget(me, views);

        } else {
            RemoteViews views = new RemoteViews(c.getPackageName(), R.layout.new_app_widget);
            wdgfood = b.getString("FN");
            String out = "已收藏  " + wdgfood;
            views.setTextViewText(R.id.appwidget_text, out);

            Intent ii = new Intent(c, NewMainActivity.class);
            ii.putExtra("Tocol", true);
            //ii.putExtra("foodName", "Tocoll");
            PendingIntent pi = PendingIntent.getActivity(c, 1, ii, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.wdgimg, pi);
            views.setOnClickPendingIntent(R.id.appwidget_text, pi);


            ComponentName me = new ComponentName(c, NewAppWidget.class);
            awm.updateAppWidget(me, views);
        }

    }

}

