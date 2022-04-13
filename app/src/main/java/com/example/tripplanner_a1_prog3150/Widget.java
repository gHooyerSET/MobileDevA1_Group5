/*
 * FILE          : Widget.java
 * PROJECT       : PROG3150 - Assignment #1 - Trip Planner
 * FIRST VERSION : 2022-04-12
 * PROGRAMMER    : Nathan Domingo
 * DESCRIPTION   : The code behind the desktop widget.
 */
package com.example.tripplanner_a1_prog3150;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class Widget extends AppWidgetProvider {

    // Ref: https://www.youtube.com/watch?v=xGQJg31TPtU&list=PLrnPJCHvNZuDCoET8jL2VK4YVRNhVEy0K&index=1
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
