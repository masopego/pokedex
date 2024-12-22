package com.penagomez.pokedex.data.dto;

public class Pokemon {

    private final String image;
    private final String name;


    public Pokemon(String image, String name){
        this.image = image;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
