package com.penagomez.pokedex;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.preference.PreferenceManager;

import com.penagomez.pokedex.data.dto.mapper.PokemonTypeTranslations;
import com.penagomez.pokedex.data.infrastructure.api.mapper.PokemonTypeMapper;
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

        PokemonTypeMapper.initialize(PokemonTypeTranslations.getTranslations(this));
    }

    private void applySavedLanguage() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String language = prefs.getString("language", Locale.getDefault().getLanguage());
        setLocale(language);
    }

    private void setLocale(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);

        resources.updateConfiguration(config, resources.getDisplayMetrics());
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
