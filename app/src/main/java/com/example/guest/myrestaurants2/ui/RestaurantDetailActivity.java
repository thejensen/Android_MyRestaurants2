package com.example.guest.myrestaurants2.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.guest.myrestaurants2.R;
import com.example.guest.myrestaurants2.adapters.RestaurantPagerAdapter;
import com.example.guest.myrestaurants2.models.Restaurant;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RestaurantDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private RestaurantPagerAdapter adapterViewPager;
    ArrayList<Restaurant> mRestaurants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        ButterKnife.bind(this);

//        we pull our ArrayList<Restaurant> Parcelable using the unwrap() method on our "restaurants" intent extra. We also retrieve the position int included as an intent extra.
        mRestaurants = Parcels.unwrap(getIntent().getParcelableExtra("restaurants"));
        int startingPosition = getIntent().getIntExtra("position", 0);

//        we create a new pager adapter called adapterViewPager, providing the mRestaurants as an argument.
        adapterViewPager = new RestaurantPagerAdapter(getSupportFragmentManager(), mRestaurants);
//        then we instruct our ViewPager to use this new adapter.
        mViewPager.setAdapter(adapterViewPager);
//        we also set the current item to the position of the item that was just clicked on.
        mViewPager.setCurrentItem(startingPosition);
    }
}
