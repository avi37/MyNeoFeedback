package com.example.admin.myneofeedback.Common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.admin.myneofeedback.Views.LoginActivity;
import com.example.admin.myneofeedback.Views.MainNavigationActivity;
import com.example.admin.myneofeedback.Views.SplashMenuActivity;

public class SessionManager {

    SharedPreferences loginPref;
    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String LOGIN_PREF_NAME = "LoginPref";

    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_PASSWORD = "password";

    public SessionManager(Context context) {
        this._context = context;
        loginPref = _context.getSharedPreferences(LOGIN_PREF_NAME, PRIVATE_MODE);
        editor = loginPref.edit();
    }

    public void createLoginSession(String number, String password) {
        Toast.makeText(_context, "Logging you in...", Toast.LENGTH_SHORT).show();

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_NUMBER, number);
        editor.putString(KEY_PASSWORD, password);

        editor.commit();
    }

    public boolean checkLogin() {
        boolean status = false;

        if (this.isLoggedIn()) {
            status = true;
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainNavigationActivity.class);

            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            /*// Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/

            _context.startActivity(i);

        }
        return status;
    }

    public void logoutUser() {

        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, SplashMenuActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Toast.makeText(_context, "User Logged out", Toast.LENGTH_SHORT).show();

        _context.startActivity(i);
    }

    // Get Login State
    public boolean isLoggedIn() {
        return loginPref.getBoolean(IS_LOGIN, false);
    }

}
