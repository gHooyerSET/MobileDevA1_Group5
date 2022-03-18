package com.example.tripplanner_a1_prog3150;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/*  -- Class Header Comment
    Name    :    ReviewTripActivity
    Purpose :    To handle all code behind functionality for the Purchase Review Trip Activity
    */
public class ReviewTripActivity extends MenuActivity {

    private Trip trip;
    private Button cancelButton;
    private Button orderButton;
    private TextView originTextView;
    private TextView destinationTextView;
    private TextView tripGoersTextView;
    private TextView ticketCostTextView;
    private TextView totalTicketCostTextView;
    private TextView hotelCostTextView;
    private TextView hotelNightsTextView;
    private TextView amenitiesCostTextView;
    private TextView totalHotelCostTextView;
    private TextView totalTripCostTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_trip);

        trip = (Trip)getIntent().getSerializableExtra("trip");

        cancelButton = findViewById(R.id.cancelTripOrderButton);
        orderButton = findViewById(R.id.orderTripButton);
        originTextView = findViewById(R.id.reviewOriginTextView);
        destinationTextView = findViewById(R.id.reviewDestinationTextView);
        tripGoersTextView = findViewById(R.id.reviewNumPeopleTextView);
        ticketCostTextView = findViewById(R.id.reviewIndTicketPriceTextView);
        totalTicketCostTextView = findViewById(R.id.reviewTotalTicketPriceTextView);
        hotelCostTextView = findViewById(R.id.reviewHotelCostNightTextView);
        hotelNightsTextView = findViewById(R.id.reviewNumNightsTextView);
        amenitiesCostTextView = findViewById(R.id.reviewAmenitiesCostTextView);
        totalHotelCostTextView = findViewById(R.id.reviewHotelCostTextView);
        totalTripCostTextView = findViewById(R.id.reviewTotalCostTextView);

        originTextView.setText(trip.getOrigin());
        destinationTextView.setText(trip.getDestination());
        tripGoersTextView.setText(String.format("%d",trip.getTripGoers()));
        ticketCostTextView.setText("$" + String.format("%.02f",trip.getTicketPrice()));
        totalTicketCostTextView.setText("$" + String.format("%.02f",trip.getTotalTicketCost()));
        hotelCostTextView.setText("$" + String.format("%.02f",trip.getHotelCost()));
        hotelNightsTextView.setText(String.format("%d",trip.getNights()));
        amenitiesCostTextView.setText("$" + String.format("%.02f",trip.getAmenitiesCost()));
        totalHotelCostTextView.setText("$" + String.format("%.02f",trip.getTotalHotelCost()));
        totalTripCostTextView.setText("$" + String.format("%.02f",trip.getTotalCost()));

        orderButton.setOnClickListener(view -> {
            /*
             *   TITLE : Start new activity on button click
             *   AUTHOR : Denis Kolodin & 'Emmanuel'
             *   DATE : 2022-02-07
             *   VERSION : N/A
             *   AVAILABILITY : https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
             */
            Intent nextScreenIntent = new Intent(ReviewTripActivity.this,ConfirmationActivity.class);
            /*
             *   TITLE : Sending objects between activities
             *   AUTHOR : 'Sridhar'
             *   DATE : 2017-10-18
             *   VERSION : N/A
             *   AVAILABILITY : https://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
             */
            nextScreenIntent.putExtra("trip", trip);
            ReviewTripActivity.this.startActivity(nextScreenIntent);
        });

        cancelButton.setOnClickListener(view->{
            /*
             *   TITLE : Start new activity on button click
             *   AUTHOR : Denis Kolodin & 'Emmanuel'
             *   DATE : 2022-02-07
             *   VERSION : N/A
             *   AVAILABILITY : https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
             */
            Intent nextScreenIntent = new Intent(ReviewTripActivity.this,MainActivity.class);
            /*
             *   TITLE : Sending objects between activities
             *   AUTHOR : 'Sridhar'
             *   DATE : 2017-10-18
             *   VERSION : N/A
             *   AVAILABILITY : https://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
             */
            nextScreenIntent.putExtra("trip", trip);
            ReviewTripActivity.this.startActivity(nextScreenIntent);
        });
    }
}