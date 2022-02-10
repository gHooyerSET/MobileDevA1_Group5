/*
 * FILE          : PurchaseTicketActivity.java
 * PROJECT       : PROG3150 - Assignment #1 - Trip Planner
 * FIRST VERSION : 2022-01-27
 * PROGRAMMER    : Waleed Ahmed
 * DESCRIPTION   : The code behind for the Purchase Ticket Activity.
 */
package com.example.tripplanner_a1_prog3150;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class PurchaseTicketActivity extends AppCompatActivity {

    private Trip trip;
    private TextView destinationSelectionTextView;
    private TextView originSelectionTextView;
    private Button nextScreenButton2;

    // ticket price variables
    TextView individualPrice;
    TextView totalPrice;
    float ticketPrice = 100.00F;
    float totalTicketPrice;

    // switch variables
    Switch mySwitch;

    // seekBar variables
    SeekBar seekBar;
    TextView textView;

    int max = 3;
    int step = 1;
    int progress = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_ticket);

        // get origin and destination from last page
        trip = (Trip)getIntent().getSerializableExtra("trip");
        destinationSelectionTextView = findViewById(R.id.destinationSelectionTextView);
        originSelectionTextView = findViewById(R.id.originSelectionTextView);

        destinationSelectionTextView.setText(trip.getDestination());
        originSelectionTextView.setText(trip.getOrigin());

        // next page button
        nextScreenButton2 = findViewById(R.id.nextScreenButton2);

        // set individual ticket price
        individualPrice = (TextView) findViewById(R.id.indTicketPriceTextView);
        individualPrice.setText("$"+ticketPrice);

        // create switch
        mySwitch = (Switch) findViewById(R.id.switch1);

        // switch toggle
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    ticketPrice = 200.00F;
                    individualPrice.setText("$"+String.format("%.02f",ticketPrice));
                    CalculateTotal(progress, ticketPrice);
                }
                else
                {
                    ticketPrice = 100.00F;
                    individualPrice.setText("$"+String.format("%.02f",ticketPrice));
                    CalculateTotal(progress, ticketPrice);
                }
            }
        });

        // create seekBar and textView
        seekBar = (SeekBar) findViewById(R.id.peopleSeekBar);
        textView = (TextView) findViewById(R.id.numPeopleTextView);

        /*
         *   TITLE : Changing step values in seekbar?
         *   AUTHOR : Dharmendra
         *   DATE : 2022-02-09
         *   VERSION : N/A
         *   AVAILABILITY : https://stackoverflow.com/questions/7329166/changing-step-values-in-seekbar
         */
        // set max and progress of seekbar
        seekBar.setMax(max);
        seekBar.setProgress(0);

        // seekBar change event
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i + 1;
                textView.setText(""+progress);
                CalculateTotal(progress, ticketPrice);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        nextScreenButton2.setOnClickListener(view -> {
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
            trip.setTripGoers(progress);
            trip.setTicketPrice(ticketPrice);
            nextScreenIntent.putExtra("trip", trip);
            PurchaseTicketActivity.this.startActivity(nextScreenIntent);
        });
    }

    public void CalculateTotal(int numPeople, float indPrice)
    {
        totalPrice = (TextView) findViewById(R.id.totalTicketPriceTextView);
        totalTicketPrice = numPeople*indPrice;
        totalPrice.setText("$"+String.format("%.02f",totalTicketPrice));
    }

}