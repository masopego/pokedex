package com.penagomez.pokedex.data.service;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonService {
    @GET("pokemon")
    Call<PokemonListResponse> getPokemonList(@Query("offset") int offset, @Query("limit") int limit);

    @GET("pokemon/{name}")
    Call<PokemonDetailResponse>getPokemonByName(@Path("name") String pokemonName);
}
