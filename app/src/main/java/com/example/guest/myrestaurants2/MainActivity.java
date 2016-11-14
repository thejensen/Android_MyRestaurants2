package com.example.guest.myrestaurants2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mFindRestaurantsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState); causes Android to run all of the default behaviors for an activity. It's very rare that you would change this line.
        super.onCreate(savedInstanceState);
//        setContentView tells the activity which layout to use for the device screen. In this case, we are using activity_main.xml which we just styled.
//        R.layout.activity_main tells Android to use the main_activity.xml layout for this activity. R - which is short for Resources - gives us a way to access all of our files in the res directory.
        setContentView(R.layout.activity_main);
//        findViewById takes the ID of the Button in our layout and returns the view.
//        R.id.findRestaurantsButton returns the view of our button.
//        (Button) typecasts our view as Button. findViewById() will return a generic type View, but our mFindRestaurantsButton was declared as the specific View type of Button, so we need to cast it as such.
        mFindRestaurantsButton = (Button) findViewById(R.id.findRestaurantsButton);
//        The setOnClickListener() method takes a new onClickListener as a parameter. Let’s use Tab Autocompletion to write this out. If we starting typing new View and then press Tab, the rest of the code will be filled in for us.
        mFindRestaurantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Hello World!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
