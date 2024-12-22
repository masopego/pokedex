package com.penagomez.pokedex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.penagomez.pokedex.databinding.MyPokedexFragmentBinding;
import com.penagomez.pokedex.databinding.PokedexListFragmentBinding;

public class PokedexListFragment extends Fragment {

    private PokedexListFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PokedexListFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
