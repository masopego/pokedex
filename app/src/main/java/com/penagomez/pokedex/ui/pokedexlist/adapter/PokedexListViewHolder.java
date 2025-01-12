package com.penagomez.pokedex.ui.pokedexlist.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.penagomez.pokedex.data.dto.PokemonFavorite;
import com.penagomez.pokedex.databinding.PokemonCardviewBinding;

public class PokedexListViewHolder extends RecyclerView.ViewHolder {
    private final PokemonCardviewBinding binding;


    public PokedexListViewHolder(PokemonCardviewBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }


    public void bind(PokemonFavorite pokemon){
        binding.setPokemon(pokemon);
        binding.executePendingBindings();
    }
}
