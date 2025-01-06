package com.penagomez.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        Preference logoutPreference = findPreference("logout");

        if (logoutPreference != null) {
            logoutPreference.setOnPreferenceClickListener(this::handleLogout);
        }
    }

    public boolean handleLogout(Preference preference) {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(requireContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Borra el stack
        startActivity(intent);

        Toast.makeText(requireContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();

        return true;
    }

}