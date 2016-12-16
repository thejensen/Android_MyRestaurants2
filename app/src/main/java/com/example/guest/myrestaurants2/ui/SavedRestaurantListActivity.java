package com.example.guest.myrestaurants2.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.guest.myrestaurants2.Constants;
import com.example.guest.myrestaurants2.R;
import com.example.guest.myrestaurants2.adapters.FirebaseRestaurantListAdapter;
import com.example.guest.myrestaurants2.adapters.FirebaseRestaurantViewHolder;
import com.example.guest.myrestaurants2.models.Restaurant;
import com.example.guest.myrestaurants2.util.OnStartDragListener;
import com.example.guest.myrestaurants2.util.SimpleItemTouchHelperCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedRestaurantListActivity extends AppCompatActivity implements OnStartDragListener {
// First, we initialize our DatabaseReference, FirebaseRecyclerAdapter, and RecyclerView member variables.
    private DatabaseReference mRestaurantReference;
    private FirebaseRestaurantListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// We then pass in the activity_restaurants layout into the setContentView() method to display the correct layout.
        setContentView(R.layout.activity_restaurants2);
        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

// Next, we set the mRestaurantReference using the "restaurants" child node key from our Constants class.
        mRestaurantReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
                .child(uid);

        setUpFirebaseAdapter();
    }

// We then create a method to set up the FirebaseAdapter which takes the model class, the list item layout OF THE DRAGGABLE KIND ;), the view holder, and the database reference as parameters.

    private void setUpFirebaseAdapter() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mRestaurantReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
                .child(uid);

        mFirebaseAdapter = new FirebaseRestaurantListAdapter(Restaurant.class,
                R.layout.restaurant_list_item_drag, FirebaseRestaurantViewHolder.class,
                mRestaurantReference, this, this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
// We then set the adapter on our RecyclerView.
        mRecyclerView.setAdapter(mFirebaseAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);

        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

// Finally, we need to clean up the FirebaseAdapter. When the activity is destroyed, we need to call cleanup() on the adapter so that it can stop listening for changes in the Firebase database.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}