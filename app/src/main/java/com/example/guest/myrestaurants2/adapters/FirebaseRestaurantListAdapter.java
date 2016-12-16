package com.example.guest.myrestaurants2.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.example.guest.myrestaurants2.R;
import com.example.guest.myrestaurants2.models.Restaurant;
import com.example.guest.myrestaurants2.ui.RestaurantDetailActivity;
import com.example.guest.myrestaurants2.ui.RestaurantDetailFragment;
import com.example.guest.myrestaurants2.util.ItemTouchHelperAdapter;
import com.example.guest.myrestaurants2.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Guest on 12/12/16.
 */
//a custom adapter that inherits all functionality of the FirebaseRecyclerAdapter, and also includes its own code implementing the ItemTouchHelperAdapter. This will allow us to handle both FirebaseRecyclerAdapter and drag-and-drop functionalities in the same adapter.
//We'll start by creating the new class in the adapters sub-package called FirebaseRestaurantListAdapter. We will extend the FirebaseRecyclerAdapter to inherit its functionality. We will also implement the ItemTouchHelperAdapterinterface, and create a constructor:


//    Just like we saw in SavedRestaurantsActivity, the FirebaseRecyclerAdapter requires the class of the data that will populate the RecyclerView, the layout we will inflate for each item, the ViewHolder class, and the database reference or query.
public class FirebaseRestaurantListAdapter extends FirebaseRecyclerAdapter<Restaurant, FirebaseRestaurantViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Restaurant> mRestaurants = new ArrayList<>();
    private int mOrientation;

    //    We also add the OnStartDragListener and the context to the constructor. The context will be needed when we eventually create an intent to navigate to the detail activity.
    public FirebaseRestaurantListAdapter(Class<Restaurant> modelClass, int modelLayout, Class<FirebaseRestaurantViewHolder> viewHolderClass, Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
//        In order to set our mRef member variable to the correct datatype, we call getRef() on an instance of Query to return the DatabaseReference.
        mRef = ref.getRef();
//        We will eventually set a TouchListener on the restaurant ImageView and use the OnStartDragListener to trigger to onStartDrag() callback.
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
//            Each time the adapter is constructed, the onChildAdded() will be triggered for each item in the given reference.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                We will use the add() method to add each returned item to the mRestaurants ArrayList so that we can access the list of restaurants throughout our adapter.
                mRestaurants.add(dataSnapshot.getValue(Restaurant.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    populateViewHolder() comes from an interface included as part of the FirebaseRecyclerAdapter class.
    @Override
    protected void populateViewHolder(final FirebaseRestaurantViewHolder viewHolder, Restaurant model, int position) {
        viewHolder.bindRestaurant(model);

        mOrientation = viewHolder.itemView.getResources().getConfiguration().orientation;
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            createDetailFragment(0);
        }

        viewHolder.mRestaurantImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = viewHolder.getAdapterPosition();
                if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    createDetailFragment(itemPosition);
                } else {
//                Just like we did previously in the ViewHolder, we create an intent, pass in the position and the ArrayList of Restaurants and then call the startActivity() method using the context passed in to our constructor.
                    Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
//                To get the current position of the click item, we can call the getAdapterPosition() method on the ViewHolder passed into the populateViewHolder() method.
//                Notice that the position information we're including with our intent when we say intent.putExtra("position", viewHolder.getAdapterPosition()); is an integer. Make sure the RestaurantDetailActivity is prepared to gather a position of the integer data type when it receives this intent:
                    intent.putExtra("position", viewHolder.getAdapterPosition());
                    intent.putExtra("restaurants", Parcels.wrap(mRestaurants));
                    mContext.startActivity(intent);
                }
            }
        });
    }

    private void createDetailFragment(int position) {
        // Creates new RestaurantDetailFragment with the given position:
        RestaurantDetailFragment detailFragment = RestaurantDetailFragment.newInstance(mRestaurants, position);
        // Gathers necessary components to replace the FrameLayout in the layout with the RestaurantDetailFragment:
        FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        //  Replaces the FrameLayout with the RestaurantDetailFragment:
        ft.replace(R.id.restaurantDetailContainer, detailFragment);
        // Commits these changes:
        ft.commit();
    }

//    onItemMove() and onItemDismiss() override methods from the ItemTouchHelperAdapter interface.
//    We call the notifyItemMoved() method to notify our adapter that the underlying data has changed.
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
//        We use Collections.swap() to update the order of our mRestaurants ArrayList items passing in the ArrayList of items and the starting and ending positions.
        Collections.swap(mRestaurants, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

//    To delete the dismissed item from Firebase, we can call the getRef() method, passing in an item's position and the FirebaseRecyclerAdapter will return the DatabaseReference for the given object. We can then call the removeValue() method to delete that object from Firebase. Once deleted, the FirebaseRecyclerAdapter will automatically update the view.
    @Override
    public void onItemDismiss(int position) {
//        We call the remove() method on our ArrayList of items in onItemDismiss() to remove the item from mRestaurants at the given position.
        mRestaurants.remove(position);
        getRef(position).removeValue();
    }

//    We can grab the index of each restaurant in the mRestaurants ArrayList by calling the ArrayList.indexOf() method, passing in the object which we would like to know the index. We will use this index as the index in Firebase.
//    We grab the reference of each item using the getRef() method, passing in the position of the item in the ArrayList.
//    We then use the setIndex() method we added to our Restaurant model to update the index property for each item.
//    We can finally use the setValue() method passing the Restaurant object whose index property we just updated.
    private void setIndexInFirebase() {
        for (Restaurant restaurant : mRestaurants) {
            int index = mRestaurants.indexOf(restaurant);
            DatabaseReference ref = getRef(index);
            restaurant.setIndex(Integer.toString(index));
            ref.setValue(restaurant);
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
        setIndexInFirebase();
        mRef.removeEventListener(mChildEventListener);
    }
}
