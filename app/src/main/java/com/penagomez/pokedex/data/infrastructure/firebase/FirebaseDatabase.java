package com.penagomez.pokedex.data.infrastructure.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.penagomez.pokedex.data.dto.Pokemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FirebaseDatabase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference pokedexRef = db.collection("pokedex");
    DocumentReference pokemonRef = pokedexRef.document();


    public void saveFavouritePokemon(Pokemon pokemon, String userEmail, Consumer<Void> onSuccess, Consumer<Exception> onError) {

        Map<String, Object> row = new HashMap<>();
        row.put("name", pokemon.getName());
        row.put("weight", pokemon.getWeight());
        row.put("height", pokemon.getHeight());
        row.put("index", pokemon.getId());
        row.put("image", pokemon.getImage());
        row.put("types", pokemon.getTypes());
        row.put("userId", userEmail);

        pokemonRef.set(row)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "Documento agregado correctamente");
                        onSuccess.accept(aVoid);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error al agregar documento", e);
                        onError.accept(e);
                    }
                });
    }

    public void getFavouritePokemons(String userEmail, Consumer<List<Pokemon>> onSuccess, Consumer<Exception> onError){
        pokedexRef.whereEqualTo("userId", userEmail).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot documents = task.getResult();
                List<Pokemon> favouritePokemons = new ArrayList<>();
                for (QueryDocumentSnapshot document : documents) {
                    String name = document.getString("name");
                    int weight = document.getLong("weight").intValue();
                    int height = document.getLong("height").intValue();
                    int id = document.getLong("index").intValue();
                    List<String> types = (List<String>) document.get("types");
                    String image = document.getString("image");

                    Pokemon pokemon = new Pokemon(name, weight, height, id, types, image);
                    favouritePokemons.add(pokemon);
                }
                onSuccess.accept(favouritePokemons);

            } else {
                onError.accept(task.getException());
            }
        });
    }

    public void removeFavouritePokemon(String userEmail, String pokemonName, Consumer<Void> onSuccess, Consumer<Exception> onError){
        pokedexRef.whereEqualTo("userId", userEmail)
                .whereEqualTo("name", pokemonName).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot documents = task.getResult();
                if (!documents.isEmpty()) {
                    for (QueryDocumentSnapshot document : documents) {
                        document.getReference().delete()
                                .addOnSuccessListener(aVoid -> {
                                    onSuccess.accept(aVoid);
                                })
                                .addOnFailureListener(e -> {
                                    onError.accept(e);
                                });
                    }
                } else {
                    System.out.println("No se encontraron documentos que coincidan.");
                }
            } else {
                onError.accept(task.getException());
            }
        });
    }

}
