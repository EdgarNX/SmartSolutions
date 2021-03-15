package com.neo.smartsolutions.welcome;

public interface OnPressedListener {
    void onBackPressed();
    void onSignUpButtonPressed(String email, String password, String country);
    void onGoogleSignUpButtonPressed(String idToken);
    void onFacebookSignUpButtonPressed();

    void onLogInButtonPressed(String email, String password);
    void onGoogleLogInButtonPressed(String idToken);
    void onFacebookLogInButtonPressed();

    void onModeItemSelected(int mode);
}
