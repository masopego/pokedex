package com.penagomez.pokedex.ui.mypokedex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.penagomez.pokedex.ui.MainActivity;
import com.penagomez.pokedex.data.dto.Pokemon;
import com.penagomez.pokedex.databinding.FavouritePokemonCardviewBinding;
import com.penagomez.pokedex.ui.mypokedex.listener.OnPokemonActionListener;

import java.util.List;

public class MyPokedexReciclerViewAdapter extends RecyclerView.Adapter<MyPokedexViewHolder> {

    private final List<Pokemon> pokemons;
    private final Context context;
    private final OnPokemonActionListener listener;


    public MyPokedexReciclerViewAdapter(List<Pokemon> pokemons, Context context, OnPokemonActionListener listener) {
        this.pokemons = pokemons;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyPokedexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FavouritePokemonCardviewBinding binding = FavouritePokemonCardviewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new MyPokedexViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPokedexViewHolder holder, int position) {
        Pokemon currentPokemon = this.pokemons.get(position);
        holder.bind(currentPokemon);

        holder.deleteButton.setOnClickListener(v -> itemDeleteClicked(currentPokemon));
        holder.itemView.setOnClickListener(view -> itemClicked(currentPokemon, view));
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    private void itemClicked(Pokemon currentPokemon, View view) {
        ((MainActivity) context).pokemonFavouriteClicked(currentPokemon, view);
    }

    private void itemDeleteClicked(Pokemon currentPokemon){
        if (listener != null) {
            listener.onDeleteClick(currentPokemon);
        }
    }

}
