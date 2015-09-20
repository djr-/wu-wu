package com.example.tripplanner;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private void addMarkerToMap(LatLng latLng, String markerName) {
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(markerName));
    }

    private String trimParens(String stringWithParens) {
        String stringWithoutParens;
        stringWithoutParens = stringWithParens.replace("(", "");
        stringWithoutParens = stringWithParens.replace(")", "");
        return stringWithoutParens;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        LatLng firstLatLong = new LatLng(0, 0);
        LatLng secondLatLong = new LatLng(0, 0);

        ArrayList<TripAdvisorApi.Data> tripList = (ArrayList<TripAdvisorApi.Data>)getIntent().getSerializableExtra("tripList");
        System.out.println("OMG COUNT = " + tripList.size());
        for (int i = 0; i < tripList.size(); ++i){
            String name = tripList.get(i).name;
            double longitude = tripList.get(i).longitude;
            double latitude = tripList.get(i).latitude;
            if (i == 0)
                firstLatLong = new LatLng(latitude, longitude);
            if (i == 1)
                secondLatLong = new LatLng(latitude, longitude);
            LatLng goodLatLong = new LatLng(latitude, longitude);
            addMarkerToMap(goodLatLong, name);
        }

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(firstLatLong)      // Sets the center of the map to Mountain View
                .zoom(11)                   // Sets the zoom
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GoogleDistanceMatrixApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GoogleDistanceMatrix distanceMatrix = retrofit.create(GoogleDistanceMatrix.class);
        String DISTANCE_MATRIX_API_KEY = getResources().getString(R.string.google_distance_matrix_key);

        String firstTrimmedLatLong = trimParens(firstLatLong.toString());
        String secondTrimmedLatLong = trimParens(secondLatLong.toString());

        Call<GoogleDistanceMatrixApi.Result> call = distanceMatrix.computeTimeBetween("38.63983,-90.29417", "39.63983,-90.29417", DISTANCE_MATRIX_API_KEY);

        call.enqueue(new Callback<GoogleDistanceMatrixApi.Result>() {
            @Override
            public void onResponse(Response<GoogleDistanceMatrixApi.Result> response) {
                GoogleDistanceMatrixApi.Result result = response.body();
                System.out.println(result.status);
                System.out.println(result.origin_addresses);
                System.out.println(result.destination_addresses);
                System.out.println(result.rows.get(0).elements.get(0).status);
                System.out.println(result.rows.get(0).elements.get(0).duration.text);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}
