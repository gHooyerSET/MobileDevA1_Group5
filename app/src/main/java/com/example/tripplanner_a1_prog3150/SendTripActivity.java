/*
 * FILE          : SendTripActivity.java
 * PROJECT       : PROG3150 - Assignment #1 - Trip Planner
 * FIRST VERSION : 2022-04-11
 * PROGRAMMER    : Patrick Cho
 * DESCRIPTION   : The code behind for the confirmation page.
 */

package com.example.tripplanner_a1_prog3150;

import android.os.Bundle;
import android.widget.Spinner;

public class SendTripActivity extends MenuActivity {
    //On this page, we will be able to select from dropdown list of all contacts
        //There will be two buttons, send - or go back
            //Send will not do anything for now - out of assignment scope
            //Go back will return to previous page

    //Create variables
    private Spinner contactSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_trip);

        //Initialize Variable

    }
}
