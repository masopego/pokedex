package com.penagomez.pokedex.ui.pokedexlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.penagomez.pokedex.MainActivity;
import com.penagomez.pokedex.data.dto.Pokemon;
import com.penagomez.pokedex.databinding.PokemonCardviewBinding;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PokedexListRecyclerViewAdapter extends RecyclerView.Adapter<PokedexListViewHolder> {
    private final List<Pokemon> pokemons;
    private final Context context;
    private Set<String> favouritedPokemons;


    public PokedexListRecyclerViewAdapter(List<Pokemon> pokemons, Context context) {
        this.pokemons = pokemons;
        this.context = context;
        this.favouritedPokemons = new HashSet<>();
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
        Pokemon currentPokemon = this.pokemons.get(position);
        holder.bind(currentPokemon);

        holder.itemView.setOnClickListener(view -> itemClicked(currentPokemon, view));
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    private void itemClicked(Pokemon currentPokemon, View view) {
        ((MainActivity) context).pokemonClicked(currentPokemon, view);
    }

    public void setFavouritedPokemon(Set<String> favouritedPokemons) {
        this.favouritedPokemons = favouritedPokemons;
        notifyDataSetChanged();
    }

}



