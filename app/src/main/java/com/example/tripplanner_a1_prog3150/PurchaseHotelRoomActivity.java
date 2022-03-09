/*
 * FILE          : PurchaseHotelRoomActivity.java
 * PROJECT       : PROG3150 - Assignment #1 - Trip Planner
 * FIRST VERSION : 2022-01-27
 * PROGRAMMER    : Patrick Cho
 * DESCRIPTION   : The code behind for the hotel selection page.
 */
package com.example.tripplanner_a1_prog3150;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class PurchaseHotelRoomActivity extends MenuActivity {
    //Create our variables
    private Spinner hotelSpinner;
    private SeekBar hotelNightBar;
    private Button nextScreenButton;
    private TextView nightNumberTextView;
    private TextView costNumberTextView;
    private int nightCount;
    private float nightCost;
    private float totalCost;
    private String[] hotelCosts;
    private int spinnerPosition;
    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_hotel_room);
        //Initialize our variables
        hotelSpinner = findViewById(R.id.hotelSpinner);
        hotelNightBar = findViewById(R.id.seekBar);
        nextScreenButton = findViewById(R.id.nextScreenButton3);
        nextScreenButton.setEnabled(false); //Initialize as false
        nightNumberTextView = findViewById(R.id.nightNumberTextView);
        costNumberTextView = findViewById(R.id.costNumberTextView);
        nightNumberTextView.setText(hotelNightBar.getProgress() + "/" + hotelNightBar.getMax());
        trip = (Trip)getIntent().getSerializableExtra("trip");
        //This array contains the associated values to the hotel selection spinner
        hotelCosts = getResources().getStringArray(R.array.hotelArrayValues);

        //Set up listeners

        //Listener for Hotel Selector
        //https://stackoverflow.com/questions/1947933/how-to-get-spinner-value
        hotelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerPosition = hotelSpinner.getSelectedItemPosition();
                nightCost = Float.valueOf(hotelCosts[spinnerPosition]);
                totalCost = nightCount * nightCost;
                costNumberTextView.setText("$" + String.format("%.02f", totalCost));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //You must select a hotel to proceed
                nextScreenButton.setEnabled(false);
            }
        });

        //Listener for Seek Bar - will update night count below and also update price at bottom of page
        //https://www.tutlane.com/tutorial/android/android-seekbar-with-examples
        hotelNightBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                nightCount = progress;
                if(progress == 0)
                {
                    nextScreenButton.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"You must select more than 0 nights.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    nextScreenButton.setEnabled(true);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                nightNumberTextView.setText(nightCount + "/" + seekBar.getMax());
                //Here, we are going to calculate the total cost, then change the text of the cost
                totalCost = nightCount * nightCost;
                //https://stackoverflow.com/questions/2538787/how-to-print-a-float-with-2-decimal-places-in-java
                costNumberTextView.setText("$" + String.format("%.02f", totalCost));
            }
        });

        //Button Listener
        nextScreenButton.setOnClickListener(view -> {
            /*
             *   TITLE : Start new activity on button click
             *   AUTHOR : Denis Kolodin & 'Emmanuel'
             *   DATE : 2022-02-07
             *   VERSION : N/A
             *   AVAILABILITY : https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
             */
            Intent nextScreenIntent = new Intent(PurchaseHotelRoomActivity.this,ReviewTripActivity.class);
            Intent menuIntent = new Intent(PurchaseHotelRoomActivity.this,MenuActivity.class);
            /*
             *   TITLE : Sending objects between activities
             *   AUTHOR : 'Sridhar'
             *   DATE : 2017-10-18
             *   VERSION : N/A
             *   AVAILABILITY : https://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
             */
            trip.setHotelCost(nightCost);
            trip.setNights(nightCount);
            MenuData.enableReview = true;
            nextScreenIntent.putExtra("trip",trip);
            menuIntent.putExtra("trip",trip);
            PurchaseHotelRoomActivity.this.startActivity(nextScreenIntent);
        });




    }
}