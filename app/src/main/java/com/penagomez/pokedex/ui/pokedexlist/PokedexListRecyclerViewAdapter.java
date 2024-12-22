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

import java.util.List;

public class PokedexListRecyclerViewAdapter extends RecyclerView.Adapter<PokedexListViewHolder> {
    private final List<Pokemon> pokemons;
    private final Context context;


    public PokedexListRecyclerViewAdapter(List<Pokemon> pokemons, Context context) {
        this.pokemons = pokemons;
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
        // Get the current pokemon and bind data to the ViewHolder
        Pokemon currentPokemon = this.pokemons.get(position);
        holder.bind(currentPokemon);

        // Set click listener to trigger navigation or other actions
        holder.itemView.setOnClickListener(view -> itemClicked(currentPokemon, view));
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    private void itemClicked(Pokemon currentPokemon, View view) {
        ((MainActivity) context).pokemonClicked(currentPokemon, view);
    }

}



