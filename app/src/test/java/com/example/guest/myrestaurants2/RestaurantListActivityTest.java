package com.example.guest.myrestaurants2;

import android.os.Build;
import android.widget.ListView;

import com.example.guest.myrestaurants2.ui.RestaurantListActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)

public class RestaurantListActivityTest {
    private RestaurantListActivity activity;
    private ListView mRestaurantsListView;

////    We'll also add a rule to begin the RestaurantListActivity and define the mRestaurantListView before each test:
//    @Before
//    public void setUp() {
//        activity = Robolectric.setupActivity(RestaurantListActivity.class);
//        mRestaurantsListView = (ListView) activity.findViewById(R.id.listView);
//    }
//
////    checking that mRestaurantListView.getAdapter(); returns anything but null (which implies the adapter is correctly associated with the ListView), and that mRestaurantListView.getAdapter().getCount(); returns the correct number of restaurants from our restaurants array the ListView is responsible for displaying.
//    @Test
//    public void restaurantListViewPopulates() {
//        assertNotNull(mRestaurantsListView.getAdapter());
//        assertEquals(mRestaurantsListView.getAdapter().getCount(), 17);
//    }

}
