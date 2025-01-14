package com.penagomez.pokedex.ui.pokemondetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.penagomez.pokedex.databinding.PokemonDetailsFragmentBinding;
import com.penagomez.pokedex.ui.utils.PokemonTypeUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PokemonDetailsFragment extends Fragment {

    private PokemonDetailsFragmentBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        binding = PokemonDetailsFragmentBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            String image = getArguments().getString("image");
            String name = getArguments().getString("name");
            String weight = getArguments().getString("weight");
            String height = getArguments().getString("height");
            String id = getArguments().getString("id");
            List<String> types = getArguments().getStringArrayList("types");

            PokemonTypeUtils typeUtils = new PokemonTypeUtils();
            StringBuilder typeNames = typeUtils.getTypeName(getContext(), types);



            Picasso.get().load(image).into(binding.pokemonImage);
            binding.pokemonName.setText(name);
            binding.pokemonWeight.setText(weight);
            binding.pokemonHeight.setText(height);
            binding.pokemonId.setText(id);
            binding.pokemonTypes.setText(typeNames.toString());

        }
    }
}
