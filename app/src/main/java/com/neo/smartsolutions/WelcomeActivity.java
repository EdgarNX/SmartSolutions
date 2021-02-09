package com.neo.smartsolutions;

import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.neo.smartsolutions.welcome.LogInFragment;
import com.neo.smartsolutions.welcome.OnPressedListener;
import com.neo.smartsolutions.welcome.SingUpFragment;
import com.neo.smartsolutions.welcome.WelcomeFragment;

public class WelcomeActivity extends MainActivity implements OnPressedListener {

    public static final String EMAIL_MESSAGE_KEY = "email";
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
    }

    @Override
    public void onModeItemSelected(int mode) {
        if (mode == SIGN_UP_MODE_CODE) {
            SingUpFragment singUpFragment = new SingUpFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up, R.anim.slide_in_up, R.anim.slide_out_up)
                    .replace(R.id.form_placeholder, singUpFragment) // replace flContainer
                    .addToBackStack(null)
                    .commit();
        } else {
            LogInFragment logInFragment = new LogInFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up, R.anim.slide_in_up, R.anim.slide_out_up)
                    .replace(R.id.form_placeholder, logInFragment) // replace flContainer
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onSignUpButtonPressed(String email, String password, String country) {
        Toast.makeText(WelcomeActivity.this, email + password + country, Toast.LENGTH_LONG).show();
        goToTheNextActivity("edgarnemeth@gmail.com");
    }

    @Override
    public void onLogInButtonPressed(String email, String password) {
        Toast.makeText(WelcomeActivity.this, email + password, Toast.LENGTH_LONG).show();
        goToTheNextActivity("edgarnemeth@gmail.com");
    }

    private void goToTheNextActivity(String email) {
        Intent intentToBaseActivity = new Intent(WelcomeActivity.this, BaseActivity.class);
        intentToBaseActivity.putExtra(EMAIL_MESSAGE_KEY, email);
        startActivity(intentToBaseActivity);
    }
}