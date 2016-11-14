package com.example.guest.myrestaurants2;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class MainActivityInstrumentationTest {
//    The code in @Rule tells our device which activity to launch before each test. Here, we're instructing the instrumentation tests to launch the MainActivity before each test
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

//    This test verifies that the instrumentation test can type the text "Portland" into our locationEditText, and the text "Portland" will be visible in the EditText.
    @Test
    public void validateEditText() {
//        onView() specifies that we want to interact with a view
//        withId() is a ViewMatcher method that allows us to find specific views by ID
//        typeText() is a ViewAction method that allows us to type the specified text into our EditText
//        matches() is a ViewAssertion method that validates the specific properties of the given view
        onView(withId(R.id.locationEditText)).perform(typeText("Portland")).check(matches(withText("Portland")));
    }

//    check if the location entered into our form is successfully being passed to our RestaurantsActivity with the intent extra we just recently created.
    @Test
        public void locationIsSentToRestaurantsActivity() {
        String location = "Portland";
        onView(withId(R.id.locationEditText)).perform(typeText(location));
        onView(withId(R.id.findRestaurantsButton)).perform(click());
        onView(withId(R.id.locationTextView)).check(matches(withText("Here are all the restaurants near: " + location)));
    }
}
