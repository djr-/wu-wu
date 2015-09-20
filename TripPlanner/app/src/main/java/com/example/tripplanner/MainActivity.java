package com.example.tripplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends Activity {
    public final static String EXTRA_CITY = "com.example.tripplanner.CITY";
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /**Called when User clicks button to enter City Name **/
    public void selectCity(View view){
        // Create a REST adapter which points to our API
        EditText cityEntry = (EditText) findViewById(R.id.editText);
        String city = cityEntry.getText().toString();
        city = changeString(city);
        Intent intent = new Intent(this, DisplayTripAdvisorActivity.class);
        intent.putExtra("city", city);
        startActivity(intent);


    }
    public String changeString(String cityString){
        StringBuilder query = new StringBuilder();
        try {
            query.append(URLEncoder.encode(cityString, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        cityString = query.toString().toLowerCase().replace("+","%20");
        return cityString;
    }

    public void listTest(View view){

        Intent intent = new Intent(this, MyListActivity.class);
        startActivity(intent);
    }
}
