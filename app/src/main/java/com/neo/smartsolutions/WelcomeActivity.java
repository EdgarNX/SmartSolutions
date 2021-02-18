package com.neo.smartsolutions;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.neo.smartsolutions.welcome.LogInFragment;
import com.neo.smartsolutions.welcome.OnPressedListener;
import com.neo.smartsolutions.welcome.SingUpFragment;
import com.neo.smartsolutions.welcome.WelcomeFragment;

import java.util.Objects;

public class WelcomeActivity extends MainActivity implements OnPressedListener {

    private static final String EMAIL_PASSWORD_TAG = "EmailPasswordActivity";
    public static final String EMAIL_MESSAGE_KEY = "email";
    public static final String UID_MESSAGE_KEY = "uid";
    public static final int SIGN_UP_MODE_CODE = 0;
    public static final int LOG_IN_MODE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w_activity_welcome);

        if (savedInstanceState == null) {
            WelcomeFragment welcomeFragment = new WelcomeFragment();

            FragmentTransaction fragment = getSupportFragmentManager().beginTransaction();

            fragment.add(R.id.form_placeholder, welcomeFragment);
            fragment.commit();
        }

        mAuth = FirebaseAuth.getInstance();

        showProgressDialog();
    }

    //firebase

    private void signUp(String email, String password) {
        showProgressDialog();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    hideProgressDialog();
                    Toast.makeText(WelcomeActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    goToTheNextActivity();
                }
            }
        });
    }

    private void logIn(String email, String password) {
        showProgressDialog();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(EMAIL_PASSWORD_TAG, "logInWithEmail:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
                    hideProgressDialog();
                    Toast.makeText(WelcomeActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    goToTheNextActivity();
                }
            }
        });
    }

    //methods

    @Override
    public void onModeItemSelected(int mode) {
        if (mode == SIGN_UP_MODE_CODE) {
            SingUpFragment singUpFragment = new SingUpFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up, R.anim.slide_in_up, R.anim.slide_out_up)
                    .replace(R.id.form_placeholder, singUpFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            LogInFragment logInFragment = new LogInFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up, R.anim.slide_in_up, R.anim.slide_out_up)
                    .replace(R.id.form_placeholder, logInFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void goToTheNextActivity() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Intent intentToHomeActivity = new Intent(WelcomeActivity.this, HomeActivity.class);
        intentToHomeActivity.putExtra(EMAIL_MESSAGE_KEY, currentUser.getEmail());
        intentToHomeActivity.putExtra(UID_MESSAGE_KEY, currentUser.getUid());
        startActivity(intentToHomeActivity);
    }

    @Override
    public void onSignUpButtonPressed(String email, String password, String country) {
        Toast.makeText(WelcomeActivity.this, email + password + country, Toast.LENGTH_LONG).show();
        //todo upload the county into the firebase
        signUp(email, password);
    }

    @Override
    public void onLogInButtonPressed(String email, String password) {
        logIn(email, password);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            goToTheNextActivity();
        } else {
            hideProgressDialog();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}