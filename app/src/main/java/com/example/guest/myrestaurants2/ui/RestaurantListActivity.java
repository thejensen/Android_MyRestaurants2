package com.example.guest.myrestaurants2.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
    public static final String TAG = RestaurantListActivity.class.getSimpleName();

    private SharedPreferences mSharedPreferences;
//    Now that users will be able to search a new zip code here in our RestaurantListActivity, we'll need access to the Editor to stash this new zip code in SharedPreferences
    private SharedPreferences.Editor mEditor;
    private String mRecentAddress;


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

        if (mRecentAddress != null) {
            Log.v(TAG, "Recent address" + mRecentAddress);
            getRestaurants(mRecentAddress);
        }
    }

//    onCreateOptionsMenu() we inflate and bind our Views, define our mSharedPreferences and mEditor member variables.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

//        to retrieve a user’s search from the SearchView, we must grab the action_search menu item from our new layout, and use the MenuItemCompat.getActionView() method.
        MenuItem menuItem = menu.findItem(R.id.action_search);
//        SearchView objects have their own dedicated listeners called OnQueryTextListener, that listen for changes in the SearchView
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
//        Because we only want to gather the input after the user has submitted something (and not every time they type a single character into the field), we'll place our logic in onQueryTextSubmit(), leaving onQueryTextChange fairly empty. We call addToSharedPreferences() to save the zip code the user searches, and getRestaurants() to begin executing a request to the Yelp API to return restaurants in that area.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                getRestaurants(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

//    onOptionsItemSelected() simply contains the line return super.onOptionsItemSelected(item);. This ensures that all functionality from the parent class (referred to here as super) will still apply despite us manually overriding portions of the menu's functionality.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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

    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    }

}
