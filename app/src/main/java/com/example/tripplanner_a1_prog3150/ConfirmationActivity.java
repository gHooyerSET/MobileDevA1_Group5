/*
 * FILE          : ConfirmationActivity.java
 * PROJECT       : PROG3150 - Assignment #1 - Trip Planner
 * FIRST VERSION : 2022-01-27
 * PROGRAMMER    : Gerritt Hooyer
 * DESCRIPTION   : The code behind for the confirmation page.
 */
package com.example.tripplanner_a1_prog3150;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmationActivity extends AppCompatActivity {

    //Create variables
    private TextView confirmationOriginTextView;
    private TextView confirmationDestinationTextView;
    private TextView confirmationTotalCostTextView;
    private Button newTripButton;
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
        //Set textview text
        confirmationTotalCostTextView.setText("$" + String.format("%.02f",trip.getTotalCost()));
        confirmationOriginTextView.setText(trip.getOrigin());
        confirmationDestinationTextView.setText(trip.getDestination());
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
    }
}