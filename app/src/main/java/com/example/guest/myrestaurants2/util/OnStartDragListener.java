package com.example.guest.myrestaurants2.util;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Guest on 12/12/16.
 */

//We will implement this interface in our SavedRestaurantsListActivity to attach the event listener to our custom ItemTouchHelper which will eventually be attached to our RecyclerView. This interface will pass events back to our adapter allowing us to attach the touch listener to an item in our ViewHolder.

public interface OnStartDragListener {
//    onStartDrag() will be called when the user begins a 'drag-and-drop' interaction with the touchscreen. viewHolder represents the RecyclerView holder corresponding to the object being dragged.
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
