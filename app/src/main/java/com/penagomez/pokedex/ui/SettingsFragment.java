package com.penagomez.pokedex.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.penagomez.pokedex.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        Preference removeFavouritesPreference = findPreference("remove_favourites");
        Preference aboutUsPreference = findPreference("about_us");
        Preference logoutPreference = findPreference("logout");

        if(removeFavouritesPreference != null){
            removeFavouritesPreference.setOnPreferenceChangeListener(this::handleRemoveFavourites);
        }
        if(aboutUsPreference != null){
            aboutUsPreference.setOnPreferenceClickListener(this::handleAboutUs);
        }
        if (logoutPreference != null) {
            logoutPreference.setOnPreferenceClickListener(this::handleLogout);
        }
    }

    private boolean handleLogout(Preference preference) {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(requireContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Borra el stack
        startActivity(intent);

        Toast.makeText(requireContext(), R.string.logout_done, Toast.LENGTH_SHORT).show();

        return true;
    }

    private boolean handleAboutUs(Preference preference){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setIcon(R.mipmap.ic_launcher)
                .setTitle(requireContext().getString(R.string.about_us_title))
                .setMessage(requireContext().getString(R.string.about_us_message))
                .setPositiveButton(requireContext().getString(R.string.about_us_button), (dialog, which) -> dialog.dismiss());
        builder.create().show();
        return true;
    }

    private boolean handleRemoveFavourites(Preference preference, Object newValue) {
        boolean isEnabled = (Boolean) newValue;

        Toast.makeText(requireContext(),
                isEnabled ? R.string.remove_favourites_enabled : R.string.remove_favourites_disabled,
                Toast.LENGTH_SHORT).show();

        return true;
    }

}