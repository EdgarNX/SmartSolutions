package com.neo.smartsolutions.welcome;

public interface OnPressedListener {
    void onBackPressed();
    void onSignUpButtonPressed(String email, String password, String country);
    void onLogInButtonPressed(String email, String password);
    void onModeItemSelected(int mode);
}
