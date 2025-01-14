package com.penagomez.pokedex.ui.mypokedex.adapter;

import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import com.penagomez.pokedex.R;
import com.penagomez.pokedex.data.dto.Pokemon;
import com.penagomez.pokedex.databinding.FavouritePokemonCardviewBinding;
import com.penagomez.pokedex.ui.utils.PokemonTypeUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MyPokedexViewHolder extends RecyclerView.ViewHolder {

    private final FavouritePokemonCardviewBinding binding;
    ImageButton deleteButton;

    public MyPokedexViewHolder(FavouritePokemonCardviewBinding binding){
        super(binding.getRoot());
        this.binding = binding;
        deleteButton = itemView.findViewById(R.id.btnDelete);
    }


    public void bind(Pokemon pokemon){
        boolean canRemoveFavourites = androidx.preference.PreferenceManager
                .getDefaultSharedPreferences(itemView.getContext())
                .getBoolean("remove_favourites", false);


        List<String> types = pokemon.getTypes();
        PokemonTypeUtils typeUtils = new PokemonTypeUtils();
        StringBuilder typeNames = typeUtils.getTypeName(itemView.getContext(), types);


        Picasso.get().load(pokemon.getImage()).into(binding.image);
        binding.name.setText(pokemon.getName());
        binding.type.setText(typeNames.toString());
        binding.setIsRemoveFavouritesEnabled(canRemoveFavourites);
        binding.executePendingBindings();
    }
}
