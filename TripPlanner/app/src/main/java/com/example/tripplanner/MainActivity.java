package com.example.tripplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends Activity {
    public final static String EXTRA_CITY = "com.example.tripplanner.CITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a REST adapter which points to our API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        GitHub github = retrofit.create(GitHub.class);

        // Create a call instance for looking up contributors.
        Call<List<Api.Contributor>> call = github.contributors("djr-", "wu-wu");

        call.enqueue(new Callback<List<Api.Contributor>>() {
            @Override
            public void onResponse(Response<List<Api.Contributor>> response) {
                List<Api.Contributor> contributors = response.body();
                for (Api.Contributor contributor : contributors) {
                    System.out.println(contributor.login + " (" + contributor.contributions + ")");
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
        Intent intent = new Intent(this, DisplayTripAdvisorActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String city = editText.getText().toString();
        intent.putExtra(EXTRA_CITY, city);
        startActivity(intent);
    }
}
