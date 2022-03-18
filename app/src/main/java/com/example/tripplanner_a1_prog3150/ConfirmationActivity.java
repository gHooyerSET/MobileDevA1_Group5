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

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class ConfirmationActivity extends MenuActivity {

    //Create variables
    private TextView confirmationOriginTextView;
    private TextView confirmationDestinationTextView;
    private TextView confirmationTotalCostTextView;
    private Button newTripButton;
    private Trip trip;
    private TripDB tripDB;

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


        // Use a toast to notify the user about the asynchronous operation beginning
        //Create the tripDB object
        tripDB = new TripDB(this);
        // Start the asynchronous task to create the database entry
        // and create a 'receipt' file as an asynchronous operation
        new ASyncDatabaseCreator().execute(trip,tripDB);

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

    public class ASyncFileWriter extends AsyncTask
    {

        @Override
        protected Object doInBackground(@NonNull Object[] params) {
            String fileString = "";
            byte[] fileBuffer;
            String filename = "";
            // Create the file name
            filename = trip.getTripId() + "_" + trip.getDestination() + "_" + "trip.txt";
            // Create file data in the string
            fileString = "Trip ID: " + trip.getTripId() + "\n";
            fileString += "Origin: " + trip.getOrigin() + "\n";
            fileString += "Destination: " + trip.getDestination() + "\n";
            fileString += "Trip Goers: " + trip.getTripGoers() + "\n";
            fileString += "# of Nights: " + String.format("%.2f",trip.getNights()) + "\n";
            fileString += "Amenities Cost: $" + String.format("%.2f",trip.getAmenitiesCost()) + "\n";
            fileString += "Hotel Cost: $" + String.format("%.2f",trip.getHotelCost()) + " / night\n";
            fileString += "Total Hotel Cost: $" + String.format("%.2f",trip.getTotalHotelCost()) + "\n";
            fileString += "Ticket Price: $" + String.format("%.2f",trip.getTicketPrice()) + "\n";
            fileString += "Total Ticket Cost: $" + String.format("%.2f",trip.getTotalTicketCost()) + "\n";
            fileString += "Total Cost: $" + String.format("%.2f",trip.getTotalCost()) + "\n";
            try
            {
                // Create the output filestream
                FileOutputStream out = openFileOutput(filename, Context.MODE_PRIVATE);
                // Encode the string to bytes
                fileBuffer = fileString.getBytes(StandardCharsets.UTF_8);
                // Write the string
                out.write(fileBuffer,0,fileBuffer.length);
                // Close the file
                out.close();
            } catch (Exception e) {
                // Log the exception
                Log.d(String.format("%d",e.hashCode()),e.toString());
            }
            return null;
        }
    }

    public class ASyncDatabaseCreator extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                // Insert the trip
                tripDB.insertTrip(trip);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(Object o) {
            new ASyncFileWriter().execute(trip);
        }
    }
}