package com.penagomez.pokedex.data.dto.mapper;

import android.content.Context;

import com.penagomez.pokedex.R;

import java.util.Map;
import static java.util.Map.entry;

public class PokemonTypeTranslations {
    public static Map<String, String> getTranslations(Context context) {
        return Map.ofEntries(
                entry("normal", context.getString(R.string.type_normal)),
                entry("fighting", context.getString(R.string.type_fighting)),
                entry("flying", context.getString(R.string.type_flying)),
                entry("poison", context.getString(R.string.type_poison)),
                entry("ground", context.getString(R.string.type_ground)),
                entry("rock", context.getString(R.string.type_rock)),
                entry("bug", context.getString(R.string.type_bug)),
                entry("ghost", context.getString(R.string.type_ghost)),
                entry("steel", context.getString(R.string.type_steel)),
                entry("fire", context.getString(R.string.type_fire)),
                entry("water", context.getString(R.string.type_water)),
                entry("grass", context.getString(R.string.type_grass)),
                entry("electric", context.getString(R.string.type_electric)),
                entry("psychic", context.getString(R.string.type_psychic)),
                entry("ice", context.getString(R.string.type_ice)),
                entry("dragon", context.getString(R.string.type_dragon)),
                entry("dark", context.getString(R.string.type_dark)),
                entry("fairy", context.getString(R.string.type_fairy))
        );
    }
}
