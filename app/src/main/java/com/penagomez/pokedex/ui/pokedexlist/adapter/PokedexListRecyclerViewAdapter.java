package com.penagomez.pokedex.ui.pokedexlist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.penagomez.pokedex.ui.MainActivity;
import com.penagomez.pokedex.data.dto.PokemonFavorite;
import com.penagomez.pokedex.databinding.PokemonCardviewBinding;

import java.util.List;

public class PokedexListRecyclerViewAdapter extends RecyclerView.Adapter<PokedexListViewHolder> {
    private final List<PokemonFavorite> pokemons;
    private final Context context;


    public PokedexListRecyclerViewAdapter(List<PokemonFavorite> pokemonNames, Context context) {
        this.pokemons = pokemonNames;
        this.context = context;
    }

    @NonNull
    @Override
    public PokedexListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PokemonCardviewBinding binding = PokemonCardviewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new PokedexListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PokedexListViewHolder holder, int position) {
        PokemonFavorite currentPokemon = this.pokemons.get(position);
        holder.bind(currentPokemon);

        holder.itemView.setOnClickListener(view -> itemClicked(currentPokemon, view));
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    private void itemClicked(PokemonFavorite currentPokemon, View view) {
        if (!currentPokemon.isFavorite()) {
            ((MainActivity) context).pokemonClicked(currentPokemon, view);
        }
    }
}



