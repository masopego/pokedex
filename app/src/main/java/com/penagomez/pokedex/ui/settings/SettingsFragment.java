package com.penagomez.pokedex.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.penagomez.pokedex.ui.utils.LocaleHelper;
import com.penagomez.pokedex.R;
import com.penagomez.pokedex.ui.sign.LoginActivity;

import androidx.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        Preference removeFavouritesPreference = findPreference("remove_favourites");
        Preference aboutUsPreference = findPreference("about_us");
        Preference logoutPreference = findPreference("logout");
        ListPreference languagePreference = findPreference("language");

        if(removeFavouritesPreference != null){
            removeFavouritesPreference.setOnPreferenceChangeListener(this::handleRemoveFavourites);
        }
        if(aboutUsPreference != null){
            aboutUsPreference.setOnPreferenceClickListener(this::handleAboutUs);
        }
        if (languagePreference != null) {
            setupLanguagePreferenceListener(languagePreference);
        }
        if (logoutPreference != null) {
            logoutPreference.setOnPreferenceClickListener(this::handleLogout);
        }
    }


    private boolean handleLogout(Preference preference) {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.logout_title)
                .setMessage(R.string.logout_message)
                .setPositiveButton(R.string.logout_confirm_button, (dialog, which) -> {
                    FirebaseAuth.getInstance().signOut();

                    Intent intent = new Intent(requireContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    Toast.makeText(requireContext(), R.string.logout_done, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.logout_not_button, (dialog, which) -> dialog.dismiss())
                .show();

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

    private void setupLanguagePreferenceListener(ListPreference languagePreference) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String savedLanguage = prefs.getString("language", "es");

        if (!savedLanguage.equals(languagePreference.getValue())) {
            languagePreference.setValue(savedLanguage);
        }

        languagePreference.setOnPreferenceChangeListener((preference, newValue) -> {
            String selectedLanguage = (String) newValue;

            updateLocale(selectedLanguage);

            return true;
        });
    }

    private void updateLocale(String languageCode) {
        Context updatedContext = LocaleHelper.updateLocale(requireContext(), languageCode);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(updatedContext);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("language", languageCode);
        editor.apply();

        requireActivity().recreate();
    }

}