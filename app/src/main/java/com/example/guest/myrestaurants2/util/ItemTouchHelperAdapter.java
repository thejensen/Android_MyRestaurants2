package com.example.guest.myrestaurants2.util;

/**
 * Created by Guest on 12/12/16.
 */

//We will eventually implement this interface and override its methods in our custom FirebaseRecyclerAdapter to tell our adapter what to do when an item is moved or dismissed via the touchscreen. It will eventually pass event callbacks from our custom ItemTouchHelper class back up the chain.

public interface ItemTouchHelperAdapter {
//    onItemMove() will be called each time the user moves an item by dragging it across the touch screen. The argument fromPosition represents the location the item originally resided at. toPosition represents the location the user moved the item to.
    boolean onItemMove(int fromPosition, int toPosition);
//    onItemDismiss() is called when an item has been dismissed with a swipe motion. The parameter position represents the location of the dismissed item.
    void onItemDismiss(int position);
}
