package com.penagomez.pokedex;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.penagomez.pokedex.ui.utils.LocaleHelper;

import java.util.Locale;

public class PokedexApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        applySavedLanguage();
    }

    private void applySavedLanguage() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String language = prefs.getString("language", Locale.getDefault().getLanguage());
        LocaleHelper.updateLocale(this, language);
    }
}
