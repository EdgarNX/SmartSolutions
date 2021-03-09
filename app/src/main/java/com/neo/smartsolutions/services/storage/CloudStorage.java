package com.neo.smartsolutions.services.storage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.neo.smartsolutions.R;
import com.neo.smartsolutions.WelcomeActivity;
import com.neo.smartsolutions.devices.device_local_db.Device;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CloudStorage {

    public static final String TAG_STORAGE = "STORE";

    FirebaseFirestore store;
    FirebaseAuth auth;
    Context context;
    LocalStorage localStorage;

    public CloudStorage(LocalStorage localStorage, FirebaseAuth auth, FirebaseFirestore store, Context context) {
        this.localStorage = localStorage;
        this.auth = auth;
        this.store = store;
        this.context = context;
    }

    public void logOut() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage(R.string.logout_question);
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                auth.signOut();
                localStorage.deleteAllLocation();
                localStorage.deleteAllDevices();
                onLogOut();
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

    private void onLogOut() {
        Intent intentToWelcomeActivity = new Intent(context, WelcomeActivity.class);
        context.startActivity(intentToWelcomeActivity);
    }

    public void addLocationInDatabase(String name, String city, String street, int number) {
        String userID = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        Map<String, Object> location = new HashMap<>();
        location.put("name", name);
        location.put("city", city);
        location.put("street", street);
        location.put("number", number);

        store.collection("users")
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

    public void addDeviceInDatabase(String name, String location, String description, String type, String status, String code) {
        String userID = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        Map<String, Object> device = new HashMap<>();
        device.put("name", name);
        device.put("location", location);
        device.put("description", description);
        device.put("type", type);
        device.put("status", status);
        device.put("code", code);

        store.collection("users")
                .document(userID)
                .collection("locations")
                .document(location)
                .collection("devices")
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

    public void addUserInDatabase(String country) {
        String userID = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        Map<String, Object> user = new HashMap<>();
        user.put("uid", userID);
        user.put("country", country);

        store.collection("users")
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

    public void downloadLocationsAndStoreLocally() {
        String userID = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        CollectionReference docRef = store.collection("users").document(userID).collection("locations");
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();
                    if (!document.isEmpty()) {
                        document.getDocuments().forEach((temp) -> {
                            localStorage.addLocationInLocalDb(Objects.requireNonNull(temp.get("name")).toString(), Objects.requireNonNull(temp.get("city")).toString(), Objects.requireNonNull(temp.get("street")).toString(), Objects.requireNonNull(temp.get("number")).toString());
                            downloadDevicesAndStoreLocally(Objects.requireNonNull(temp.get("name")).toString());
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
        String userID = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        CollectionReference docRef = store.collection("users").document(userID).collection("locations").document(name).collection("devices");
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();
                    if (!document.isEmpty()) {
                        document.getDocuments().forEach((temp) -> {
                            localStorage.addDeviceInLocalDb(Objects.requireNonNull(temp.get("name")).toString(), Objects.requireNonNull(temp.get("location")).toString(), Objects.requireNonNull(temp.get("description")).toString(), Objects.requireNonNull(temp.get("type")).toString(), Objects.requireNonNull(temp.get("status")).toString(), Objects.requireNonNull(temp.get("code")).toString());
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

    public void deleteDevice(Device device) {
        String userID = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        DocumentReference docRef = store.collection("users")
                .document(userID)
                .collection("locations")
                .document(device.getLocation())
                .collection("devices")
                .document(device.getName());


        docRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Log.d(TAG_STORAGE, "Deleted");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG_STORAGE, "get failed with ", e);
                    }
                });

    }
}

