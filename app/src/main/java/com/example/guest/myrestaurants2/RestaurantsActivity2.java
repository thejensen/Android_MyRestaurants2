package com.example.guest.myrestaurants2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RestaurantsActivity2 extends AppCompatActivity {
    private TextView mLocationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants2);
        mLocationTextView = (TextView) findViewById(R.id.locationTextView);
//        Now, let’s pull the data out of the intent extra in our RestaurantsActivity. To do this, we will use the getIntent() method to recreate the Intent,
        Intent intent = getIntent();
//        and the getStringExtra() method to pull out the location value based using the string key we provided:
        String location = intent.getStringExtra("location");
        mLocationTextView.setText("Here are all the restaurants near: " + location);
    }
}
