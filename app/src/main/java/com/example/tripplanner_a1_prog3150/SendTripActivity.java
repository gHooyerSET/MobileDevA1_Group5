/*
 * FILE          : SendTripActivity.java
 * PROJECT       : PROG3150 - Assignment #1 - Trip Planner
 * FIRST VERSION : 2022-04-11
 * PROGRAMMER    : Patrick Cho
 * DESCRIPTION   : The code behind for the confirmation page.
 */

package com.example.tripplanner_a1_prog3150;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class SendTripActivity extends MenuActivity {
    static boolean isInit = true;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS=1;
    //On this page, we will be able to select from dropdown list of all contacts
        //There will be two buttons, send - or go back
            //Send will not do anything for now - out of assignment scope
            //Go back will return to previous page

    //Create variables
    private Spinner contactSpinner;
    private Button sendTripToContactButton;
    private Button goBackButton;
    private Button refreshContactsListButton;
    private AlertDialog permissionDialog;
    private ArrayList<String> nameArray;
    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_trip);

        //Initialize Variable
        contactSpinner = findViewById(R.id.contactSpinner);
        sendTripToContactButton = findViewById(R.id.sendTripToContactButton);
        goBackButton = findViewById(R.id.goBackButton);
        refreshContactsListButton = findViewById(R.id.refreshContactListButton);
        trip = (Trip)getIntent().getSerializableExtra("trip");
        nameArray = new ArrayList<String>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            getContacts(nameArray);
            //https://stackoverflow.com/questions/11920754/android-fill-spinner-from-java-code-programmatically
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nameArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            contactSpinner.setAdapter(adapter);
        }

        //Initialize Button Handlers

        refreshContactsListButton.setOnClickListener(view -> {
            Intent refreshIntent = new Intent( SendTripActivity.this, SendTripActivity.class);
            //Go to next page
            refreshIntent.putExtra("trip", trip);
            SendTripActivity.this.startActivity(refreshIntent);
        });

        sendTripToContactButton.setOnClickListener(view -> {

            permissionDialog = new AlertDialog.Builder(SendTripActivity.this)
                    .setMessage("Are you sure you want to send this trip?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            Toast.makeText(SendTripActivity.this,"Trip Sent!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            //Do nothing
                            Toast.makeText(SendTripActivity.this,"Trip not Sent!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Do nothing
                        }
                    })
                    .show();
        });

        goBackButton.setOnClickListener(view -> {
            Intent previousScreenIntent = new Intent( SendTripActivity.this, ConfirmationActivity.class);
            //Go to previous page
            previousScreenIntent.putExtra("trip", trip);
            SendTripActivity.this.startActivity(previousScreenIntent);
        });
    }

    public void getContacts(ArrayList<String> nameArray) {
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);
        // Loop for every contact in the phone
        if (cursor.getCount() > 0) {
            //Populate Array
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow( DISPLAY_NAME ));
                nameArray.add(name);
            }
        }
    }
}
