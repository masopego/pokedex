package com.penagomez.pokedex.data.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.penagomez.pokedex.data.dto.Pokemon;
import com.penagomez.pokedex.data.dto.PokemonFavorite;
import com.penagomez.pokedex.data.dto.PokemonName;
import com.penagomez.pokedex.data.repository.APIClient;
import com.penagomez.pokedex.data.repository.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokedexService {

    private final PokemonService pokemonService; // Servicio para la API

    public PokedexService() {
        pokemonService = APIClient.getRetrofitInstance().create(PokemonService.class);
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