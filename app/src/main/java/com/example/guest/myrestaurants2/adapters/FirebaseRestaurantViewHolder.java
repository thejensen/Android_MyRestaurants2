package com.example.guest.myrestaurants2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.myrestaurants2.R;
import com.example.guest.myrestaurants2.models.Restaurant;
import com.squareup.picasso.Picasso;

/**
 * Created by jensese on 12/6/16.
 */

// We also implement the View.OnClickListener interface and set the click listener on our itemView.
public class FirebaseRestaurantViewHolder extends RecyclerView.ViewHolder {
// Just like in our RestaurantViewHolder, we add static variables to hold the width and height of our images for Picasso.
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

//    only the image view is drag enabled, but it takes the rest with it apparently
//    Considering our ViewHolder contains each of the views in a given item view, it may make sense to do this in the ViewHolder itself. However, the OnStartDragListenerpassed in to our adapter cannot be sent to our ViewHolder with a constructor, since the FirebaseRecyclerAdapter handles the construction of the ViewHolder internally.
//    Instead, we need to grant our adapter access to the ImageView by declaring it as a public member variable, and then set its OnTouchListener in the adapter. We'll begin this process by tweaking code in our ViewHolder to make the ImageView public:
    public ImageView mRestaurantImageView;

// We then add member variables to hold the view and context which we set in our constructor.
    View mView;
    Context mContext;

    public FirebaseRestaurantViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

// In our bindRestaurant() method, we first bind the views and then set the image and text views.
    public void bindRestaurant(Restaurant restaurant) {
        mRestaurantImageView = (ImageView) mView.findViewById(R.id.restaurantImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.restaurantNameTextView);
        TextView categoryTextView = (TextView) mView.findViewById(R.id.categoryTextView);
        TextView ratingTextView = (TextView) mView.findViewById(R.id.ratingTextView);

        Picasso.with(mContext)
                .load(restaurant.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mRestaurantImageView);

        nameTextView.setText(restaurant.getName());
        categoryTextView.setText(restaurant.getCategories().get(0));
        ratingTextView.setText("Rating: " + restaurant.getRating() + "/5");
    }
}