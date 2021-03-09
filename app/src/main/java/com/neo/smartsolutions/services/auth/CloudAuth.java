package com.neo.smartsolutions.services.auth;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.neo.smartsolutions.WelcomeActivity;
import com.neo.smartsolutions.services.storage.CloudStorage;
import com.neo.smartsolutions.services.storage.LocalStorage;

import java.util.Objects;

public class CloudAuth {

    public static final String TAG_AUTH = "AUTH";

    FirebaseAuth auth;
    Context context;
    CloudStorage cloudStorage;
    LocalStorage localStorage;
    private final WelcomeActivity welcomeActivity;

    public CloudAuth(WelcomeActivity welcomeActivity, LocalStorage localStorage, FirebaseAuth auth, CloudStorage cloudStorage, Context context) {
        this.welcomeActivity = welcomeActivity;
        this.localStorage = localStorage;
        this.auth = auth;
        this.context = context;
        this.cloudStorage = cloudStorage;
    }

    public void signUp(String email, String password, String country) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(welcomeActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    welcomeActivity.hideProgressDialog();
                    Toast.makeText(context, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    cloudStorage.addUserInDatabase(country);
                }
            }
        });
    }

    public void logIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(welcomeActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG_AUTH, "logInWithEmail:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
                    welcomeActivity.hideProgressDialog();
                    Toast.makeText(welcomeActivity, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    cloudStorage.downloadLocationsAndStoreLocally();
                }
            }
        });
    }

}
