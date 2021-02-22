package com.neo.smartsolutions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.neo.smartsolutions.devices.AddDeviceFragment;
import com.neo.smartsolutions.devices.DeviceFragment;
import com.neo.smartsolutions.devices.device_types.IntensityFragment;
import com.neo.smartsolutions.devices.device_types.RelayFragment;
import com.neo.smartsolutions.help.HelpFragment;
import com.neo.smartsolutions.home.HomeFragment;
import com.neo.smartsolutions.home.Listener;
import com.neo.smartsolutions.locations.AddLocationFragment;
import com.neo.smartsolutions.settings.SettingsFragment;
import com.neo.smartsolutions.locations.location_local_db.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HomeActivity extends MainActivity implements NavigationView.OnNavigationItemSelectedListener, Listener {

    public static String DEVICE_STATUS = "notSet";
    public static final int CONTROL_MODE_CODE = 0;
    public static final int SOLUTIONS_MODE_CODE = 1;
    private TextView toolbar_title;
    private Menu menu;

    private String currentLocation;
    private String currentDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity_base);

        getDatabasesInstantiated();

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

        View headerView = navigationView.getHeaderView(0);
        TextView textUserEmail = (TextView) headerView.findViewById(R.id.textUserEmail);
        String currentUserEmail = fAuth.getCurrentUser().getEmail();
        textUserEmail.setText(currentUserEmail);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
        drawer.closeDrawer(GravityCompat.START);
    }

    //firebase

    private void logOut() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.logout_question);
        alert.setCancelable(false);
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fAuth.signOut();
                mLocationViewModel.deleteAll();
                onLogOut();
            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }

    private void addLocationInDatabase(String name, String city, String street, int number) {
        String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        Map<String, Object> location = new HashMap<>();
        location.put("name", name);
        location.put("city", city);
        location.put("street", street);
        location.put("number", number);

        fStore.collection("users")
                .document(userID)
                .collection("locations")
                .document(name)
                .set(location)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG_STORAGE, "Added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG_STORAGE, "Error adding document", e);
                    }
                });
    }

    private void addDeviceInDatabase(String name, String type, String code) {
        String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        String location = getTheCurrentLocation();

        Map<String, Object> device = new HashMap<>();
        device.put("name", name);
        device.put("type", type);
        device.put("code", code);

        fStore.collection("users")
                .document(userID)
                .collection(location)
                .document(name)
                .set(device)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG_STORAGE, "Added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG_STORAGE, "Error adding document", e);
                    }
                });
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
                beginTransactionToAnotherFragment(new AddLocationFragment(), "Add locations", false);
            } else if ("Solutions".equals(toolbar_title.getText().toString())) {
                //todo add here the add solution
                beginTransactionToAnotherFragment(new SettingsFragment(), "Settings", false);
            } else {
                beginTransactionToAnotherFragment(new AddDeviceFragment(), "Add device", false);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            if (!"Locations".equals(toolbar_title.getText().toString())) {
                beginTransactionToAnotherFragment(new HomeFragment(), "Locations", true);
            }
        } else if (id == R.id.nav_help) {
            beginTransactionToAnotherFragment(new HelpFragment(), "Help", false);
        } else if (id == R.id.nav_settings) {
            beginTransactionToAnotherFragment(new SettingsFragment(), "Settings", false);
        } else if (id == R.id.nav_logout) {
            logOut();
        }
        return true;
    }

    //methods

    void addLocationInLocalDb(String number, String city, String street, String name) {
        Location location = new Location(name, city, street, number);
        mLocationViewModel.insert(location);
    }

    private void onLogOut() {
        Intent intentToWelcomeActivity = new Intent(HomeActivity.this, WelcomeActivity.class);
        startActivity(intentToWelcomeActivity);
    }

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

    private void saveTheCurrentLocation(String locationName) {
        currentLocation = locationName;
    }

    private String getTheCurrentLocation() {
        return currentLocation;
    }

    private void saveTheCurrentDevice(String deviceName) {
        currentDevice = deviceName;
    }

    private String getTheCurrentDevice() {
        return currentDevice;
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
        beginTransactionToAnotherFragment(new HomeFragment(), "Locations", true);
    }

    @Override
    public void onSubmitButtonPressedFromAddLocation(String name, String city, String street, int number) {
        addLocationInDatabase(name, city, street, number);
        addLocationInLocalDb(name, city, street, Integer.toString(number));
        onBackPressedToLocationFragment();
    }

    @Override
    public void onLocationSelected(String locationName) {
        //todo load here the hole array of devices
        saveTheCurrentLocation(locationName);
        beginTransactionToAnotherFragment(new DeviceFragment(), locationName, true);
    }

    @Override
    public void onDeviceSelected(String deviceName, String deviceType, String status) {
        //todo here you can edit the devices

        saveTheCurrentDevice(deviceName);
        DEVICE_STATUS = status;

        if ("relay".equals(deviceType)) {
            beginTransactionToAnotherFragment(new RelayFragment(), deviceName, false);
        } else {
            //if it is intensity type
            beginTransactionToAnotherFragment(new IntensityFragment(), deviceName, false);

        }
    }

    @Override
    public void onBackPressedToDeviceFragment() {
        //todo reload here the new hole array of devices
        beginTransactionToAnotherFragment(new DeviceFragment(), getTheCurrentLocation(), true);
    }

    @Override
    public void onSubmitButtonPressedFromAddDevice(String name, String type, String code) {
        //todo don't forget the status to update in firebase
        addDeviceInDatabase(name, type, code);
        onBackPressedToDeviceFragment();
    }

    @Override
    public void onOnOffButtonPressedInRelayFragment(String status) {
        //todo change here the status what we obtain in the layout to be visible also in firebase
        DEVICE_STATUS = status;
    }

    @Override
    public void onIntensitySubmitButtonPressed(String status) {
        //todo change here the status what we obtain in the layout to be visible also in firebase
        Toast.makeText(HomeActivity.this, status, Toast.LENGTH_LONG).show();
        DEVICE_STATUS = status;
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