/*
* FILE          : MainActivity.java
* PROJECT       : PROG3150 - Assignment #1 - Trip Planner
* FIRST VERSION : 2022-01-27
* PROGRAMMER    :
* DESCRIPTION   : The code behind the Trip Planner app.
*/

package com.example.tripplanner_a1_prog3150;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Create our variables
    private Spinner originSpinner;
    private Spinner destinationSpinner;
    private Button nextScreenButton;
    private int hideToastHack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize our variables
        originSpinner = findViewById(R.id.originSpinner);
        destinationSpinner = findViewById(R.id.destinationSpinner);
        nextScreenButton = findViewById(R.id.nextScreenButton);
        hideToastHack = 0;
        //Set up listeners
        originSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //When an item is selected in either spinner, we should check if they are the equal
                checkSelections();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //If nothing is selected, disable the nextScreenButton
                nextScreenButton.setEnabled(false);
            }
        });
        destinationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //When an item is selected in either spinner, we should check if they are the equal
                checkSelections();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //If nothing is selected, disable the nextScreenButton
                nextScreenButton.setEnabled(false);
            }
        });

    }

    public void checkSelections()
    {
        /*              hideToastHack - What it's doing                 */
        /* When we create our listeners in onCreate for our spinners,   */
        /* the event for onItemSelected fires. Since both spinners end  */
        /* up calling the same function, the easiest way to avoid this  */
        /* is to create a counter that counts up to 2, and only once it */
        /* reaches this number can it begin performing it's actual      */
        /* function, hence the following sequence                       */

        //Check if we've reached our required value
        if(hideToastHack >= 2)
        {
            //If both spinners have the same place selected, disable the nextScreenButton and display an error Toast
            if(originSpinner.getSelectedItem().toString().equals(destinationSpinner.getSelectedItem().toString()))
            {
                Toast.makeText(this,"Your destination and origin cannot be the same place.",Toast.LENGTH_LONG).show();
                nextScreenButton.setEnabled(false);
            }
            else
            {
                //Otherwise, enable the button
                nextScreenButton.setEnabled(true);
            }
        }
        //Otherwise, increment the hideToastHack value
        else
        {
            hideToastHack++;
        }
    }



}