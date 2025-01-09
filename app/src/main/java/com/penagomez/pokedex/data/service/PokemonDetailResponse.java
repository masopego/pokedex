package com.penagomez.pokedex.data.service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonDetailResponse {
    private final String name;
    private final int weight;
    private final int height;
    private final int id;

    @SerializedName("types")
    private List<TypeWrapper> types;

    @SerializedName("sprites")
    private Sprites sprites;


    public PokemonDetailResponse(String name, int weight, int height, int id) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

    public List<TypeWrapper> getTypes() {
        return types;
    }

    public Sprites getSprites() {
        return sprites;
    }


    public static class TypeWrapper {
        @SerializedName("type")
        private Type type;

        public Type getType() {
            return type;
        }

        public static class Type {
            private String name;

            public String getTypeName() {
                return name;
            }
        }
    }

    public static class Sprites {
        @SerializedName("front_default")
        private String frontDefault;

        public String getFrontDefault() {
            return frontDefault;
        }
    }
}
