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
import com.penagomez.pokedex.data.dto.PokemonName;
import com.penagomez.pokedex.data.repository.APIClient;
import com.penagomez.pokedex.data.service.PokemonListResponse;
import com.penagomez.pokedex.data.service.PokemonService;
import com.penagomez.pokedex.databinding.PokedexListFragmentBinding;
import com.penagomez.pokedex.ui.pokedexlist.adapter.PokedexListRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokedexListFragment extends Fragment {

    private PokedexListFragmentBinding binding;
    private List<PokemonName> pokemonNames = new ArrayList<>();
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
        PokemonService service = APIClient.getRetrofitInstance().create(PokemonService.class);
        service.getPokemonList(0,50).enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pokemonNames.addAll(response.body().getResults());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                if (getContext() != null) {
                    Toast.makeText(getContext(), R.string.not_found, Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}