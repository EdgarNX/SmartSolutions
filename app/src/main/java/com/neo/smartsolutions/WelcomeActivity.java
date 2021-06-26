package com.neo.smartsolutions;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.neo.smartsolutions.services.auth.CloudAuth;
import com.neo.smartsolutions.services.storage.CloudStorage;
import com.neo.smartsolutions.services.storage.LocalStorage;
import com.neo.smartsolutions.welcome.LogInFragment;
import com.neo.smartsolutions.welcome.OnPressedListener;
import com.neo.smartsolutions.welcome.SingUpFragment;
import com.neo.smartsolutions.welcome.WelcomeFragment;


public class WelcomeActivity extends MainActivity implements OnPressedListener {

    public static final int SIGN_UP_MODE_CODE = 0;
    public static final int LOG_IN_MODE_CODE = 1;

    private CloudAuth cloudAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w_activity_welcome);

        getDatabasesInstantiated();

        if (savedInstanceState == null) {
            WelcomeFragment welcomeFragment = new WelcomeFragment();

            FragmentTransaction fragment = getSupportFragmentManager().beginTransaction();

            fragment.add(R.id.form_placeholder, welcomeFragment);
            fragment.commit();
        }

        LocalStorage localStorage = new LocalStorage(mLocationViewModel, mDeviceViewModel);
        CloudStorage cloudStorage = new CloudStorage(localStorage, fAuth, fStore, this);
        cloudAuth = new CloudAuth(this, localStorage, fAuth, cloudStorage, this);
    }

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

    @Override
    public void onSignUpButtonPressed(String email, String password, String country) {
        showProgressDialog();
        cloudAuth.signUp(email, password, country);
    }

    @Override
    public void onGoogleSignUpButtonPressed(String idToken) {
        //todo Google Sign Up
    }

    @Override
    public void onFacebookSignUpButtonPressed() {
        //todo Facebook Sign Up
    }

    @Override
    public void onLogInButtonPressed(String email, String password) {
        showProgressDialog();
        cloudAuth.logIn(email, password);
    }

    @Override
    public void onGoogleLogInButtonPressed(String idToken) {
        //todo Google Log In
    }

    @Override
    public void onFacebookLogInButtonPressed() {
        //todo Facebook Log In
    }

    private void goToTheNextActivity() {
        Intent intentToHomeActivity = new Intent(WelcomeActivity.this, HomeActivity.class);
        startActivity(intentToHomeActivity);
    }

    private final FirebaseAuth.AuthStateListener fAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                goToTheNextActivity();
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        fAuth.addAuthStateListener(fAuthListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (fAuthListener != null) {
            fAuth.removeAuthStateListener(fAuthListener);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}