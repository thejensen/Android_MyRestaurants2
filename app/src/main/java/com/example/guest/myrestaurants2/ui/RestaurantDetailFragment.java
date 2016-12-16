package com.example.guest.myrestaurants2.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.myrestaurants2.Constants;
import com.example.guest.myrestaurants2.R;
import com.example.guest.myrestaurants2.models.Restaurant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RestaurantDetailFragment extends Fragment implements View.OnClickListener {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;
    @Bind(R.id.restaurantImageView) ImageView mImageLabel;
    @Bind(R.id.restaurantNameTextView) TextView mNameLabel;
    @Bind(R.id.cuisineTextView) TextView mCategoriesLabel;
    @Bind(R.id.ratingTextView) TextView mRatingLabel;
    @Bind(R.id.websiteTextView) TextView mWebsiteLabel;
    @Bind(R.id.phoneTextView) TextView mPhoneLabel;
    @Bind(R.id.addressTextView) TextView mAddressLabel;
    @Bind(R.id.saveRestaurantButton) TextView mSaveRestaurantButton;

    private Restaurant mRestaurant;

//    This method is used instead of a constructor and returns a new instance of RestaurantDetailFragment. The Parcelor library adds the Restaurant object to a bundle and set the bundle as the argument for our new RestaurantDetail Fragment. This allows us to access necessary data when a new instance of our fragment is created.
    public static RestaurantDetailFragment newInstance(Restaurant restaurant) {
        RestaurantDetailFragment restaurantDetailFragment = new RestaurantDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("restaurant", Parcels.wrap(restaurant));
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

//    onCreate is called when the fragment is created. We unwrap our restaurant object from the arguments we added in the newInstance method.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRestaurant = Parcels.unwrap(getArguments().getParcelable("restaurant"));
    }

//    the restaurant object is used to set our ImageView and TextViews.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
        ButterKnife.bind(this, view);

//        scale down images before setting the ImageView source in order to avoid consuming too much memory with large images ()
        Picasso.with(view.getContext())
                .load(mRestaurant.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mImageLabel);

        mNameLabel.setText(mRestaurant.getName());
        mCategoriesLabel.setText(android.text.TextUtils.join(", ", mRestaurant.getCategories()));
        mRatingLabel.setText(Double.toString(mRestaurant.getRating()) + "/5");
        mPhoneLabel.setText(mRestaurant.getPhone());
        mAddressLabel.setText(android.text.TextUtils.join(", ", mRestaurant.getAddress()));

        mWebsiteLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);

        mSaveRestaurantButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
//        if mWebsiteLabel is clicked, we create a new implicit intent called webIntent and provide it two arguments: The ACTION_VIEW activity, responsible for displaying data to the user, and the restaurant's website URL. We start this new activity by calling startActivity().
        if (v == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mRestaurant.getWebsite()));
            startActivity(webIntent);
        }
//        If mPhoneLabel is clicked, we create an implicit intent called phoneIntent, and provide it two arguments: The ACTION_DIAL activity, which dials the number in the user's phone app, and the restaurant's telephone number. Again, we start this activity by calling startActivity().
        if (v == mPhoneLabel) {
            Intent phoneIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + mRestaurant.getPhone()));
            startActivity(phoneIntent);
        }
//        If mAddressLabel is selected, we create an implicit intent called mapIntent, and provide it two arguments: The ACTION_VIEW activity, and the restaurant's longitude and latitude, and begin the activity with startActivity().
//        ?q=(" + mRestaurant.getName() + ")" creates a marker on the map with a label of the restaurant’s name.
        if (v == mAddressLabel) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + mRestaurant.getLatitude() + "," + mRestaurant.getLongitude() + "?q=(" + mRestaurant.getName() + ")"));
            startActivity(mapIntent);
        }
//        In the conditional, we create a new DatabaseReference object called restaurantRef using the getInstance() and getReference() methods, passing in the key for our restaurants node.
//        Then, we call push() and setValue() , passing in our restaurant object as an argument, to create a node for the selected restaurant with a unique push id.
//        Finally, we display a brief toast to confirm the restaurant has been saved.
        if (v == mSaveRestaurantButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            DatabaseReference restaurantRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
                    .child(uid);

            DatabaseReference pushRef = restaurantRef.push();
            String pushId = pushRef.getKey();
            mRestaurant.setPushId(pushId);
            pushRef.setValue(mRestaurant);

            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }
}