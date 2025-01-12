package com.penagomez.pokedex.data.dto;

public class PokemonName {
    private String name;
    private boolean isFavorite = false;

    // Constructor, getters y setters

    public PokemonName(String name) {
        this.name = name;
        this.isFavorite = false;
    }

    public PokemonName(String name, boolean isFavorite) {
        this.name = name;
        this.isFavorite = isFavorite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}


