package com.example.tripplanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2" };



    ListView mainListView;
    ArrayAdapter mArrayAdapter;
    ArrayList mNameList = new ArrayList();
    TextView mainTextView;
    int id;
    Retrofit retrofit;
    TripAdvisor tripAdvisor;
    String API_KEY;
    String name;
    String subcategory;

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

        retrofit = new Retrofit.Builder()
                .baseUrl(TripAdvisorApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tripAdvisor = retrofit.create(TripAdvisor.class);
        String city = intent.getStringExtra("city");
        API_KEY = getResources().getString(R.string.TripAdvisorKey);
        Call<TripAdvisorApi.SearchResults> call = tripAdvisor.searchGeos(city, API_KEY);
        mainTextView = (TextView) findViewById(R.id.text_test);
        call.enqueue(new Callback<TripAdvisorApi.SearchResults>() {
            @Override
            public void onResponse(Response<TripAdvisorApi.SearchResults> response) {
                TripAdvisorApi.SearchResults searchResults = response.body();
                for(int i = 0; i < 1; ++i) {
                    id = searchResults.geos.get(i).location_id;
                    System.out.println("OMG ID = " + id);
                    System.out.println(searchResults.geos.get(i).location_string);
                    subcategory = "other";
                    Call<TripAdvisorApi.SearchAttractionResults> call2 = tripAdvisor.searchAttractions(Integer.toString(id), subcategory, API_KEY);
                    call2.enqueue(new Callback<TripAdvisorApi.SearchAttractionResults>() {
                        @Override
                        public void onResponse(Response<TripAdvisorApi.SearchAttractionResults> response) {
                            TripAdvisorApi.SearchAttractionResults secondSearchResults = response.body();
                            for (int j = 0; j < secondSearchResults.data.size(); ++j) {
                                TripAdvisorApi.Data attraction = secondSearchResults.data.get(j);
                                mNameList.add(attraction);
                                mArrayAdapter.notifyDataSetChanged();
                            }

                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }


                    });
                }


                    }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

        // 4. Access the ListView
        mainListView = (ListView) findViewById(R.id.main_listview);

        // Create an ArrayAdapter for the ListView
        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mNameList);

        // Set the ListView to use the ArrayAdapter
        mainListView.setAdapter(mArrayAdapter);
        mArrayAdapter.notifyDataSetChanged();
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TripAdvisorApi.Data object = (TripAdvisorApi.Data) mNameList.get(position);
                System.out.println("OMG" + object.name + object.location_id + object.selected);
                if (object.selected){
                    view.setBackgroundColor(Color.WHITE);
                    object.selected = false;
                }
                else {
                    view.setBackgroundColor(Color.GREEN);
                    object.selected = true;
                }
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
