package com.example.guest.myrestaurants2.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.guest.myrestaurants2.models.Restaurant;
import com.example.guest.myrestaurants2.ui.RestaurantDetailFragment;

import java.util.ArrayList;

public class RestaurantPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Restaurant> mRestaurants;

//      RestaurantsPagerAdapter is a constructor where we set the required FragmentManager and array list of restaurants we will be swiping through.
    public RestaurantPagerAdapter(FragmentManager fm, ArrayList<Restaurant> restaurants) {
        super(fm);
        mRestaurants = restaurants;
    }

//      returns an instance of the RestaurantDetailFragment for the restaurant in the position provided as an argument.
    @Override
    public Fragment getItem(int position) {
        return RestaurantDetailFragment.newInstance(mRestaurants, position);
    }

//    determines how many restaurants are in our arraylist. This lets our adapter know how many fragments it must create.
    @Override
    public int getCount() {
        return mRestaurants.size();
    }

//    getPageTitle updates the title that appears in the scrolling tabs at the top of the screen.
    @Override
    public CharSequence getPageTitle(int position) {
        return mRestaurants.get(position).getName();
    }

}