package com.neo.smartsolutions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.neo.smartsolutions.home.HomeFragment;
import com.neo.smartsolutions.home.Listener;
import com.neo.smartsolutions.home.AddLocationFragment;
import com.neo.smartsolutions.settings.SettingsFragment;

public class BaseActivity extends MainActivity implements NavigationView.OnNavigationItemSelectedListener, Listener {

    public static final int CONTROL_MODE_CODE = 0;
    public static final int SOLUTIONS_MODE_CODE = 1;
    private TextView toolbar_title;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity_base);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        setActionBarTitle("Locations");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        beginTransactionToAnotherFragment(new HomeFragment());
    }
    //methods for nav drawer
    private void setActionBarTitle(String title) {
        toolbar_title.setText(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        return true;
    }

    //todo the plus from the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.buttonAdd) {
            if("Locations".equals(toolbar_title.getText().toString())) {
                beginTransactionToAnotherFragment(new AddLocationFragment());
                setActionBarTitle("Add locations");
                menu.findItem(R.id.buttonAdd).setVisible(false);
            } else if ("Solutions".equals(toolbar_title.getText().toString())) {
                //todo add here the add solution
                beginTransactionToAnotherFragment(new SettingsFragment());
                setActionBarTitle("Settings");
                menu.findItem(R.id.buttonAdd).setVisible(false);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Toast.makeText(BaseActivity.this, "home", Toast.LENGTH_LONG).show();
            beginTransactionToAnotherFragment(new HomeFragment());
            setActionBarTitle("Locations");
            menu.findItem(R.id.buttonAdd).setVisible(true);
        } else if (id == R.id.nav_help) {
            Toast.makeText(BaseActivity.this, "help", Toast.LENGTH_LONG).show();
            beginTransactionToAnotherFragment(new SettingsFragment());
            setActionBarTitle("Help");
            menu.findItem(R.id.buttonAdd).setVisible(false);
        } else if (id == R.id.nav_settings) {
            Toast.makeText(BaseActivity.this, "settings", Toast.LENGTH_LONG).show();
            beginTransactionToAnotherFragment(new SettingsFragment());
            setActionBarTitle("Settings");
            menu.findItem(R.id.buttonAdd).setVisible(false);
        } else if (id == R.id.nav_logout) {
            Toast.makeText(BaseActivity.this, "logout", Toast.LENGTH_LONG).show();
            beginTransactionToAnotherFragment(new SettingsFragment());
            setActionBarTitle("Logout");
            menu.findItem(R.id.buttonAdd).setVisible(false);
        }
        return true;
    }

    public void beginTransactionToAnotherFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onTabModeSelected(int mode) {
        if (mode == CONTROL_MODE_CODE) {
            setActionBarTitle("Locations");
        } else {
            setActionBarTitle("Solutions");
        }
    }

    @Override
    public void onBackPressedFromAddLocationFragment() {
        beginTransactionToAnotherFragment(new HomeFragment());
        setActionBarTitle("Locations");
        menu.findItem(R.id.buttonAdd).setVisible(true);
    }

    @Override
    public void onSubmitButtonPressed(String name, String city, String street, int number) {
        Toast.makeText(BaseActivity.this, name + city + street + number, Toast.LENGTH_LONG).show();
        onBackPressedFromAddLocationFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}