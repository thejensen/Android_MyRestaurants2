package com.example.guest.myrestaurants2.adapters;

import android.content.Context;

import com.example.guest.myrestaurants2.models.Restaurant;
import com.example.guest.myrestaurants2.util.ItemTouchHelperAdapter;
import com.example.guest.myrestaurants2.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

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

    //    We also add the OnStartDragListener and the context to the constructor. The context will be needed when we eventually create an intent to navigate to the detail activity.
    public FirebaseRestaurantListAdapter(Class<Restaurant> modelClass, int modelLayout, Class<FirebaseRestaurantViewHolder> viewHolderClass, Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
//        In order to set our mRef member variable to the correct datatype, we call getRef() on an instance of Query to return the DatabaseReference.
        mRef = ref.getRef();
//        We will eventually set a TouchListener on the restaurant ImageView and use the OnStartDragListener to trigger to onStartDrag() callback.
        mOnStartDragListener = onStartDragListener;
        mContext = context;
    }

//    populateViewHolder() comes from an interface included as part of the FirebaseRecyclerAdapter class.
    @Override
    protected void populateViewHolder(FirebaseRestaurantViewHolder viewHolder, Restaurant model, int position) {
        viewHolder.bindRestaurant(model);
    }

//    onItemMove() and onItemDismiss() override methods from the ItemTouchHelperAdapter interface.
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}
