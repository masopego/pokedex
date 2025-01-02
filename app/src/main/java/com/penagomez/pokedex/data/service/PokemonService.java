package com.penagomez.pokedex.data.service;

import com.penagomez.pokedex.data.dto.Pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonService {
    @GET("pokemon")
    Call<PokemonResponse> getPokemonList(@Query("offset") int offset, @Query("limit") int limit);

    @GET("pokemon/{name}")
    Call<Pokemon>getPokemonByName(@Path("name") String pokemonName);
}
