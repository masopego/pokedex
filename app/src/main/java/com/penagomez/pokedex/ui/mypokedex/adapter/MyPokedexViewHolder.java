package com.penagomez.pokedex.ui.mypokedex.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.penagomez.pokedex.data.dto.Pokemon;
import com.penagomez.pokedex.databinding.FavouritePokemonCardviewBinding;


public class MyPokedexViewHolder extends RecyclerView.ViewHolder {

    private final FavouritePokemonCardviewBinding binding;

    public MyPokedexViewHolder(FavouritePokemonCardviewBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }


    public void bind(Pokemon pokemon){
        binding.name.setText(pokemon.getName());
        binding.executePendingBindings();
    }
}
