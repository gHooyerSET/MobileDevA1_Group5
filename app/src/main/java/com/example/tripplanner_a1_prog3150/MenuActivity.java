/*
 * FILE          : MenuActivity.java
 * PROJECT       : PROG3150 - Assignment #1 - Trip Planner
 * FIRST VERSION : 2022-03-06
 * PROGRAMMER    : Nathan Domingo
 * DESCRIPTION   : The code behind the Trip Planner app menu.
 */

package com.example.tripplanner_a1_prog3150;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    // Create options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    // Enable options
    @Override
    public boolean onPrepareOptionsMenu(@NonNull Menu menu) {

        MenuItem item_flight = menu.findItem(R.id.item_flight);
        MenuItem item_hotel = menu.findItem(R.id.item_hotel);
        MenuItem item_review = menu.findItem(R.id.item_review);

        if (MenuData.enableFlight) {
            item_flight.setEnabled(true);
        }
        if (MenuData.enableHotel) {
            item_hotel.setEnabled(true);
        }
        if (MenuData.enableReview) {
            item_review.setEnabled(true);
        }

        return true;
    }

    // Menu item logic
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_main:
                openMain();
                break;
            case R.id.item_flight:
                openFlight();
                break;
            case R.id.item_hotel:
                openHotel();
                break;
            case R.id.item_review:
                openCheckout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    // Open Menu Item
    public void openMain(){
        Trip trip = (Trip)getIntent().getSerializableExtra("trip");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("trip",trip);
        startActivity(intent);
    }
    public void openFlight(){
        Trip trip = (Trip)getIntent().getSerializableExtra("trip");
        Intent intent = new Intent(this, PurchaseTicketActivity.class);
        intent.putExtra("trip",trip);
        startActivity(intent);
    }
    public void openHotel(){
        Trip trip = (Trip)getIntent().getSerializableExtra("trip");
        Intent intent = new Intent(this, PurchaseHotelRoomActivity.class);
        intent.putExtra("trip",trip);
        startActivity(intent);
    }
    public void openCheckout(){
        Trip trip = (Trip)getIntent().getSerializableExtra("trip");
        Intent intent = new Intent(this, ReviewTripActivity.class);
        intent.putExtra("trip",trip);
        startActivity(intent);
    }
}
