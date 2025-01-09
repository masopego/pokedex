package com.penagomez.pokedex.ui.pokemondetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.penagomez.pokedex.databinding.PokemonDetailsFragmentBinding;

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
            int image = getArguments().getInt("image");
            String name = getArguments().getString("name");


            binding.pokemonImage.setImageResource(image);
            binding.pokemonName.setText(name);
        }
    }
}
