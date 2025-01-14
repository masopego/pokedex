package com.penagomez.pokedex.ui.utils;

import android.content.Context;

import java.util.List;

public class PokemonTypeUtils {

    public StringBuilder getTypeName(Context context, List<String> types){
        StringBuilder typesText = new StringBuilder();

        for (String type : types) {
            String resourceName = "type_" + type;
            int resourceId = context.getResources().getIdentifier(resourceName, "string", context.getPackageName());
            if (resourceId != 0) {
                if (typesText.length() > 0) {
                    typesText.append(", ");
                }
                typesText.append(context.getString(resourceId));
            }
        }

        return typesText;
    }
}
