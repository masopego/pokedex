package com.penagomez.pokedex.ui.pokedexlist;

import androidx.recyclerview.widget.RecyclerView;

import com.penagomez.pokedex.data.dto.PokemonName;
import com.penagomez.pokedex.databinding.PokemonCardviewBinding;

public class PokedexListViewHolder extends RecyclerView.ViewHolder {
    private final PokemonCardviewBinding binding;


    public PokedexListViewHolder(PokemonCardviewBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }


    public void bind(PokemonName pokemonName){
        binding.name.setText(pokemonName.getName());
        binding.executePendingBindings();
    }
}
