package com.example.hugo.myapplication.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by hugo on 08/08/17.
 */

public class DatabaseSetup {

    public static void setUpData(SQLiteDatabase db){
        insertData(db, "Granny Smith", "1");
        insertData(db, "orange", "1");
        insertData(db, "lemon", "0.5");
        insertData(db, "fig", "0.5");
        insertData(db, "pineapple", "1");
        insertData(db, "banana", "1");
        insertData(db, "jackfruit", "1");
        insertData(db, "pomegranate", "0.5");
        insertData(db, "custard apple", "0.5");
        insertData(db, "corn", "1");
        insertData(db, "bell pepper", "1");
        insertData(db, "head cabbage", "1");
        insertData(db, "broccoli", "1");
        insertData(db, "cauliflower", "1");
        insertData(db, "zucchini", "1");
        insertData(db, "spaghetti squash", "1");
        insertData(db, "acorn squash", "1");
        insertData(db, "butternut squash", "1");
        insertData(db, "cucumber", "0.5");
        insertData(db, "mushroom", "0.2");
        insertData(db, "strawberry", "0.2");
        insertData(db, "guacamole", "1");



    }
    public static void insertData(SQLiteDatabase db, String foodName, String quantity){
        ContentValues cv;
        cv = new ContentValues();
        cv.put(RecordContract.PortionToQuantity.COLUMN_FOOD_NAME, foodName);
        cv.put(RecordContract.PortionToQuantity.COLUMN_PORTION, quantity);
        db.insert(RecordContract.PortionToQuantity.TABLE_NAME, null, cv);

    }

}
