package com.neo.smartsolutions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.smartsolutions.welcome.LogInFragment;
import com.neo.smartsolutions.welcome.SingUpFragment;
import com.neo.smartsolutions.welcome.WelcomeFragment;

public class WelcomeActivity extends AppCompatActivity implements WelcomeFragment.OnModeItemSelectedListener {

    public static final int SIGN_UP_MODE_CODE = 0;
    public static final int LOG_IN_MODE_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        if (savedInstanceState == null) {
            WelcomeFragment welcomeFragment = new WelcomeFragment();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.add(R.id.form_placeholder, welcomeFragment);
            ft.commit();
        }
    }

    @Override
    public void onModeItemSelected(int mode) {

        if(mode == SIGN_UP_MODE_CODE) {
            SingUpFragment singUpFragment = new SingUpFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up)
                    .replace(R.id.form_placeholder, singUpFragment) // replace flContainer
                    .addToBackStack(null)
                    .commit();
        } else {
            LogInFragment logInFragment = new LogInFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up)
                    .replace(R.id.form_placeholder, logInFragment) // replace flContainer
                    .addToBackStack(null)
                    .commit();
        }

    }
}