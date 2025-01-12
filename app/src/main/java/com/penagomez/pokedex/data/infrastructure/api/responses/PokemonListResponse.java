package com.penagomez.pokedex.data.infrastructure.api.responses;

import com.penagomez.pokedex.data.dto.PokemonName;

import java.util.List;

public class PokemonListResponse {
    private List<PokemonName> results;
    public List<PokemonName> getResults() {
        return results;
    }
}
