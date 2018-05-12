package com.example.hugo.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.hugo.myapplication.DateUtil;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by hugo on 03/06/17.
 */

public class RecordDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "record.db";

    private static final int DATABASE_VERSION = 1;

    public RecordDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_ENTRY_TABLE = "CREATE TABLE " + RecordContract.DayRecordEntry.TABLE_NAME + " (" +
                RecordContract.DayRecordEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RecordContract.DayRecordEntry.COLUMN_FOOD_NAME + " TEXT NOT NULL, " +
                RecordContract.DayRecordEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, " +
                RecordContract.DayRecordEntry.COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_DATE" +
                "); ";

        final String SQL_CREATE_PORTION_TABLE = "CREATE TABLE " + RecordContract.PortionToQuantity.TABLE_NAME + " (" +
                RecordContract.PortionToQuantity.COLUMN_FOOD_NAME + " TEXT PRIMARY KEY, " +
                RecordContract.PortionToQuantity.COLUMN_PORTION + " DECIMAL(2,1)" +
                "); ";

        db.execSQL(SQL_CREATE_ENTRY_TABLE);
        db.execSQL(SQL_CREATE_PORTION_TABLE);

        DatabaseSetup setup = new DatabaseSetup();
        setup.setUpData(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RecordContract.PortionToQuantity.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RecordContract.DayRecordEntry.TABLE_NAME);

        onCreate(db);
    }

    public void newEntry(String food_name, int quantity){
        if (quantity >0) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(RecordContract.DayRecordEntry.COLUMN_FOOD_NAME, food_name);
            cv.put(RecordContract.DayRecordEntry.COLUMN_QUANTITY, quantity);

            cv.put(RecordContract.DayRecordEntry.COLUMN_TIMESTAMP, DateUtil.dateTo_yyyyMMdd(new Date()));

            db.insert(RecordContract.DayRecordEntry.TABLE_NAME, null, cv);

            Log.w(food_name + " " + quantity, "NEW INPUT");
            db.close();
        }

        //TODO change so that only allow input of predefined food

    }

    public double getDayRecord(Date rawDate){
        SQLiteDatabase db = this.getReadableDatabase();

        //String date = new SimpleDateFormat("yyyy-MM-dd").format(rawDate);

        String date = DateUtil.dateTo_yyyyMMdd(rawDate);
/*
        String SQLQuery = "SELECT SUM(" + RecordContract.DayRecordEntry.COLUMN_QUANTITY + ") " +
                "FROM " + RecordContract.DayRecordEntry.TABLE_NAME +
                " WHERE " + RecordContract.DayRecordEntry.COLUMN_TIMESTAMP + " = ?" ;

  */
        //new sql with the portion table, need to artificially insert info for portion table before it will work

        String SQLQuery = "SELECT SUM(" + RecordContract.DayRecordEntry.COLUMN_QUANTITY + "*" + RecordContract.PortionToQuantity.COLUMN_PORTION + ") " +
                "FROM " + RecordContract.DayRecordEntry.TABLE_NAME +
                " NATURAL JOIN " + RecordContract.PortionToQuantity.TABLE_NAME +
                " WHERE " + RecordContract.DayRecordEntry.COLUMN_TIMESTAMP + " = ?" ;


        //prepared statemnt??
        try(Cursor cursor = db.rawQuery(SQLQuery, new String[]{date})) {

            if (cursor != null && cursor.getCount()>0) {
                cursor.moveToFirst();
                double totalForDay = cursor.getDouble(0);
                //  System.out.println("column position: " + cursor.getColumnIndex("SUM"));
                db.close();
                return totalForDay;
            }
        }
        db.close();
        return 0;


    }
