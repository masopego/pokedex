package com.penagomez.pokedex.data.service;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.penagomez.pokedex.R;
import com.penagomez.pokedex.data.dto.Pokemon;
import com.penagomez.pokedex.data.dto.PokemonFavorite;
import com.penagomez.pokedex.data.dto.PokemonName;
import com.penagomez.pokedex.data.infrastructure.api.PokemonApiClient;
import com.penagomez.pokedex.data.infrastructure.api.mapper.PokemonMapper;
import com.penagomez.pokedex.data.infrastructure.api.responses.PokemonDetailResponse;
import com.penagomez.pokedex.data.infrastructure.api.responses.PokemonListResponse;
import com.penagomez.pokedex.data.infrastructure.api.ApiConfig;
import com.penagomez.pokedex.data.infrastructure.firebase.FirebaseDatabase;
import com.penagomez.pokedex.ui.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokedexService {

    private final PokemonApiClient pokemonService;

    public PokedexService() {
        pokemonService = ApiConfig.getRetrofitInstance().create(PokemonApiClient.class);
    }

    public CompletableFuture<List<PokemonFavorite>> getPokemonsWithFavorites(int offset, int limit) {
        CompletableFuture<List<PokemonName>> pokemonsFromAPI = getPokemonsFromAPI(offset, limit);
        CompletableFuture<List<String>> favoritePokemonIds = getFavoritePokemonsFromFirebase();

        return pokemonsFromAPI.thenCombine(favoritePokemonIds, (pokemonNames, favouritePokemonNames) -> {
            List<PokemonFavorite> pokemonsWithFavorites = new ArrayList<>();

            for (PokemonName pokemon : pokemonNames) {
                boolean isFavorite = favouritePokemonNames.contains(pokemon.getName());
                pokemonsWithFavorites.add(new PokemonFavorite(pokemon.getName(), isFavorite));
            }
            return pokemonsWithFavorites;
        });
    }

    private CompletableFuture<List<PokemonName>> getPokemonsFromAPI(int offset, int limit) {
        CompletableFuture<List<PokemonName>> future = new CompletableFuture<>();

        pokemonService.getPokemonList(offset, limit).enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    future.complete(response.body().getResults());
                } else {
                    future.completeExceptionally(new Exception("Error en la API"));
                }
            }

            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;
    }

    public CompletableFuture<Pokemon> getPokemonByName(String name) {
        CompletableFuture<Pokemon> future = new CompletableFuture<>();

        pokemonService.getPokemonByName(name).enqueue(
                new Callback<PokemonDetailResponse>() {
                    @Override
                    public void onResponse(Call<PokemonDetailResponse> call, Response<PokemonDetailResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Pokemon pokemon = PokemonMapper.fromResponse(response.body());
                            future.complete(pokemon);
                        }
                    }

                    @Override
                    public void onFailure(Call<PokemonDetailResponse> call, Throwable t) {
                        future.completeExceptionally(t);
                    }
                }
        );

        return future;
    }

    private CompletableFuture<List<String>> getFavoritePokemonsFromFirebase() {
        CompletableFuture<List<String>> future = new CompletableFuture<>();

        FirebaseDatabase repository = new FirebaseDatabase();
        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        repository.getFavouritePokemons(
                userEmail,
                pokemons -> {
                    List<String> pokemonNames = new ArrayList<>();
                    for (Pokemon pokemon : pokemons) {
                        String pokemonName = pokemon.getName();
                        pokemonNames.add(pokemonName);
                    }
                    future.complete(pokemonNames);
                },
                error -> {
                    future.completeExceptionally(new Exception("Error al cargar favoritos desde Firebase"));
                }
        );

        return future;
    }


}