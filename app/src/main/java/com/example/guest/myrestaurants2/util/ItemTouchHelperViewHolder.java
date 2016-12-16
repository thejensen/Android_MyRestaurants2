package com.example.guest.myrestaurants2.util;

/**
 * Created by jensese on 12/15/16.
 */

public interface ItemTouchHelperViewHolder {
//    onItemSelected() will handle updating the appearance of a selected item while the user is dragging-and-dropping it.
    void onItemSelected();
//    onItemClear() will remove the 'selected' state (and therefore the corresponding changes in appearance) from an item.
    void onItemClear();
}
