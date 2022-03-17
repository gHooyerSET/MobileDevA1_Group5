package com.example.tripplanner_a1_prog3150;

import android.content.Context;
import android.os.AsyncTask;

import java.io.FileOutputStream;


public class ASyncFileWriter extends AsyncTask
{
    @Override
    protected Object doInBackground(Object[] params) {

        Context c = (Context)params[0];
        String fileString = "";
        byte[] fileBuffer;
        String filename = "";


        try
        {
            FileOutputStream out = c.openFileOutput(filename,Context.MODE_PRIVATE);

        } catch (Exception e) {

        }
        return null;
    }
}