/*
 * FILE          : TripDB.java
 * PROJECT       : PROG3150 - Assignment #1 - Trip Planner
 * FIRST VERSION : 2022-03-16
 * PROGRAMMER    : Patrick Cho and Nathan Domingo
 * DESCRIPTION   : This file contains all database setup and functionality
 */

package com.example.tripplanner_a1_prog3150;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*  -- Class Header Comment
    Name    :    TripDB
    Purpose :    To handle the creation of database, creation of initial test data,
                 and all database functionality
    */
public class TripDB {

        //Database Constants
        public static final String DB_NAME = "trips.db";
        public static final int DB_VERSION = 1;

        //Trip Table Constants
        public static final String TRIP_TABLE = "trip";

        public static final String TRIP_ID = "_id";
        public static final int TRIP_ID_COL = 0;

        public static final String TRIP_ORIGIN = "trip_origin";
        public static final int TRIP_ORIGIN_COL = 1;

        public static final String TRIP_DESTINATION = "trip_destination";
        public static final int TRIP_DESTINATION_COL = 2;

        public static final String TRIP_GROUP_SIZE = "trip_group_size";
        public static final int TRIP_GROUP_SIZE_COL = 3;

        public static final String TRIP_AMENITIES_COST = "trip_amenities_cost";
        public static final int  TRIP_AMENITIES_COST_COL = 4;

        public static final String TRIP_TICKET_PRICE = " trip_ticket_price";
        public static final int TRIP_TICKET_PRICE_COL = 5;

        public static final String TRIP_NUM_OF_NIGHTS = "trip_num_of_nights";
        public static final int TRIP_NUM_OF_NIGHTS_COL = 6;

        public static final String TRIP_NIGHT_COST = "trip_night_cost";
        public static final int TRIP_NIGHT_COST_COL = 7;

        public static final String TRIP_TOTAL_COST = "trip_total_cost";
        public static final int TRIP_TOTAL_COST_COL = 8;

        //CREATE and DROP TABLE statements
        public static final String CREATE_TRIP_TABLE =
                "CREATE TABLE " + TRIP_TABLE + " (" +
                TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TRIP_ORIGIN + "TEXT, " +
                TRIP_DESTINATION + "TEXT, " +
                TRIP_GROUP_SIZE + "INTEGER, " +
                TRIP_AMENITIES_COST + "FLOAT, " +
                TRIP_TICKET_PRICE + "FLOAT, " +
                TRIP_NUM_OF_NIGHTS + "INTEGER, " +
                TRIP_NIGHT_COST + "FLOAT, " +
                TRIP_TOTAL_COST + "FLOAT);";

        public static final String DROP_TRIP_TABLE =
                "DROP TABLE IF EXISTS " + TRIP_TABLE;

        /*  -- Class Header Comment
        Name    :    DBHelper
        Purpose :    To initialize the DB/DBHelper
        */
        private static class DBHelper extends SQLiteOpenHelper {
                public DBHelper(Context context, String name, CursorFactory factory, int version) {
                        super(context, name, factory, version);
                }

                /*  -- Function Header Comment
                Name    : onCreate
                Purpose : Performs initial database setup
                Inputs  : SQLiteDatabase db - the database
                Outputs : Creates trip table, and inserts test data
                Returns : void
                */
                @Override
                public void onCreate(SQLiteDatabase db) {
                        db.execSQL(TripDB.CREATE_TRIP_TABLE);
                        //Generate Some Test Data
                        db.execSQL("INSERT INTO trip VALUES (1, 'Toronto', 'Seoul', 3, 0, 200, 5, 100, 1100)");
                        db.execSQL("INSERT INTO trip VALUES (2, 'Seoul', 'Toronto', 2, 0, 100, 3, 150, 650)");
                }

