package com.example.tripplanner;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MyListActivity extends ListActivity {
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        String[] values = new String[] { "Bob's Pizza", "City Park", "Billy Bowling",
                "Saloon", "Night Club", "Art Museum", "Zoo", "Botanical Garden",
                "Sally's Steakhouse", "Cindy's Cafe" };
        int[] locNums = new int[] {1,2,3,4,5,6,7,8,9,0};
        Boolean[] locSel = new Boolean[] {false,false,false,false,false,false,false,false,false,false};





        displayLocation[] disVals = new displayLocation[]{};


        for(int i=0; i < values.length; i++){
            //disVals;
        }

        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, values);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
    }
}
