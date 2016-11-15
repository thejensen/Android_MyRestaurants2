package com.example.guest.myrestaurants2;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.IsNot.not;


public class RestaurantsActivityInstrumentationTest {
    @Rule
    public ActivityTestRule<RestaurantsActivity2> activityTestRule = new ActivityTestRule<>(RestaurantsActivity2.class);

//    Here, we're instructing Espresso to check that clicking on the first item (.atPosition(0)) in our ListView results in a Toast that displays "Mi Mero Mole" (note that you may need to change "Mi Mero Mole" to whatever the first restaurant in your restaurants array is) when clicked.
    @Test
    public void listItemClickDesiplaysToastWithCorrectRestaurant() {
        View activityDecorView = activityTestRule.getActivity().getWindow().getDecorView();
        String restaurantName = "HUB";
        onData(anything())
                .inAdapterView(withId(R.id.listView))
                .atPosition(0)
                .perform(click());
        onView(withText(restaurantName)).inRoot(withDecorView(not(activityDecorView)))
                .check(matches(withText(restaurantName)));
    }

}
