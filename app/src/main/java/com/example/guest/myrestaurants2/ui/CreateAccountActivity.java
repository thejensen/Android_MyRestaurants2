package com.example.guest.myrestaurants2.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.myrestaurants2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = CreateAccountActivity.class.getSimpleName();

    @Bind(R.id.createUserButton)
    Button mCreateUserButton;
    @Bind(R.id.nameEditText)
    EditText mNameEditText;
    @Bind(R.id.emailEditText) EditText mEmailEditText;
    @Bind(R.id.passwordEditText) EditText mPasswordEditText;
    @Bind(R.id.confirmPasswordEditText) EditText mConfirmPasswordEditText;
    @Bind(R.id.loginTextView) TextView mLoginTextView;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mAuthProgressDialog;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        createAuthStateListener();
        createAuthProgressDialog();

        mLoginTextView.setOnClickListener(this);
        mCreateUserButton.setOnClickListener(this);
    }

//    The system calls the onStart() method every time your activity becomes visible (whether it's being restarted or created for the first time).
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

//    Android calls onStop() when an activity is no longer visible. Once the activity is stopped, the system might destroy the instance if it needs to recover system memory. In extreme cases, the system might simply kill your app process without calling the activity's final onDestroy() callback. This is why it is important to use onStop() to release resources that might leak memory.
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View view) {

        if (view == mLoginTextView) {
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        if (view == mCreateUserButton) {
            createNewUser();
        }

    }

    private void createNewUser() {
        mName = mNameEditText.getText().toString().trim();
//        final String name = mNameEditText.getText().toString().trim();
        final String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();

        boolean validEmail = isValidEmail(email);
        boolean validName = isValidName(mName);
        boolean validPassword = isValidPassword(password, confirmPassword);
        if(!validEmail || !validName || !validPassword) return;

//        Finally, we show the ProgressDialog in our createNewUser() method with the line mAuthProgressDialog.show();. Notice that this line is only called after the form validation methods have returned true.
        mAuthProgressDialog.show();

//        createUserWithEmailAndPassword() method is complete (no matter what the outcome is), we dismiss the dialog entirely with the line mAuthProgressDialog.dismiss(); so that the user may either continue using the app, or view any error messages.
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                mAuthProgressDialog.dismiss();

                if (task.isSuccessful()) {
                    Log.d(TAG, "Authentication successful");
//                    The result of this task (now represents create account activity + main activity and its transition and account information) is the user's create account info inputs
//                    To pass the user object to our createFirebaseUserProfile() method, we can grab the result from the Task object returned in onComplete(). We may then retrieve the specific user by calling Firebase's getUser() method.
                    createFirebaseUserProfile(task.getResult().getUser());
                } else {
                    Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    private void createAuthStateListener() {
//        create new AuthStateListener by setting our member variable to the FirebaseAuth.AuthStateListener interface.
        mAuthListener = new FirebaseAuth.AuthStateListener() {

//            This interface listens to changes in the current AuthState. When there is a change (ie. a user becomes authenticated or signs out), this interface triggers the onAuthStateChanged() method.
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                The onAuthStateChanged() method returns FirebaseAuth data. Using this data, we can create a FirebaseUser by calling the getCurrentUser() method. We double-check that this user is not null before traveling to the MainActivity.
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.v(TAG, "user: " + user.getEmail());
                    Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    private boolean isValidEmail(String email) {
        boolean isGoodEmail = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if(!isGoodEmail) {
            mEmailEditText.setError("Please enter a valid email address");
            return false;
        }
        return isGoodEmail;
    }

    private boolean isValidName(String name) {
        if(name.equals("")) {
            mNameEditText.setError("Please enter your name");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password, String confirmPassword) {
        if(password.length() < 6) {
            mPasswordEditText.setError("Please create a password containing at least 6 characters");
            return false;
        } else if(!password.equals(confirmPassword)) {
            mPasswordEditText.setError("Passwords do not match!");
            return false;
        }
        return true;
    }

//    set the title and message values of the dialog box, and setCancelable() to false so that users cannot close the dialog manually. (We want this dialog box to remain in sight until the account is either successfully authenticated, or we have errors to display to the user).
    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading...");
        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
        mAuthProgressDialog.setCancelable(false);
    }

    private void createFirebaseUserProfile(final FirebaseUser user) {

//To set the name, we first need to build a new UserProfileChangeRequest object. This is a Firebase object used to request updates to user profile information.
        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
//                We call the setDisplayName() method to attach the user-entered name to the user's profile.
                .setDisplayName(mName)
                .build();

//        We then pass this UserProfileChangeRequest object into the updateProfile() method and attach an OnCompleteListener.
                user.updateProfile(addProfileName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    trigger the onComplete() method when the request is finished processing. If the request was successful, we log the name to the logcat.
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, user.getDisplayName());
                        }
                    }
                });
    }

}