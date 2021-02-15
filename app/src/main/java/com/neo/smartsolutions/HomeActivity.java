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
import com.neo.smartsolutions.devices.AddDeviceFragment;
import com.neo.smartsolutions.devices.DeviceFragment;
import com.neo.smartsolutions.home.HomeFragment;
import com.neo.smartsolutions.home.Listener;
import com.neo.smartsolutions.locations.AddLocationFragment;
import com.neo.smartsolutions.settings.SettingsFragment;

import java.util.Objects;

public class HomeActivity extends MainActivity implements NavigationView.OnNavigationItemSelectedListener, Listener {

    public static final int CONTROL_MODE_CODE = 0;
    public static final int SOLUTIONS_MODE_CODE = 1;
    private TextView toolbar_title;
    private Menu menu;

    private String currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity_base);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        setActionBarTitle("Locations");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

//        beginTransactionToAnotherFragment(new HomeFragment(),"Locations", true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout,  new HomeFragment()).commit();
        drawer.closeDrawer(GravityCompat.START);
    }

    //navigation drawer

    private void setActionBarTitle(String title) {
        toolbar_title.setText(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.buttonAdd) {
            if ("Locations".equals(toolbar_title.getText().toString())) {
                beginTransactionToAnotherFragment(new AddLocationFragment(),"Add locations",false);
//                setActionBarTitle("Add locations");
//                menu.findItem(R.id.buttonAdd).setVisible(false);
            } else if ("Solutions".equals(toolbar_title.getText().toString())) {
                //todo add here the add solution
                beginTransactionToAnotherFragment(new SettingsFragment(),"Settings", false);
//                setActionBarTitle("Settings");
//                menu.findItem(R.id.buttonAdd).setVisible(false);
            } else {
                //todo when an home page is opened here
                beginTransactionToAnotherFragment(new AddDeviceFragment(),"Add device", false);
//                setActionBarTitle("Add device");
//                menu.findItem(R.id.buttonAdd).setVisible(false);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Toast.makeText(HomeActivity.this, "home", Toast.LENGTH_LONG).show();
            beginTransactionToAnotherFragment(new HomeFragment(), "Locations", true);
//            setActionBarTitle("Locations");
//            menu.findItem(R.id.buttonAdd).setVisible(true);
        } else if (id == R.id.nav_help) {
            Toast.makeText(HomeActivity.this, "help", Toast.LENGTH_LONG).show();
            beginTransactionToAnotherFragment(new SettingsFragment(), "Help", false);
//            setActionBarTitle("Help");
//            menu.findItem(R.id.buttonAdd).setVisible(false);
        } else if (id == R.id.nav_settings) {
            Toast.makeText(HomeActivity.this, "settings", Toast.LENGTH_LONG).show();
            beginTransactionToAnotherFragment(new SettingsFragment(), "Settings", false);
//            setActionBarTitle("Settings");
//            menu.findItem(R.id.buttonAdd).setVisible(false);
        } else if (id == R.id.nav_logout) {
            Toast.makeText(HomeActivity.this, "logout", Toast.LENGTH_LONG).show();
            beginTransactionToAnotherFragment(new SettingsFragment(), "Logout", false);
//            setActionBarTitle("Logout");
//            menu.findItem(R.id.buttonAdd).setVisible(false);
        }
        return true;
    }

    //methods 
    
    public void beginTransactionToAnotherFragment(Fragment fragment, String layoutTitle, boolean shotAddButton) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up, R.anim.slide_in_up, R.anim.slide_out_up)
                .replace(R.id.frameLayout, fragment)
                .commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        setActionBarTitle(layoutTitle);

        menu.findItem(R.id.buttonAdd).setVisible(shotAddButton);
    }

    private void saveTheCurrentLocation(String locationName) { currentLocation = locationName; }

    private String getTheCurrentLocation() {
        return currentLocation;
    }
    
    //listeners
    
    @Override
    public void onTabModeSelected(int mode) {
        if (mode == CONTROL_MODE_CODE) {
            setActionBarTitle("Locations");
        } else {
            setActionBarTitle("Solutions");
        }
    }

    @Override
    public void onBackPressedToLocationFragment() {
        //todo reload here the hole array of locations
        beginTransactionToAnotherFragment(new HomeFragment(), "Locations", true);
//        setActionBarTitle("Locations");
//        menu.findItem(R.id.buttonAdd).setVisible(true);
    }

    @Override
    public void onSubmitButtonPressedFromLocation(String name, String city, String street, int number) {
        Toast.makeText(HomeActivity.this, name + city + street + number, Toast.LENGTH_LONG).show();
        onBackPressedToLocationFragment();
    }

    @Override
    public void onLocationSelected(String locationName) {
        //todo load here the hole array of devices
        saveTheCurrentLocation(locationName);

        beginTransactionToAnotherFragment(new DeviceFragment(), locationName, true);
//        setActionBarTitle(locationName);
//        menu.findItem(R.id.buttonAdd).setVisible(true);
    }

    @Override
    public void onDeviceSelected(String deviceName, String deviceType) {
        //todo here you can edit the devices
        if("relay".equals(deviceType)) {
            beginTransactionToAnotherFragment(new DeviceFragment(),deviceName,false);

        } else {
            //if it is intensity type
            beginTransactionToAnotherFragment(new DeviceFragment(),deviceName,false);

        }
    }

    @Override
    public void onBackPressedFromAddDeviceFragment() {
        //todo reload here the new hole array of devices
        beginTransactionToAnotherFragment(new DeviceFragment(),getTheCurrentLocation(),true);
//        setActionBarTitle(getTheCurrentLocation());
//        menu.findItem(R.id.buttonAdd).setVisible(true);
    }

    @Override
    public void onSubmitButtonPressedFromDevice(String name, String type, String code) {
        //todo don't forget the status to update in firebase
        Toast.makeText(HomeActivity.this, name + type + code, Toast.LENGTH_LONG).show();
        onBackPressedFromAddDeviceFragment();
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