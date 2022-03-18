package com.example.tripplanner_a1_prog3150;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

        //DB Helper
        private static class DBHelper extends SQLiteOpenHelper {
                public DBHelper(Context context, String name, CursorFactory factory, int version) {
                        super(context, name, factory, version);
                }

                @Override
                public void onCreate(SQLiteDatabase db) {
                        db.execSQL(TripDB.CREATE_TRIP_TABLE);
                        //Generate Some Test Data
                        db.execSQL("INSERT INTO trip VALUES (1, 'Toronto', 'Seoul', 3, 0, 200, 5, 100, 1100)");
                        db.execSQL("INSERT INTO trip VALUES (2, 'Seoul', 'Toronto', 2, 0, 100, 3, 150, 650)");
                }

                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                        Log.d("Trip", "Upgrading db from version " + oldVersion + "to" + newVersion);
                        db.execSQL(TripDB.DROP_TRIP_TABLE);
                        onCreate(db);
                }
        }

        //DB and DBHelper Objects
        private SQLiteDatabase db;
        private DBHelper dbHelper;

        //constructor
        public TripDB(Context context) {
                dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
        }

        //private methods
        private void openReadableDB() { db = dbHelper.getReadableDatabase(); }

        private void openWriteableDB() { db = dbHelper.getWritableDatabase(); }

        private void closeDB() { if (db != null) db.close(); }

        private void closeCursor(Cursor cursor) { if (cursor != null) cursor.close(); }

        //public methods
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

        public int deleteTaskById(int tripID) {
               String where = TRIP_ID + "= ?";
               String[] whereArgs = { String.valueOf(tripID)};

               this.openWriteableDB();
               int rowCount = db.delete(TRIP_TABLE, where, whereArgs);
               this.closeDB();

               return rowCount;
        }

        public Trip getTripById(int tripID) {
                String where = TRIP_ID + "= ?";
                String[] whereArgs = { Integer.toString(tripID) };

                this.openReadableDB();
                Cursor cursor = db.query(TRIP_TABLE, null, where, whereArgs,
                        null, null, null);
                cursor.moveToFirst();

                Trip existingTrip = getTripFromCursor(cursor);

                this.closeCursor(cursor);
                this.closeDB();
                return existingTrip;
        }

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

