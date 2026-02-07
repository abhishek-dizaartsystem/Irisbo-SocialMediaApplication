package com.example.sociamediaapplication.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * TokenManager is responsible for storing and retrieving
 * the JWT authentication token locally on the device.
 *
 * Why?
 * After login, the backend sends a JWT token.
 * We must store it so the user stays logged in
 * even after closing and reopening the app.
 */
class TokenManager(context: Context) {

    /**
     * Creates (or accesses) a SharedPreferences file named "auth_prefs".
     *
     * MODE_PRIVATE means:
     * This file can only be accessed by this app.
     *
     * Why SharedPreferences?
     * Because JWT is small data (a simple string),
     * and SharedPreferences is lightweight and perfect for key-value storage.
     */
    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    /**
     * Saves the JWT token into SharedPreferences.
     *
     * Why?
     * So that we can:
     * - Attach it in Authorization header for future API calls
     * - Keep user logged in after app restart
     */
    fun saveToken(token: String) {
        prefs.edit { putString("jwt_token", token) }
    }

    /**
     * Retrieves the stored JWT token.
     *
     * Returns:
     * - Token string if exists
     * - null if user is not logged in
     *
     * Why?
     * Used when:
     * - Making authenticated API calls
     * - Checking if user session exists on app launch
     */
    fun getToken(): String? {
        return prefs.getString("jwt_token", null)
    }

    /**
     * Clears all stored authentication data.
     *
     * Why?
     * Used during logout.
     * Removes token so user becomes unauthenticated.
     */
    fun clearToken() {
        prefs.edit { clear() }
    }
}
