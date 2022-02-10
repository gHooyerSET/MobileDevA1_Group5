/*
 * FILE          : MainActivity.java
 * PROJECT       : PROG3150 - Assignment #1 - Trip Planner
 * FIRST VERSION : 2022-01-27
 * PROGRAMMER    : Gerritt Hooyer
 * DESCRIPTION   : The code behind the Trip Planner app.
 */
package com.example.tripplanner_a1_prog3150;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

public class PurchaseTicketActivity extends AppCompatActivity {

    private Trip trip;
    private TextView destinationSelectionTextView;
    private TextView originSelectionTextView;
    private Button nextScreenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_ticket);

        //Initialize button
        nextScreenButton = findViewById(R.id.nextScreenButton2);

        trip = (Trip)getIntent().getSerializableExtra("trip");
        destinationSelectionTextView = findViewById(R.id.destinationSelectionTextView);
        originSelectionTextView = findViewById(R.id.originSelectionTextView);

        destinationSelectionTextView.setText(trip.getDestination());
        originSelectionTextView.setText(trip.getOrigin());

        //Button Listener
        nextScreenButton.setOnClickListener(view -> {
            /*
             *   TITLE : Start new activity on button click
             *   AUTHOR : Denis Kolodin & 'Emmanuel'
             *   DATE : 2022-02-07
             *   VERSION : N/A
             *   AVAILABILITY : https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
             */
            Intent nextScreenIntent = new Intent(PurchaseTicketActivity.this,PurchaseHotelRoomActivity.class);
            /*
             *   TITLE : Sending objects between activities
             *   AUTHOR : 'Sridhar'
             *   DATE : 2017-10-18
             *   VERSION : N/A
             *   AVAILABILITY : https://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
             */
            nextScreenIntent.putExtra("trip",trip);
            PurchaseTicketActivity.this.startActivity(nextScreenIntent);
        });
    }
}