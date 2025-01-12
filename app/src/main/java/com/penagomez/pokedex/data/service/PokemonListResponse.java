package com.penagomez.pokedex.data.service;

import com.penagomez.pokedex.data.dto.PokemonFavorite;
import com.penagomez.pokedex.data.dto.PokemonName;

import java.util.List;

public class PokemonListResponse {
    private List<PokemonName> results;

    public List<PokemonName> getResults() {
        return results;
    }
}
