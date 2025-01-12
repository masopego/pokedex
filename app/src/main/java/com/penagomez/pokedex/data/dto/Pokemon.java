package com.penagomez.pokedex.data.dto;

import java.util.List;

public class Pokemon {
    private final String name;
    private final int weight;
    private final int height;
    private final int id;
    private final List<String> types;
    private final String image;


    public Pokemon(String name, int weight, int height, int id, List<String> types, String image){
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.id = id;
        this.types = types;
        this.image = image;
    }

    public String getName() {
        return this.name;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

    public List<String> getTypes() {
        return types;
    }

    public String getImage() {
        return image;
    }
}
