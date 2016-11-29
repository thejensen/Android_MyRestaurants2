package com.example.guest.myrestaurants2.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.myrestaurants2.R;
import com.example.guest.myrestaurants2.adapters.RestaurantListAdapter;
import com.example.guest.myrestaurants2.models.Restaurant;
import com.example.guest.myrestaurants2.services.YelpService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RestaurantsActivity2 extends AppCompatActivity {
    @Bind(R.id.locationTextView) TextView mLocationTextView;
    @Bind(R.id.listView) ListView mListView;
    private String[] restaurants = new String[] {"HUB", "Garden Bar", "Reel M Inn", "People's Pig", "Rabbits Cafe", "Bar Bar", "Slide Inn", "Nightlight", "The Florida Room", "Fresh Roll", "Eat", "Cruz Room", "Radio Room", "Santeria", "Sauce Box", "Shut Up and Eat", "A Real Taste of India (Foodcart)"};
    public static final String TAG = RestaurantsActivity2.class.getSimpleName();
    public ArrayList<Restaurant> mRestaurants = new ArrayList<>();
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private RestaurantListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants2);
        ButterKnife.bind(this);

//        Then, we'll create an ArrayAdapter and set our ListView adapter to the new adapter:
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, restaurants);
        mListView.setAdapter(adapter);

//        If you click a list item, the name of the item shows in a toast
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String restaurant = ((TextView)view).getText().toString();
                Toast.makeText(RestaurantsActivity2.this, restaurant, Toast.LENGTH_LONG).show();
                Log.v(TAG, "In the onItemClickListener");
            }
        });

//        Now, let’s pull the data out of the intent extra in our RestaurantsActivity. To do this, we will use the getIntent() method to recreate the Intent,
        Intent intent = getIntent();
//        and the getStringExtra() method to pull out the location value based using the string key we provided:
        String location = intent.getStringExtra("location");
        mLocationTextView.setText("Here are all the restaurants near: " + location);
        Log.d(TAG, "In the onCreate method");

        getRestaurants(location);
    }

    private void getRestaurants (String location) {
        final YelpService yelpService = new YelpService();
        yelpService.findRestaurants(location, new Callback() {

            @Override
            public void onFailure (Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) {
                mRestaurants = yelpService.processResults(response);

                RestaurantsActivity2.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new RestaurantListAdapter(getApplicationContext(), mRestaurants);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(RestaurantsActivity2.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }

        });
    }
}
