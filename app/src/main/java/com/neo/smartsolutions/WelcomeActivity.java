package com.neo.smartsolutions;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.neo.smartsolutions.devices.device_local_db.Device;
import com.neo.smartsolutions.locations.location_local_db.Location;
import com.neo.smartsolutions.locations.location_local_db.LocationViewModel;
import com.neo.smartsolutions.welcome.LogInFragment;
import com.neo.smartsolutions.welcome.OnPressedListener;
import com.neo.smartsolutions.welcome.SingUpFragment;
import com.neo.smartsolutions.welcome.WelcomeFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WelcomeActivity extends MainActivity implements OnPressedListener {

    public static final int SIGN_UP_MODE_CODE = 0;
    public static final int LOG_IN_MODE_CODE = 1;

    List<String> nameList = new ArrayList<>();

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
    }

    //firebase

    private void signUp(String email, String password, String country) {
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    hideProgressDialog();
                    Toast.makeText(WelcomeActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    addUserInDatabase(country);
                }
            }
        });
    }

    private void logIn(String email, String password) {
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG_AUTH, "logInWithEmail:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
                    hideProgressDialog();
                    Toast.makeText(WelcomeActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    downloadLocationsAndStoreLocally();
                    //nameList.add(temp.get("name").toString());
                    //downloadDevicesAndStoreLocally(temp.get("name").toString());
                }
            }
        });
    }

    private void addUserInDatabase(String country) {
        String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        Map<String, Object> user = new HashMap<>();
        user.put("uid", userID);
        user.put("country", country);

        fStore.collection("users")
                .document(userID)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG_STORAGE, "Added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG_STORAGE, "Error adding document", e);
                    }
                });
    }

    private void downloadLocationsAndStoreLocally() {
        String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        CollectionReference docRef = fStore.collection("users").document(userID).collection("locations");
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();
                    if (!document.isEmpty()) {
                        document.getDocuments().forEach((temp) -> {
                            addLocationInLocalDb(Objects.requireNonNull(temp.get("number")).toString(), Objects.requireNonNull(temp.get("city")).toString(), Objects.requireNonNull(temp.get("street")).toString(), Objects.requireNonNull(temp.get("name")).toString());
                            downloadDevicesAndStoreLocally(Objects.requireNonNull(temp.get("name")).toString());
                            // nameList.add(temp.get("name").toString());
                        });
                        Log.e(TAG_STORAGE, "DocumentSnapshot data: " + document.toString());
                    } else {
                        Log.e(TAG_STORAGE, "No such document");
                    }
                } else {
                    Log.e(TAG_STORAGE, "get failed with ", task.getException());
                }
            }
        });
    }

    private void downloadDevicesAndStoreLocally(String name) {
        String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        CollectionReference docRef = fStore.collection("users").document(userID).collection("locations").document(name).collection("devices");
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();
                    if (!document.isEmpty()) {
                        document.getDocuments().forEach((temp) -> {
                            addDeviceInLocalDb(Objects.requireNonNull(temp.get("code")).toString(), Objects.requireNonNull(temp.get("location")).toString(), Objects.requireNonNull(temp.get("description")).toString(), Objects.requireNonNull(temp.get("name")).toString(), Objects.requireNonNull(temp.get("status")).toString(), Objects.requireNonNull(temp.get("type")).toString());
                        });
                        Log.e(TAG_STORAGE, "DocumentSnapshot data: " + document.toString());
                    } else {
                        Log.e(TAG_STORAGE, "No such document");
                    }
                } else {
                    Log.e(TAG_STORAGE, "get failed with ", task.getException());
                }
            }
        });
    }

    void addLocationInLocalDb(String number, String city, String street, String name) {
        Location location = new Location(name, city, street, number);
        mLocationViewModel.insert(location);
    }

    private void addDeviceInLocalDb(String code, String location, String description, String name, String status, String type) {
        Device device = new Device(name, location, description, type, status, code);
        mDeviceViewModel.insert(device);
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
        Intent intentToHomeActivity = new Intent(WelcomeActivity.this, HomeActivity.class);
        startActivity(intentToHomeActivity);
    }

    @Override
    public void onSignUpButtonPressed(String email, String password, String country) {
        showProgressDialog();
        signUp(email, password, country);
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
        logIn(email, password);
    }

    @Override
    public void onGoogleLogInButtonPressed(String idToken) {
        //todo Google Log In
    }

    @Override
    public void onFacebookLogInButtonPressed() {
        //todo Facebook Log In
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