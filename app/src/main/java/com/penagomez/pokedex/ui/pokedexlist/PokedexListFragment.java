package com.penagomez.pokedex.ui.pokedexlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.penagomez.pokedex.R;
import com.penagomez.pokedex.data.dto.PokemonFavorite;
import com.penagomez.pokedex.data.service.PokedexService;
import com.penagomez.pokedex.databinding.PokedexListFragmentBinding;
import com.penagomez.pokedex.ui.pokedexlist.adapter.PokedexListRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class PokedexListFragment extends Fragment {

    private PokedexListFragmentBinding binding;
    private List<PokemonFavorite> pokemonNames = new ArrayList<>();
    private PokedexListRecyclerViewAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PokedexListFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadPokemons();

        adapter = new PokedexListRecyclerViewAdapter(pokemonNames, getActivity());
        binding.pokemonRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.pokemonRecyclerview.setAdapter(adapter);


    }

    private void loadPokemons() {
        binding.progressBar.setVisibility(View.VISIBLE);

        PokedexService pokedexService = new PokedexService();

        pokedexService.getPokemonsWithFavorites(0, 50)
        .thenAccept(pokemons -> {
            binding.progressBar.setVisibility(View.GONE);
            pokemonNames.addAll(pokemons);
            adapter.notifyDataSetChanged();
        }).exceptionally(t -> {
            if (getContext() != null) {
                Toast.makeText(getContext(), R.string.not_found, Toast.LENGTH_LONG).show();
            }
            return null;
        });


    }



}
