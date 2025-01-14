package com.penagomez.pokedex.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.penagomez.pokedex.ui.utils.LocaleHelper;
import com.penagomez.pokedex.R;
import com.penagomez.pokedex.data.dto.Pokemon;
import com.penagomez.pokedex.data.dto.PokemonFavorite;
import com.penagomez.pokedex.data.infrastructure.firebase.FirebaseDatabase;
import com.penagomez.pokedex.data.service.PokedexService;
import com.penagomez.pokedex.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    NavController navController = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
            NavigationUI.setupActionBarWithNavController(this, navController);
        }


        binding.bottomNavigationView.setOnItemSelectedListener(this::selectedBottomMenu);

        configureActionBar();
    }

    private boolean selectedBottomMenu(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.my_pokemons_menu)
            navController.navigate(R.id.myPokedexFragment);
        else if(menuItem.getItemId() == R.id.pokedex_list_menu)
            navController.navigate(R.id.pokedexListFragment);
        else if(menuItem.getItemId() == R.id.settings_menu){
            navController.navigate(R.id.settingsFragment);
        }

        return true;

    }

    private void configureActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
            getSupportActionBar().hide();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    public void pokemonClicked(PokemonFavorite pokemon, View view) {
        String pokemonName = pokemon.getName();
        Bundle bundle = new Bundle();
        bundle.putString("name", pokemonName);

        this.getPokemonDetail(pokemonName).thenAccept(pokemonDetailResponse -> {
            Navigation.findNavController(view).navigate(R.id.myPokedexFragment, bundle);
            binding.bottomNavigationView.setSelectedItemId(R.id.my_pokemons_menu);
        });

    }

    public void pokemonFavouriteClicked(Pokemon pokemon, View view){
        Bundle bundle = new Bundle();
        bundle.putString("name", pokemon.getName());
        bundle.putString("weight", String.valueOf(pokemon.getWeight()));
        bundle.putString("height", String.valueOf(pokemon.getHeight()));
        bundle.putString("id", String.valueOf(pokemon.getId()));
        bundle.putString("image", pokemon.getImage());
        bundle.putStringArrayList("types", new ArrayList<>(pokemon.getTypes()));

        Navigation.findNavController(view).navigate(R.id.pokemonDetailsFragment, bundle);
    }


    private CompletableFuture<Void> getPokemonDetail(String pokemonName){
        CompletableFuture<Void> future = new CompletableFuture<>();

        new PokedexService().getPokemonByName(pokemonName)
             .thenAccept(pokemon -> {
                 FirebaseDatabase repository = new FirebaseDatabase();
                 String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                 repository.saveFavouritePokemon(
                         pokemon,
                         userEmail,
                         result -> {
                             Log.d("Firebase", "Pokemon guardado correctamente.");
                             future.complete(null);
                         },
                         error -> {
                             Log.e("Firebase", "Error al guardar el Pokemon", error);
                             future.completeExceptionally(error);
                         });

             })
             .exceptionally(ex -> {
                 Log.e("PokemonDetail", "Error al obtener los detalles del Pokémon", ex);
                 Toast.makeText(MainActivity.this, "Error al obtener los detalles del Pokémon", Toast.LENGTH_SHORT).show();
                 future.completeExceptionally(ex);
                 return null;
             });

        return future;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(newBase);
        String languageCode = prefs.getString("language", "es");
        Context context = LocaleHelper.updateLocale(newBase, languageCode);
        super.attachBaseContext(context);
    }
}