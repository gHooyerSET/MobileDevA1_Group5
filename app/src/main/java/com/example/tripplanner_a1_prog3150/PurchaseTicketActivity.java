package com.example.tripplanner_a1_prog3150;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PurchaseTicketActivity extends AppCompatActivity {

    private Trip trip;
    private TextView destinationSelectionTextView;
    private TextView originSelectionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_ticket);

        trip = (Trip)getIntent().getSerializableExtra("trip");
        destinationSelectionTextView = findViewById(R.id.destinationSelectionTextView);
        originSelectionTextView = findViewById(R.id.originSelectionTextView);

        destinationSelectionTextView.setText(trip.getDestination());
        originSelectionTextView.setText(trip.getOrigin());
    }
}