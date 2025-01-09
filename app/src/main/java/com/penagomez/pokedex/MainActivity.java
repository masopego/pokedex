package com.penagomez.pokedex;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import com.google.firebase.auth.FirebaseAuth;
import com.penagomez.pokedex.data.dto.Pokemon;
import com.penagomez.pokedex.data.dto.PokemonMapper;
import com.penagomez.pokedex.data.dto.PokemonName;
import com.penagomez.pokedex.data.repository.APIClient;
import com.penagomez.pokedex.data.repository.FirebaseDatabase;
import com.penagomez.pokedex.data.service.PokemonDetailResponse;
import com.penagomez.pokedex.data.service.PokemonService;
import com.penagomez.pokedex.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void pokemonClicked(PokemonName pokemon, View view) {
        String pokemonName = pokemon.getName();
        Bundle bundle = new Bundle();
        bundle.putString("name", pokemonName);

        this.getPokemonDetail(pokemonName);
        Navigation.findNavController(view).navigate(R.id.myPokedexFragment, bundle);
    }

    public void pokemonFavouriteClicked(Pokemon pokemon, View view){
        String pokemonName = pokemon.getName();
        Bundle bundle = new Bundle();
        bundle.putString("name", pokemonName);


        Navigation.findNavController(view).navigate(R.id.pokemonDetailsFragment, bundle);
    }


    private void getPokemonDetail(String pokemonName){
        PokemonService service = APIClient.getRetrofitInstance().create(PokemonService.class);
        service.getPokemonByName(pokemonName).enqueue(new Callback<PokemonDetailResponse>() {
            @Override
            public void onResponse(Call<PokemonDetailResponse> call, Response<PokemonDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    FirebaseDatabase repository = new FirebaseDatabase();

                    Pokemon pokemon = PokemonMapper.fromResponse(response.body());
                    String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                    repository.saveFavouritePokemon(pokemon, userEmail);

                }
            }

            @Override
            public void onFailure(Call<PokemonDetailResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, R.string.not_found, Toast.LENGTH_LONG).show();
            }
        });
    }
}