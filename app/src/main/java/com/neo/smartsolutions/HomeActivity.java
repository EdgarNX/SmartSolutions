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
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callback.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather;
import com.neo.smartsolutions.devices.AddDeviceFragment;
import com.neo.smartsolutions.devices.DeviceFragment;
import com.neo.smartsolutions.devices.UpdateDevices;
import com.neo.smartsolutions.devices.device_local_db.Device;
import com.neo.smartsolutions.devices.device_types.IntensityFragment;
import com.neo.smartsolutions.devices.device_types.RelayFragment;
import com.neo.smartsolutions.help.HelpFragment;
import com.neo.smartsolutions.home.HomeFragment;
import com.neo.smartsolutions.home.Listener;
import com.neo.smartsolutions.locations.AddLocationFragment;
import com.neo.smartsolutions.services.storage.CloudStorage;
import com.neo.smartsolutions.services.storage.LocalStorage;
import com.neo.smartsolutions.services.Weather;
import com.neo.smartsolutions.settings.SettingsFragment;

import java.util.Objects;

public class HomeActivity extends MainActivity implements NavigationView.OnNavigationItemSelectedListener, Listener {

    public static String CURRENT_LOCATION_FOR_DATABASE;
    public static String CURRENT_DEVICE_FOR_DATABASE;
    public static String DEVICE_STATUS = "notSet";
    public static final int CONTROL_MODE_CODE = 0;
    public static final int SOLUTIONS_MODE_CODE = 1;
    private TextView toolbar_title;
    private Menu menu;

    private OpenWeatherMapHelper weatherHelper;
    private LocalStorage localStorage;
    private CloudStorage cloudStorage;

    private String currentLocation;
    private Device currentDevice;

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
        String currentUserEmail = Objects.requireNonNull(fAuth.getCurrentUser()).getEmail();
        textUserEmail.setText(currentUserEmail);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
        drawer.closeDrawer(GravityCompat.START);

        weatherHelper = new OpenWeatherMapHelper(getString(R.string.OPEN_WEATHER_MAP_API_KEY));
        localStorage = new LocalStorage(mLocationViewModel, mDeviceViewModel);
        cloudStorage = new CloudStorage(localStorage, fAuth, fStore, this);
    }

    //weather

    public void getCurrentLocationWeather(String city) {
        weatherHelper.getCurrentWeatherByCityName(city, new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                hideProgressDialog();

                setTheCurrentLocationWeather(currentWeather.getWeather().get(0).getDescription(), (int) (currentWeather.getMain().getTempMax() - 272.15), (int) (currentWeather.getWind().getSpeed()));

                beginTransactionToAnotherFragment(new DeviceFragment(), getTheCurrentLocation(), true);
            }

            @Override
            public void onFailure(Throwable throwable) {
                hideProgressDialog();

                setTheCurrentLocationWeather("error", 0, 0);

                beginTransactionToAnotherFragment(new DeviceFragment(), getTheCurrentLocation(), true);
                Log.e(TAG_WEATHER, throwable.getMessage());
            }
        });
    }

    //navigation drawer

    private void setTheCurrentLocationWeather(String description, int degrees, int windSpeed) {
        Weather.setWeatherDescription(description);
        Weather.setWeatherCelsius(degrees);
        Weather.setWeatherWindSpeed(windSpeed);
    }

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
            cloudStorage.logOut();
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

    private void saveTheCurrentLocation(String locationName) {
        currentLocation = locationName;
        CURRENT_LOCATION_FOR_DATABASE = locationName;
    }

    private String getTheCurrentLocation() {
        return currentLocation;
    }

    private void saveTheCurrentDevice(Device device) {
        currentDevice = device;
        CURRENT_DEVICE_FOR_DATABASE = device.getName();
    }

    private void saveTheCurrentDeviceName(String deviceName) {
        CURRENT_DEVICE_FOR_DATABASE = deviceName;
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

    //locations

    @Override
    public void onBackPressedToLocationFragment() {
        beginTransactionToAnotherFragment(new HomeFragment(), "Locations", true);
    }

    @Override
    public void onDeleteLocationButtonPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.location_delete_question);
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // todo delete all deveice form this location localy and from cloud
                // todo delete the location localy and from cloud
                onBackPressedToLocationFragment();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }

    @Override
    public void onSubmitButtonPressedFromAddLocation(String name, String city, String street, int number) {
        cloudStorage.addLocationInDatabase(name, city, street, number);
        localStorage.addLocationInLocalDb(name, city, street, Integer.toString(number));

        onBackPressedToLocationFragment();
    }

    @Override
    public void onLocationSelected(String locationName, String city) {
        //todo load here the hole array of devices
        showProgressDialog();

        getCurrentLocationWeather(city);
        saveTheCurrentLocation(locationName);
    }

    //devices

    @Override
    public void onBackPressedToDeviceFragment() {
        beginTransactionToAnotherFragment(new DeviceFragment(), getTheCurrentLocation(), true);
    }

    @Override
    public void onSubmitButtonPressedFromAddDevice(String name, String description, String status, String type, String code) {
        cloudStorage.addDeviceInDatabase(name, getTheCurrentLocation() ,description, type, status, code);
        localStorage.addDeviceInLocalDb(name, getTheCurrentLocation(), description, status, type, code);

        onBackPressedToDeviceFragment();
    }

    @Override
    public void onSubmitButtonPressedFromUpdateDevice(String name) {
        cloudStorage.updateDevice(currentDevice, name);
        localStorage.updateDeviceName(currentDevice, name);

        onBackPressedToDeviceFragment();
    }

    @Override
    public void onDeviceSelected(String deviceName, String deviceType, String status) {
        saveTheCurrentDeviceName(deviceName);
        DEVICE_STATUS = status;

        if ("Relay".equals(deviceType)) {
            beginTransactionToAnotherFragment(new RelayFragment(), deviceName, false);
        } else {
            //if it is intensity type
            beginTransactionToAnotherFragment(new IntensityFragment(), deviceName, false);
        }
    }

    @Override
    public void onDeviceSelectedToEdit(Device device) {
        saveTheCurrentDevice(device);
        beginTransactionToAnotherFragment(new UpdateDevices(), CURRENT_DEVICE_FOR_DATABASE, false);
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
    public void onDeleteDeviceButtonPressed(Device device) {
        //the local is already deleted here
        cloudStorage.deleteDevice(device);
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