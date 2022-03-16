package com.example.tripplanner_a1_prog3150;

import android.os.AsyncTask;

import java.util.Timer;

public class ASyncTimer extends AsyncTask
{
    double secondsLeft;

    @Override
    protected Object doInBackground(Object[] objects) {
        //Set the initial timer value to 0
        secondsLeft = 10;
        //Start an infinite loop
        while(true)
        {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            secondsLeft -= 0.25;

            if(secondsLeft <= 0)
            {
                break;
            }
        }


        return null;
    }
}

public class ASyncFileWriter extends AsyncTask
{
    @Override
    protected Object doInBackground(Object[] objects) {
        //Here we call the function that writes to a file.
    }
}

