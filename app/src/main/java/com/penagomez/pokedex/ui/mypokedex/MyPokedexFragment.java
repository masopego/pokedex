package com.penagomez.pokedex.ui.mypokedex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.penagomez.pokedex.R;
import com.penagomez.pokedex.data.dto.Pokemon;
import com.penagomez.pokedex.data.repository.FirebaseDatabase;
import com.penagomez.pokedex.databinding.MyPokedexFragmentBinding;
import com.penagomez.pokedex.ui.mypokedex.adapter.MyPokedexReciclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyPokedexFragment extends Fragment {

    private MyPokedexFragmentBinding binding;
    private List<Pokemon> favouritePokemons = new ArrayList<>();
    private MyPokedexReciclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MyPokedexFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        loadFavouritesPokemons();

        adapter = new MyPokedexReciclerViewAdapter(favouritePokemons, getActivity());
        binding.pokemonFavouriteRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.pokemonFavouriteRecyclerview.setAdapter(adapter);

    }


    private void loadFavouritesPokemons() {
        FirebaseDatabase repository = new FirebaseDatabase();
        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        repository.getFavouritePokemons(
                userEmail,
                pokemons -> {
                    favouritePokemons.clear();
                    favouritePokemons.addAll(pokemons);
                    adapter.notifyDataSetChanged();
                },
                error -> {
                    Toast.makeText(getContext(), R.string.not_found + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );
    }
}