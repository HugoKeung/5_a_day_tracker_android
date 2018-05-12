package com.example.hugo.myapplication;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by hugo on 31/05/17.
 */

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_about) {
            Intent myIntent = new Intent(getApplicationContext(), AboutActivity.class);

            startActivity(myIntent);
            return true;
        }

        else if (id == R.id.menu_manual_input){
            Intent myIntent = new Intent(getApplicationContext(), ManualActivity.class);

            startActivity(myIntent);
        }

        else if (id == R.id.menu_day_view){
            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(myIntent);
        }

        else if (id == R.id.menu_month_view){
            Intent myIntent = new Intent(getApplicationContext(), MonthActivity.class);

            startActivity(myIntent);
        }

        else if (id == R.id.menu_week_view){
            Intent myIntent = new Intent(getApplicationContext(), WeekActivity.class);

            startActivity(myIntent);
        }


        return super.onOptionsItemSelected(item);
    }


}
