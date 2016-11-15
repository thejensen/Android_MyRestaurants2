package com.example.guest.myrestaurants2;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.findRestaurantsButton) Button mFindRestaurantsButton;
    @Bind(R.id.locationEditText) EditText mLocationEditText;
    @Bind(R.id.appNameTextView) TextView mAppNameTextView;
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState); causes Android to run all of the default behaviors for an activity. It's very rare that you would change this line.
        super.onCreate(savedInstanceState);
//        setContentView tells the activity which layout to use for the device screen. In this case, we are using activity_main.xml which we just styled.
//        R.layout.activity_main tells Android to use the main_activity.xml layout for this activity. R - which is short for Resources - gives us a way to access all of our files in the res directory.
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//
        Typeface pacificoFont = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        mAppNameTextView.setTypeface(pacificoFont);

//        The setOnClickListener() method takes a new onClickListener as a parameter. Let’s use Tab Autocompletion to write this out. If we starting typing new View and then press Tab, the rest of the code will be filled in for us.
        mFindRestaurantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                We use the getText() method to grab the inputted value of our EditText and save it into a new String variable. The getText() method returns an editable data type so we have to convert it to a String using the toString() method.
                String location = mLocationEditText.getText().toString();
                Log.d(TAG, location);
//                Here, we are constructing a new instance of the Intent class with the line Intent intent = new Intent(MainActivity.this, RestaurantsActivity.class);. As you can see this takes two parameters: The current context, and the Activity class we want to start.
                Intent intent = new Intent(MainActivity.this, RestaurantsActivity2.class);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        });


    }
}
