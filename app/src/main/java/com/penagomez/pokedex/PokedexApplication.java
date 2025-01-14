package com.penagomez.pokedex;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.penagomez.pokedex.ui.MainActivity;

import java.util.Locale;

public class PokedexApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        applySavedLanguage();

        if (!Locale.getDefault().getLanguage().equals(getSavedLanguage())) {
            recreateMainActivity();
        }

    }

    private void applySavedLanguage() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String language = prefs.getString("language", Locale.getDefault().getLanguage());
        LocaleHelper.updateLocale(this, language);
    }

    private String getSavedLanguage() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getString("language", Locale.getDefault().getLanguage());
    }

    private void recreateMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
