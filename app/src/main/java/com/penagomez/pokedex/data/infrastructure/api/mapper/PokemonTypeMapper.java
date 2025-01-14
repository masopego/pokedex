package com.penagomez.pokedex.data.infrastructure.api.mapper;

import java.util.HashMap;
import java.util.Map;

public class PokemonTypeMapper {
    private static final Map<String, String> typeMap = new HashMap<>();

    public static void initialize(Map<String, String> translations) {
        typeMap.putAll(translations);
    }

    public static String getTypeName(String typeName) {
        return typeMap.getOrDefault(typeName.toLowerCase(), typeName);
    }
}