//close db
    //currently output every day with entry
    public ArrayList<DayRecord> getWeekRecord(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DayRecord> weekRecord;

           //new SQL query for new portion table
        String SQLQuery = "SELECT SUM(" + RecordContract.DayRecordEntry.COLUMN_QUANTITY + "*" + RecordContract.PortionToQuantity.COLUMN_PORTION + ") AS SUM, " +
                RecordContract.DayRecordEntry.COLUMN_TIMESTAMP + " FROM " +
                RecordContract.PortionToQuantity.TABLE_NAME + " NATURAL JOIN " +
                RecordContract.DayRecordEntry.TABLE_NAME + " GROUP BY " +
                RecordContract.DayRecordEntry.COLUMN_TIMESTAMP + " HAVING " +
                RecordContract.DayRecordEntry.COLUMN_TIMESTAMP + " >= DATE ('NOW', '-6 day')";



//PROGRESS BAR WITH SECTIONS OF DIFFERENT COLOUR??

        weekRecord = initialiseWeekRecord();

        try(Cursor mCursor = db.rawQuery(SQLQuery, null)){
            while (mCursor.moveToNext()){
                String date = mCursor.getString(mCursor.getColumnIndex(RecordContract.DayRecordEntry.COLUMN_TIMESTAMP));

                double portion = mCursor.getDouble(mCursor.getColumnIndex("SUM"));

                for (DayRecord day: weekRecord){

                    if (day.getDate().equals(date)){

                        day.setPortion(portion);

                    }
                }
            }
        }

        db.close();
        return weekRecord;

    }
//make a new recycler view and put this cursor into it
    public Cursor detailedDayRecord(Date rawDate){
        SQLiteDatabase db = this.getReadableDatabase();
        String date = DateUtil.dateTo_yyyyMMdd(rawDate);



        String SQLQuery = "SELECT SUM(" + RecordContract.DayRecordEntry.COLUMN_QUANTITY + "*" +
                RecordContract.PortionToQuantity.COLUMN_PORTION + ") AS CONSUMED, " +
                RecordContract.PortionToQuantity.COLUMN_FOOD_NAME +
                " FROM " + RecordContract.DayRecordEntry.TABLE_NAME +
                " NATURAL JOIN " + RecordContract.PortionToQuantity.TABLE_NAME +
                " WHERE " + RecordContract.DayRecordEntry.COLUMN_TIMESTAMP + " = ? " +
                " GROUP BY " + RecordContract.PortionToQuantity.COLUMN_FOOD_NAME +
                " ORDER BY CONSUMED DESC";

        Cursor cursor = db.rawQuery(SQLQuery, new String[]{date});


//close cursor after
        return cursor;
    }
    public ArrayList<String> allFood(){
        SQLiteDatabase db = this.getReadableDatabase();
        String SQLQuery = "SELECT " + RecordContract.PortionToQuantity.COLUMN_FOOD_NAME +
                " FROM " + RecordContract.PortionToQuantity.TABLE_NAME;

        ArrayList<String> foodList = new ArrayList<>();

        try(Cursor cursor = db.rawQuery(SQLQuery, null)){
            while(cursor.moveToNext()){
                String foodName = cursor.getString(0);
                foodList.add(foodName);

            }
        }
        db.close();
        return foodList;

    }

    private ArrayList<DayRecord> initialiseWeekRecord(){
        ArrayList<DayRecord> blankRecord = new ArrayList<>();

        for (int i = 0; i < 7; i++){

            Calendar c = Calendar.getInstance();
            try {
                c.setTime(new Date());
            }catch (Exception e){
                e.printStackTrace();
            }
            c.add(Calendar.DATE, -i);
            String newDay = DateUtil.dateTo_yyyyMMdd(c.getTime());

            blankRecord.add(new DayRecord(newDay, 0));
        }

        return blankRecord;

    }

    //return only the first valid food (one with highest probability

    public boolean foodExistInDB(String label){
        SQLiteDatabase db = this.getReadableDatabase();

        String SQLQuery = "SELECT * "
                +" FROM " + RecordContract.PortionToQuantity.TABLE_NAME
                + " WHERE " + RecordContract.PortionToQuantity.COLUMN_FOOD_NAME
                + " = ? ";

        try(Cursor cursor = db.rawQuery(SQLQuery, new String[]{label})){

            if (cursor != null && cursor.getCount()>0){

                return true;
            }
        }
        return false;


    }

    public Cursor foodPortionCursor(){
        SQLiteDatabase db = this.getReadableDatabase();
        String SQLQuery = "SELECT * FROM " + RecordContract.PortionToQuantity.TABLE_NAME
                + " ORDER BY " + RecordContract.PortionToQuantity.COLUMN_FOOD_NAME + " ASC ";
        return db.rawQuery(SQLQuery, null);

    }


}
