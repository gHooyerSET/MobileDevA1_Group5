/*
 * FILE          : FileReceiver.java
 * PROJECT       : PROG3150 - Assignment #1 - Trip Planner
 * FIRST VERSION : 2022-04-10
 * PROGRAMMER    : Gerritt Hooyer
 * DESCRIPTION   : The broadcast receiver that starts the file service.
 */
package com.example.tripplanner_a1_prog3150;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* NOTE: While Igor's examples were used as a reference,
* this code was more or less written from scratch */

/*
 * TITLE : Week 8 - Services Examples
 * AUTHOR : Igor Pustylnick
 * DATE : 2011-09-14
 * VERSION : 1.7.11
 * AVAILABIILTY : https://conestoga.desire2learn.com/d2l/le/content/563407/viewContent/11676574/View
 */
/*
 * TITLE : Week 9 - Receivers, Tabs and Adapters Examples
 * AUTHOR : Igor Pustylnick
 * DATE : 2011-09-14
 * VERSION : 1.7.11
 * AVAILABIILTY : https://conestoga.desire2learn.com/d2l/le/content/563407/viewContent/11676574/View
 */
public class FileReceiver extends BroadcastReceiver {

    public final static String FILE_BROADCAST = "com.example.tripplanner_a1_prog3150.FILE_SERVICE";


    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        Trip trip = (Trip)intent.getSerializableExtra("trip");

        //Create the intent and start the service
        Intent fileServiceIntent = new Intent(context,FileService.class);
        fileServiceIntent.putExtra("trip",trip);


        if(message.equals("Start"))
        {
            context.startService(fileServiceIntent);
            Log.d("TripPlanner","FileService started.");
        }
        else if (message.equals("Stop"))
        {
            context.stopService(fileServiceIntent);
            Log.d("TripPlanner","FileService stopped.");
        }
        else
        {
            throw new RuntimeException("Invalid message sent to FileReceiver");
        }
    }
}