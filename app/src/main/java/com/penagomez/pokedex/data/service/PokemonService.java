package com.penagomez.pokedex.data.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonService {
    @GET("pokemon")
    Call<PokemonResponse> getPokemonList(@Query("offset") int offset, @Query("limit") int limit);
}
