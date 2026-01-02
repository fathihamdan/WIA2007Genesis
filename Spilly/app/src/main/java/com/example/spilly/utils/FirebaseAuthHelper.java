package com.example.spilly.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthHelper {
    private static FirebaseAuthHelper instance;
    private FirebaseAuth mAuth;
    private Context context;

    private FirebaseAuthHelper(Context context) {
        this.context = context.getApplicationContext();
        mAuth = FirebaseAuth.getInstance();
    }

    public static synchronized FirebaseAuthHelper getInstance(Context context) {
        if (instance == null) {
            instance = new FirebaseAuthHelper(context);
        }
        return instance;
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public String getCurrentUserId() {
        FirebaseUser user = getCurrentUser();
        return user != null ? user.getUid() : null;
    }

    public boolean isLoggedIn() {
        return getCurrentUser() != null;
    }

    public void logout() {
        mAuth.signOut();
        clearUserPreferences();
    }

    // Save user info to SharedPreferences
    public void saveUserInfo(String userId, String userType, String name, String email) {
        SharedPreferences prefs = context.getSharedPreferences("SpillyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.PREF_USER_ID, userId);
        editor.putString(Constants.PREF_USER_TYPE, userType);
        editor.putString(Constants.PREF_USER_NAME, name);
        editor.putString(Constants.PREF_USER_EMAIL, email);
        editor.apply();
    }

    // Get user info from SharedPreferences
    public String getUserType() {
        SharedPreferences prefs = context.getSharedPreferences("SpillyPrefs", Context.MODE_PRIVATE);
        return prefs.getString(Constants.PREF_USER_TYPE, "");
    }

    public String getUserName() {
        SharedPreferences prefs = context.getSharedPreferences("SpillyPrefs", Context.MODE_PRIVATE);
        return prefs.getString(Constants.PREF_USER_NAME, "");
    }

    private void clearUserPreferences() {
        SharedPreferences prefs = context.getSharedPreferences("SpillyPrefs", Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }
}