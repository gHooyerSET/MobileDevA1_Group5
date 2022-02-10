package com.example.tripplanner_a1_prog3150;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ReviewTripActivity extends AppCompatActivity {

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
        totalHotelCostTextView = findViewById(R.id.reviewHotelCostTextView);
        totalTripCostTextView = findViewById(R.id.reviewTotalCostTextView);

        originTextView.setText(trip.getOrigin());
        destinationTextView.setText(trip.getDestination());
        tripGoersTextView.setText(String.format("%d",trip.getTripGoers()));
        ticketCostTextView.setText("$" + String.format("%.02f",trip.getTicketPrice()));
        totalTicketCostTextView.setText("$" + String.format("%.02f",trip.getTotalTicketCost()));
        hotelCostTextView.setText("$" + String.format("%.02f",trip.getHotelCost()));
        hotelNightsTextView.setText(String.format("%d",trip.getNights()));
        totalHotelCostTextView.setText("$" + String.format("%.02f",trip.getTotalHotelCost()));
        totalTripCostTextView.setText("$" + String.format("%.02f",trip.getTotalCost()));
    }
}