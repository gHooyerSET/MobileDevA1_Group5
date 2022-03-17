package com.example.tripplanner_a1_prog3150;

import android.os.AsyncTask;

public class ASyncTimer extends AsyncTask
{
    @Override
    protected Object doInBackground(Object[] objects) {
        //Set the initial timer value to 0
        double secondsLeft = 10;
        //Start an infinite loop
        try
        {
            while(true)
            {
                secondsLeft -= 0.25;
                if(secondsLeft <= 0)
                {
                    break;
                }
            }
        } catch (Exception e)
        {

        }
        return null;
    }
}



