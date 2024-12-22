package com.penagomez.pokedex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.penagomez.pokedex.data.dto.Pokemon;
import com.penagomez.pokedex.databinding.PokedexListFragmentBinding;
import com.penagomez.pokedex.ui.pokedexlist.PokedexListRecyclerViewAdapter;

import java.util.ArrayList;

public class PokedexListFragment extends Fragment {

    private PokedexListFragmentBinding binding;
    private ArrayList<Pokemon> pokemons;
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

        // Inicializa la lista de juegos
        loadPokemons();

        // Configurar el RecyclerView
        adapter = new PokedexListRecyclerViewAdapter(pokemons, getActivity());
        binding.pokemonRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.pokemonRecyclerview.setAdapter(adapter);


    }

    // Método para cargar juegos (puedes implementar tu lógica aquí)
    private void loadPokemons() {
        pokemons = new ArrayList<Pokemon>();
        pokemons.add(new Pokemon(
                "https://m.media-amazon.com/images/I/71uS8Ra1aGL._AC_UF894,1000_QL80_.jpg",
                "Pikachu"
        ));

        pokemons.add(new Pokemon(
                "https://m.media-amazon.com/images/I/71C9pOGlKtL.jpg",
                "Bulbasur"
        ));


        pokemons.add(new Pokemon(
                "https://m.media-amazon.com/images/I/91bAhoyCcUL.jpg",
                "Squirtle"
        ));

        pokemons.add(new Pokemon(
                "https://media.vandal.net/m/85340/paper-mario-the-origami-king-20205141527529_1.jpg",
                "Charizar"
        ));
    }



}
