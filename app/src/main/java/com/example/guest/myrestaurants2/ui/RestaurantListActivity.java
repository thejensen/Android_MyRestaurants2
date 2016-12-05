package com.example.guest.myrestaurants2.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.guest.myrestaurants2.Constants;
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

public class RestaurantListActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private String mRecentAddress;

    public static final String TAG = RestaurantListActivity.class.getSimpleName();
    public ArrayList<Restaurant> mRestaurants = new ArrayList<>();

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private RestaurantListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants2);
        ButterKnife.bind(this);

//        Now, let’s pull the data out of the intent extra in our RestaurantListActivity. To do this, we will use the getIntent() method to recreate the Intent,
        Intent intent = getIntent();
//        and the getStringExtra() method to pull out the location value based using the string key we provided:
        String location = intent.getStringExtra("location");

        getRestaurants(location);

//        we retrieve our shared preferences from the preference manager, pull data from it by calling getString() and providing the key that corresponds to the data we'd like to retrieve. We also pass in the default valuenull. The default value will be returned if the getString() method is unable to find a value that corresponds to the key we provided.
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);
        Log.d("Shared pref location ", mRecentAddress);
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

                RestaurantListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new RestaurantListAdapter(getApplicationContext(), mRestaurants);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(RestaurantListActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }

        });
    }
}
