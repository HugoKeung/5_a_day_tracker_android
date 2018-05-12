package com.example.hugo.myapplication.data;

import android.provider.BaseColumns;

/**
 * Created by hugo on 03/06/17.
 */

public class RecordContract {

    public static final class DayRecordEntry implements BaseColumns{
        public static final String TABLE_NAME = "day_record";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_FOOD_NAME = "name_of_food";

    }

    public static final class PortionToQuantity {
        public static final String TABLE_NAME = "portion_info";
        public static final String COLUMN_FOOD_NAME = "name_of_food";
        public static final String COLUMN_PORTION = "real_portion";
    }
    public static final String PORTIONxQUANTITY = "CONSUMED";

}
