package com.penagomez.pokedex.data.service;

import com.penagomez.pokedex.data.dto.Pokemon;

import java.util.List;

public class PokemonResponse {
    private List<Pokemon> results;

    public List<Pokemon> getResults() {
        return results;
    }
}
