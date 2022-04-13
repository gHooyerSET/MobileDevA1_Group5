/*
 * FILE          : FileService.java
 * PROJECT       : PROG3150 - Assignment #1 - Trip Planner
 * FIRST VERSION : 2022-04-10
 * PROGRAMMER    : Gerritt Hooyer
 * DESCRIPTION   : The service in charge of file I/O and DB insertion,
 *                  as well as displaying correct toasts.
 */
package com.example.tripplanner_a1_prog3150;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

/*
 * TITLE : Week 8 - Services Examples
 * AUTHOR : Igor Pustylnick
 * DATE : 2011-09-14
 * VERSION : 1.7.11
 * AVAILABIILTY : https://conestoga.desire2learn.com/d2l/le/content/563407/viewContent/11676574/View
 */
/*
 * TITLE : Week 9 - Receivers, Tabs and Adapters Examples
 * AUTHOR : Igor Pustylnick
 * DATE : 2011-09-14
 * VERSION : 1.7.11
 * AVAILABIILTY : https://conestoga.desire2learn.com/d2l/le/content/563407/viewContent/11676574/View
 */
public class FileService extends Service {
    public FileService() {
    }


    public int onStartCommand(Intent intent, int flags, int startID)
    {
        // Get the trip from the ConfirmationActivity that started the service.
        Trip trip = (Trip)intent.getSerializableExtra("trip");
        new ASyncDatabaseCreator().execute(trip);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    /*  -- Class Header Comment
    Name    :    ASyncFileWriter
    Purpose :    Creates a receipt text file (in a more fully-featured app, this may be uploaded to a server)
                 asynchronously
    */
    public class ASyncFileWriter extends AsyncTask<Trip,Void,Trip>
    {
        private String filename;

        @Override
        protected void onPreExecute() {
            Toast.makeText(FileService.this,"Creating receipt file...",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Trip doInBackground(Trip... params) {

            String fileString = "";
            byte[] fileBuffer;
            Trip trip = (Trip)params[0];

            // Create the file name
            filename = trip.getTripId() + "_" + trip.getDestination() + "_" + "trip.txt";
            // Create the file
            File file = new File(filename);
            // Create file data in the string
            fileString = "Trip ID: " + trip.getTripId() + "\n";
            fileString += "Origin: " + trip.getOrigin() + "\n";
            fileString += "Destination: " + trip.getDestination() + "\n";
            fileString += "Trip Goers: " + trip.getTripGoers() + "\n";
            fileString += "# of Nights: " + trip.getNights() + "\n";
            fileString += "Amenities Cost: $" + String.format("%.2f",trip.getAmenitiesCost()) + "\n";
            fileString += "Hotel Cost: $" + String.format("%.2f",trip.getHotelCost()) + " / night\n";
            fileString += "Total Hotel Cost: $" + String.format("%.2f",trip.getTotalHotelCost()) + "\n";
            fileString += "Ticket Price: $" + String.format("%.2f",trip.getTicketPrice()) + "\n";
            fileString += "Total Ticket Cost: $" + String.format("%.2f",trip.getTotalTicketCost()) + "\n";
            fileString += "Total Cost: $" + String.format("%.2f",trip.getTotalCost()) + "\n";
            try
            {
                // Try to create the file if it doesn't exist
                file.createNewFile();
                // Create the output filestream
                FileOutputStream out = openFileOutput(filename, Context.MODE_PRIVATE);
                // Encode the string to bytes
                fileBuffer = fileString.getBytes(StandardCharsets.UTF_8);
                // Write the string
                out.write(fileBuffer,0,fileBuffer.length);
                // Close the file
                out.close();
            } catch (Exception e) {
                // Log the exception
                Log.d(String.format("%d",e.hashCode()),e.toString());
            }
            return trip;

        }

        @Override
        protected void onPostExecute(Trip trip) {
            Toast.makeText(FileService.this,"File created! " + filename,Toast.LENGTH_SHORT).show();

            //Create the intent and send the broadcast message
            Intent fileReceiverIntent = new Intent(FileReceiver.FILE_BROADCAST);
            //Add the extras
            fileReceiverIntent.putExtra("message","Stop");
            fileReceiverIntent.putExtra("trip",trip);
            //Then send the intent
            sendBroadcast(fileReceiverIntent);
        }
    }
    /*  -- Class Header Comment
    Name    :    ASyncDatabaseCreator
    Purpose :    Stores the trip information into a database, asynchronously
    */
    public class ASyncDatabaseCreator extends AsyncTask<Trip,Void,Trip>
    {
        @Override
        protected void onPreExecute() {
            Toast.makeText(FileService.this,"Inserting trip into DB...",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Trip doInBackground(Trip... params) {
            // Get the reference to the trip
            Trip trip = (Trip)params[0];
            // Create the reference to the tripDB
            TripDB tripDB = new TripDB(FileService.this);

            try {
                // Insert the trip
                tripDB.insertTrip(trip);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return trip;

        }

        @Override
        protected void onPostExecute(Trip trip) {
            Toast.makeText(FileService.this,"Trip inserted!",Toast.LENGTH_SHORT).show();
            // Chaining the filewriter to this asynchronous task
            new FileService.ASyncFileWriter().execute(trip);
        }
    }
}