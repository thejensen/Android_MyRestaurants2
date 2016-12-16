package com.example.guest.myrestaurants2.adapters;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.example.guest.myrestaurants2.models.Restaurant;
import com.example.guest.myrestaurants2.util.ItemTouchHelperAdapter;
import com.example.guest.myrestaurants2.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

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
        viewHolder.mRestaurantImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });
    }

//    onItemMove() and onItemDismiss() override methods from the ItemTouchHelperAdapter interface.
//    We call the notifyItemMoved() method to notify our adapter that the underlying data has changed.
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

//    To delete the dismissed item from Firebase, we can call the getRef() method, passing in an item's position and the FirebaseRecyclerAdapter will return the DatabaseReference for the given object. We can then call the removeValue() method to delete that object from Firebase. Once deleted, the FirebaseRecyclerAdapter will automatically update the view.
    @Override
    public void onItemDismiss(int position) {
        getRef(position).removeValue();
    }
}
