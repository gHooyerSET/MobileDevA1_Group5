/*
 * FILE          : ConfirmationActivity.java
 * PROJECT       : PROG3150 - Assignment #1 - Trip Planner
 * FIRST VERSION : 2022-01-27
 * PROGRAMMER    : Gerritt Hooyer
 * DESCRIPTION   : The code behind for the confirmation page.
 */
package com.example.tripplanner_a1_prog3150;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/*
 * TITLE : Week 9 - Receivers, Tabs and Adapters Examples
 * AUTHOR : Igor Pustylnick
 * DATE : 2011-09-14
 * VERSION : 1.7.11
 * AVAILABIILTY : https://conestoga.desire2learn.com/d2l/le/content/563407/viewContent/11676574/View
 */
public class ConfirmationActivity extends MenuActivity {


    //Create variables
    private TextView confirmationOriginTextView;
    private TextView confirmationDestinationTextView;
    private TextView confirmationTotalCostTextView;
    private Button newTripButton;
    private Button sendTripButton;
    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        //Initialize variables
        trip = (Trip)getIntent().getSerializableExtra("trip");
        confirmationOriginTextView = findViewById(R.id.confirmationOriginTextView);
        confirmationDestinationTextView = findViewById(R.id.confirmationDestinationTextView);
        confirmationTotalCostTextView = findViewById(R.id.confirmationTripCostTextView);
        newTripButton = findViewById(R.id.newTripButton);
        sendTripButton = findViewById(R.id.sendTripButton);
        //Set textview text
        confirmationTotalCostTextView.setText("$" + String.format("%.02f",trip.getTotalCost()));
        confirmationOriginTextView.setText(trip.getOrigin());
        confirmationDestinationTextView.setText(trip.getDestination());


        //Create the intent and send the broadcast message
        Intent fileReceiverIntent = new Intent(FileReceiver.FILE_BROADCAST);
        //Add the extras
        fileReceiverIntent.putExtra("message","Start");
        fileReceiverIntent.putExtra("trip",trip);
        //Then send the intent
        sendBroadcast(fileReceiverIntent);

        //Set up event handler
        //This button sends us back to the MainActivity screen
        newTripButton.setOnClickListener(view -> {
            /*
             *   TITLE : Start new activity on button click
             *   AUTHOR : Denis Kolodin & 'Emmanuel'
             *   DATE : 2022-02-07
             *   VERSION : N/A
             *   AVAILABILITY : https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
             */
            Intent nextScreenIntent = new Intent(ConfirmationActivity.this,MainActivity.class);
            /*
             *   TITLE : Sending objects between activities
             *   AUTHOR : 'Sridhar'
             *   DATE : 2017-10-18
             *   VERSION : N/A
             *   AVAILABILITY : https://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
             */
            ConfirmationActivity.this.startActivity(nextScreenIntent);
        });

        sendTripButton.setOnClickListener(view -> {
           Intent nextScreenIntent = new Intent( ConfirmationActivity.this, SendTripActivity.class);
           //Go to next page
           nextScreenIntent.putExtra("trip", trip);
           ConfirmationActivity.this.startActivity(nextScreenIntent);
        });
    }
}