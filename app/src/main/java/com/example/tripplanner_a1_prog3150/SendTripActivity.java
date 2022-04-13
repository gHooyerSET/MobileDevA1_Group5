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
import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;
import java.util.List;
import java.util.ArrayList;
import android.widget.ArrayAdapter;

public class SendTripActivity extends MenuActivity {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS=1;
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
        contactSpinner = findViewById(R.id.contactSpinner);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            getContacts(contactSpinner);
        }

        //Initialize Button Handler
    }
    public void getContacts(Spinner contactSpinner) {
        List<String> nameArray = new ArrayList<String>();

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);
        // Loop for every contact in the phone
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(cursor.getColumnIndexOrThrow( _ID ));
                String name = cursor.getString(cursor.getColumnIndexOrThrow( DISPLAY_NAME ));
                nameArray.add(name);
            }
        }

        //https://stackoverflow.com/questions/11920754/android-fill-spinner-from-java-code-programmatically
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, nameArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contactSpinner.setAdapter(adapter);
    }
}
