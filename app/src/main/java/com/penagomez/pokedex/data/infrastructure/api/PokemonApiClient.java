package com.penagomez.pokedex.data.infrastructure.api;

import com.penagomez.pokedex.data.infrastructure.api.responses.PokemonDetailResponse;
import com.penagomez.pokedex.data.infrastructure.api.responses.PokemonListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonApiClient {
    @GET("pokemon")
    Call<PokemonListResponse> getPokemonList(@Query("offset") int offset, @Query("limit") int limit);

    @GET("pokemon/{name}")
    Call<PokemonDetailResponse>getPokemonByName(@Path("name") String pokemonName);
}
