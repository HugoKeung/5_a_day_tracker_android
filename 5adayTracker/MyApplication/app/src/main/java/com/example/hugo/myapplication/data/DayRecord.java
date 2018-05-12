package com.example.hugo.myapplication.data;

/**
 * Created by hugo on 05/06/17.
 */

public class DayRecord {
    String date;
    double portion;
    public DayRecord(String date, double portion){
        this.date = date;
        this.portion = portion;
    }
    public double getPortion(){
        return portion;
    }
    public String getDate(){

        return date;
    }

    void setPortion(double portion){
        this.portion = portion;
    }
}
