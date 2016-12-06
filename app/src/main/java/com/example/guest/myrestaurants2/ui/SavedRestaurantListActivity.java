package com.example.guest.myrestaurants2.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.guest.myrestaurants2.Constants;
import com.example.guest.myrestaurants2.R;
import com.example.guest.myrestaurants2.adapters.FirebaseRestaurantViewHolder;
import com.example.guest.myrestaurants2.models.Restaurant;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedRestaurantListActivity extends AppCompatActivity {
// First, we initialize our DatabaseReference, FirebaseRecyclerAdapter, and RecyclerView member variables.
    private DatabaseReference mRestaurantReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// We then pass in the activity_restaurants layout into the setContentView() method to display the correct layout.
        setContentView(R.layout.activity_restaurants2);
        ButterKnife.bind(this);

// Next, we set the mRestaurantReference using the "restaurants" child node key from our Constants class.
        mRestaurantReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RESTAURANTS);
        setUpFirebaseAdapter();
    }

// We then create a method to set up the FirebaseAdapter which takes the model class, the list item layout, the view holder, and the database reference as parameters.

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Restaurant, FirebaseRestaurantViewHolder>
                (Restaurant.class, R.layout.restaurant_list_item, FirebaseRestaurantViewHolder.class,
                        mRestaurantReference) {

// Inside of the populateViewHolder() method, we call the bindRestaurant() method on our viewHolder to set the appropriate text and image with the given restaurant.
            @Override
            protected void populateViewHolder(FirebaseRestaurantViewHolder viewHolder,
                                              Restaurant model, int position) {
                viewHolder.bindRestaurant(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
// We then set the adapter on our RecyclerView.
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

// Finally, we need to clean up the FirebaseAdapter. When the activity is destroyed, we need to call cleanup() on the adapter so that it can stop listening for changes in the Firebase database.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}