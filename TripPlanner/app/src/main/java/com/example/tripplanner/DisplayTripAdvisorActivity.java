package com.example.tripplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class DisplayTripAdvisorActivity extends Activity {
    ListView mainListView;
    ArrayAdapter mArrayAdapter;
    ArrayList mNameList = new ArrayList();
    TextView mainTextView;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_trip_advisor);
        mainListView = (ListView) findViewById(R.id.main_listview);
        mArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                mNameList);
        mainListView.setAdapter(mArrayAdapter);
        Intent intent = getIntent();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TripAdvisorApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TripAdvisor tripAdvisor = retrofit.create(TripAdvisor.class);
        String city = intent.getStringExtra("city");
        String API_KEY = getResources().getString(R.string.TripAdvisorKey);
        Call<TripAdvisorApi.SearchResults> call = tripAdvisor.searchGeos(city, API_KEY);
        mainTextView = (TextView) findViewById(R.id.text_test);
        call.enqueue(new Callback<TripAdvisorApi.SearchResults>() {
            @Override
            public void onResponse(Response<TripAdvisorApi.SearchResults> response) {
                TripAdvisorApi.SearchResults searchResults = response.body();
                for(int i = 0; i < searchResults.geos.size(); ++i){
                    id = searchResults.geos.get(i).location_id;
                    System.out.println("OMG ID = " + id);
                    System.out.println(searchResults.geos.get(i).location_string);
                    mainTextView.setText("OurPlace = " + id);
                    mNameList.add(id);
                    mArrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_trip_advisor, menu);
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
}
