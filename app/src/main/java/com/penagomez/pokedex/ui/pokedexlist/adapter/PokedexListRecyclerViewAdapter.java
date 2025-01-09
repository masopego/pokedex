package com.penagomez.pokedex.ui.pokedexlist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.penagomez.pokedex.MainActivity;
import com.penagomez.pokedex.data.dto.PokemonName;
import com.penagomez.pokedex.databinding.PokemonCardviewBinding;

import java.util.List;

public class PokedexListRecyclerViewAdapter extends RecyclerView.Adapter<PokedexListViewHolder> {
    private final List<PokemonName> pokemonNames;
    private final Context context;


    public PokedexListRecyclerViewAdapter(List<PokemonName> pokemonNames, Context context) {
        this.pokemonNames = pokemonNames;
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
        PokemonName currentPokemonName = this.pokemonNames.get(position);
        holder.bind(currentPokemonName);

        holder.itemView.setOnClickListener(view -> itemClicked(currentPokemonName, view));
    }

    @Override
    public int getItemCount() {
        return pokemonNames.size();
    }

    private void itemClicked(PokemonName currentPokemonName, View view) {
        ((MainActivity) context).pokemonClicked(currentPokemonName, view);
    }


}



