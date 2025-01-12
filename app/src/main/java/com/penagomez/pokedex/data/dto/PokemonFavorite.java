package com.penagomez.pokedex.data.dto;

public class PokemonFavorite {
    private String name;
    private boolean isFavorite = false;

    public PokemonFavorite(String name) {
        this.name = name;
        this.isFavorite = false;
    }

    public PokemonFavorite(String name, boolean isFavorite) {
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


