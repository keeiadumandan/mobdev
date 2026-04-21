package com.example.byahebuddy

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("ByaheBuddyPrefs", Context.MODE_PRIVATE)

    // SAVE data - called after login/register
    fun saveUserData(name: String, email: String) {
        val editor = prefs.edit()
        editor.putBoolean("IS_LOGGED_IN", true)
        editor.putString("USER_NAME", name)
        editor.putString("USER_EMAIL", email)
        editor.putLong("LOGIN_TIMESTAMP", System.currentTimeMillis())
        editor.apply()
    }

    // RETRIEVE data - check login status
    fun isLoggedIn(): Boolean = prefs.getBoolean("IS_LOGGED_IN", false)

    fun getUserName(): String = prefs.getString("USER_NAME", "") ?: ""
    fun getUserEmail(): String = prefs.getString("USER_EMAIL", "") ?: ""

    // UPDATE data - modify existing info
    fun updateUserName(newName: String) {
        prefs.edit().putString("USER_NAME", newName).apply()
    }

    fun updateUserEmail(newEmail: String) {
        prefs.edit().putString("USER_EMAIL", newEmail).apply()
    }

    // CLEAR data - logout
    fun clearUserData() {
        prefs.edit().clear().apply()
    }
}