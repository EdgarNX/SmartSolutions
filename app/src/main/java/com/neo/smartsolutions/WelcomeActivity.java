package com.neo.smartsolutions;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.neo.smartsolutions.welcome.LogInFragment;
import com.neo.smartsolutions.welcome.OnPressedListener;
import com.neo.smartsolutions.welcome.SingUpFragment;
import com.neo.smartsolutions.welcome.WelcomeFragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WelcomeActivity extends MainActivity implements OnPressedListener {

    public static final String EMAIL_MESSAGE_KEY = "email";
    public static final String UID_MESSAGE_KEY = "uid";
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

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
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
        FirebaseUser currentUser = fAuth.getCurrentUser();

        Intent intentToHomeActivity = new Intent(WelcomeActivity.this, HomeActivity.class);
        intentToHomeActivity.putExtra(EMAIL_MESSAGE_KEY, currentUser.getEmail());
        intentToHomeActivity.putExtra(UID_MESSAGE_KEY, currentUser.getUid());
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

//from here

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