                /*  -- Function Header Comment
                Name    : onUpgrade
                Purpose : Logs DB upgrades, drops the old trip table, recreates it
                Inputs  : SQLiteDatabase db - the database
                          int oldVersion    - old database version number
                          int new version   - new database version number
                Outputs : Logs trip upgrade, drops old table
                Returns : void
                */
                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                        Log.d("Trip", "Upgrading db from version " + oldVersion + "to" + newVersion);
                        db.execSQL(TripDB.DROP_TRIP_TABLE);
                        onCreate(db);
                }
        }

        //DB and DBHelper Objects
        private SQLiteDatabase db;
        private final DBHelper dbHelper;

        /*  -- Function Header Comment
        Name    : TripDB
        Purpose : Initializes the DBHelper (by association the DB as well)
        Inputs  : Context context - application context
        Outputs : n/a
        Returns : n/a
        */
        public TripDB(Context context) {
                dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
        }

        /*  -- Function Header Comment
        Name    : openReadableDB
        Purpose : Opens database reference for read operations
        Inputs  : n/a
        Outputs : n/a
        Returns : n/a
        */
        private void openReadableDB() { db = dbHelper.getReadableDatabase(); }

        /*  -- Function Header Comment
        Name    : openWriteableDB
        Purpose : Opens database reference for write operations
        Inputs  : n/a
        Outputs : n/a
        Returns : n/a
        */
        private void openWriteableDB() { db = dbHelper.getWritableDatabase(); }

        /*  -- Function Header Comment
        Name    : closeDB
        Purpose : Closes DB reference
        Inputs  : n/a
        Outputs : n/a
        Returns : n/a
        */
        private void closeDB() { if (db != null) db.close(); }

        /*  -- Function Header Comment
        Name    : closeCursor
        Purpose : Closes cursor reference
        Inputs  : Cursor cursor - the cursor
        Outputs : n/a
        Returns : n/a
        */
        private void closeCursor(Cursor cursor) { if (cursor != null) cursor.close(); }

        /*  -- Function Header Comment
        Name    : insertTrip
        Purpose : Insert trip to the Trip table
        Inputs  : Trip newTrip - trip object to be added
        Outputs : newTrip info to database
        Returns : long rowID - the index of the insertion
        */
        public long insertTrip(Trip newTrip) {
                ContentValues cv = new ContentValues();
                cv.put(TRIP_ID, newTrip.getTripId());
                cv.put(TRIP_ORIGIN, newTrip.getOrigin());
                cv.put(TRIP_DESTINATION, newTrip.getDestination());
                cv.put(TRIP_GROUP_SIZE, newTrip.getTripGoers());
                cv.put(TRIP_AMENITIES_COST, newTrip.getAmenitiesCost());
                cv.put(TRIP_TICKET_PRICE, newTrip.getTicketPrice());
                cv.put(TRIP_NUM_OF_NIGHTS, newTrip.getTicketPrice());
                cv.put(TRIP_NIGHT_COST, newTrip.getHotelCost());
                cv.put(TRIP_TOTAL_COST, newTrip.getTotalCost());

                this.openWriteableDB();
                long rowID = db.insert(TRIP_TABLE, null, cv);
                this.closeDB();

                return rowID;
        }

        /*  -- Function Header Comment
        Name    : deleteTaskById
        Purpose : Delete task based on given ID
        Inputs  : int tripID - the ID of the trip to be removed
        Outputs : Removes trip from Trip table
        Returns : int rowCount - num of deleted rows
        */
        public int deleteTaskById(int tripID) {
               String where = TRIP_ID + "= ?";
               String[] whereArgs = { String.valueOf(tripID)};

               this.openWriteableDB();
               int rowCount = db.delete(TRIP_TABLE, where, whereArgs);
               this.closeDB();

               return rowCount;
        }

        /*  -- Function Header Comment
        Name    : getTripById
        Purpose : Gets trip information based on ID
        Inputs  : int tripID - the trip to be found
        Outputs : n/a
        Returns : Trip existingTrip - a previously existing trip from the database\
                  null - if trip does not exist
        */
        public Trip getTripById(int tripID) {
                String where = TRIP_ID + "= ?";
                String[] whereArgs = { Integer.toString(tripID) };

                this.openReadableDB();
                Cursor cursor = db.query(TRIP_TABLE, null, where, whereArgs,
                        null, null, null);
                cursor.moveToFirst();

                Trip existingTrip = getTripFromCursor(cursor);

                if (existingTrip == null)
                {
                        return null;
                }

                this.closeCursor(cursor);
                this.closeDB();
                return existingTrip;
        }

        /*  -- Function Header Comment
        Name    : getTripFromCursor
        Purpose : Gets trip information based on cursor location
        Inputs  : Cursor cursor - location of desired trip
        Outputs : n/a
        Returns : Trip existingTrip - a previously existing trip from the database
        */
        private static Trip getTripFromCursor(Cursor cursor) {
                if (cursor == null || cursor.getCount() == 0) {
                        return null;
                }
                else {
                        try {
                                Trip existingTrip = new Trip();

                                //Initialize class variables
                                existingTrip.setTripId(cursor.getInt(TRIP_ID_COL));
                                existingTrip.setDestination(cursor.getString(TRIP_DESTINATION_COL));
                                existingTrip.setOrigin(cursor.getString(TRIP_ORIGIN_COL));
                                existingTrip.setTicketPrice(cursor.getInt(TRIP_TICKET_PRICE_COL));
                                existingTrip.setTripGoers(cursor.getInt(TRIP_GROUP_SIZE_COL));
                                existingTrip.setHotelCost(cursor.getFloat(TRIP_NIGHT_COST_COL));
                                existingTrip.setNights(cursor.getInt(TRIP_NUM_OF_NIGHTS_COL));
                                existingTrip.updateTotalTicketCost();
                                existingTrip.updateTotalHotelCost();
                                existingTrip.setAmenitiesCost(cursor.getInt(TRIP_AMENITIES_COST_COL));
                                existingTrip.updateTotalCost();
                                return existingTrip;
                        }
                        catch (Exception e) {
                                return null;
                        }
                }
        }
}

