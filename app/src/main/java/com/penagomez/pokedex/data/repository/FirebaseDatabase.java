package com.penagomez.pokedex.data.repository;

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


    public void saveFavouritePokemon(Pokemon pokemon, String userEmail){

        Map<String, Object> row = new HashMap<>();
        row.put("name", pokemon.getName());
        row.put("weight", pokemon.getWeight());
        row.put("height", pokemon.getHeight());
        row.put("index", pokemon.getId());
        row.put("image", pokemon.getImage());
        row.put("type", pokemon.getTypes());
        row.put("userId", userEmail);


        pokemonRef.set(row)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "Documento agregado correctamente");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error al agregar documento", e);
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

    public void removeFavouritePokemon(String userEmail, String pokemonName){
        pokedexRef.whereEqualTo("userId", userEmail)
                .whereEqualTo("name", pokemonName).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot documents = task.getResult();
                if (!documents.isEmpty()) {
                    for (QueryDocumentSnapshot document : documents) {
                        document.getReference().delete()
                                .addOnSuccessListener(aVoid -> {
                                    System.out.println("Documento eliminado correctamente.");
                                })
                                .addOnFailureListener(e -> {
                                    System.err.println("Error al eliminar el documento: " + e);
                                });
                    }
                } else {
                    System.out.println("No se encontraron documentos que coincidan.");
                }
            } else {
                System.err.println("Error al obtener los documentos: " + task.getException());
            }
        });
    }
}
