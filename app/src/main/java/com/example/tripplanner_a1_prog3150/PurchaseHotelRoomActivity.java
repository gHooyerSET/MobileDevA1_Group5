/*
 * FILE          : PurchaseHotelRoomActivity.java
 * PROJECT       : PROG3150 - Assignment #1 - Trip Planner
 * FIRST VERSION : 2022-01-27
 * PROGRAMMER    : Patrick Cho and Nathan Domingo
 * DESCRIPTION   : The code behind for the hotel selection page.
 */
package com.example.tripplanner_a1_prog3150;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class PurchaseHotelRoomActivity extends MenuActivity {

    //Constants
    final private int beddingCost = 100;
    final private int champagneCost = 200;
    final private int jacuzziCost = 300;

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
    // Amenities
    private float amenitiesTotalCost;
    private CheckBox beddingCheckBox;
    private CheckBox champagneCheckBox;
    private CheckBox jacuzziCheckBox;
    ArrayList<String> amenities = new ArrayList();
    // Trip
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
        amenitiesTotalCost = 0;
        //Amenities
        beddingCheckBox = findViewById(R.id.beddingCheckBox);
        champagneCheckBox = findViewById(R.id.champagneCheckBox);
        jacuzziCheckBox = findViewById(R.id.jacuzziCheckBox);
        ArrayAdapter<String> amenitiesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, amenities);
        ListView amenitiesListView = findViewById(R.id.hotelCostListView);
        amenitiesListView.setAdapter(amenitiesAdapter);
        amenities.add("Room Cost $0");
        //This array contains the associated values to the hotel selection spinner
        hotelCosts = getResources().getStringArray(R.array.hotelArrayValues);
        trip = (Trip)getIntent().getSerializableExtra("trip");

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
                totalCost = (nightCount * nightCost) + amenitiesTotalCost;
                //https://stackoverflow.com/questions/2538787/how-to-print-a-float-with-2-decimal-places-in-java
                costNumberTextView.setText("$" + String.format("%.02f", totalCost));
                String totalCostString = String.format("Room Cost $%.02f", nightCount * nightCost);
                amenities.set(0, totalCostString);
                amenitiesAdapter.notifyDataSetChanged();
            }
        });


        //Amenities Listeners
        beddingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    amenitiesTotalCost += beddingCost;
                    amenities.add("Bedding $100.00");
                    amenitiesAdapter.notifyDataSetChanged();
                    costNumberTextView.setText("$" + String.format("%.02f", totalCost + amenitiesTotalCost));

                }
                else
                {
                    amenitiesTotalCost -= beddingCost;
                    amenities.remove("Bedding $100.00");
                    amenitiesAdapter.notifyDataSetChanged();
                    costNumberTextView.setText("$" + String.format("%.02f", totalCost + amenitiesTotalCost));
                }
            }
        });

        champagneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    amenitiesTotalCost += champagneCost;
                    amenities.add("Champagne Bar $200.00");
                    amenitiesAdapter.notifyDataSetChanged();
                    costNumberTextView.setText("$" + String.format("%.02f", totalCost + amenitiesTotalCost));
                }
                else
                {
                    amenitiesTotalCost -= champagneCost;
                    amenities.remove("Champagne Bar $200.00");
                    amenitiesAdapter.notifyDataSetChanged();
                    costNumberTextView.setText("$" + String.format("%.02f", totalCost + amenitiesTotalCost));
                }
            }
        });

        jacuzziCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    amenitiesTotalCost += jacuzziCost;
                    amenities.add("Poseidon Jacuzzi $300.00");
                    amenitiesAdapter.notifyDataSetChanged();
                    costNumberTextView.setText("$" + String.format("%.02f", totalCost + amenitiesTotalCost));
                }
                else
                {
                    amenitiesTotalCost -= jacuzziCost;
                    amenities.remove("Poseidon Jacuzzi $300.00");
                    amenitiesAdapter.notifyDataSetChanged();
                    costNumberTextView.setText("$" + String.format("%.02f", totalCost + amenitiesTotalCost));
                }
            }
        });

        //Button Listener
        nextScreenButton.setOnClickListener(view -> {
             //https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
            Intent nextScreenIntent = new Intent(PurchaseHotelRoomActivity.this,ReviewTripActivity.class);
            Intent menuIntent = new Intent(PurchaseHotelRoomActivity.this,MenuActivity.class);
             //https://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
            trip.setAmenitiesCost(amenitiesTotalCost);
            trip.setHotelCost(nightCost);
            trip.setNights(nightCount);
            MenuData.enableReview = true;
            nextScreenIntent.putExtra("trip",trip);
            menuIntent.putExtra("trip",trip);
            PurchaseHotelRoomActivity.this.startActivity(nextScreenIntent);
        });
    }
}