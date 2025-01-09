package com.penagomez.pokedex.data.dto;

import com.penagomez.pokedex.data.service.PokemonDetailResponse;

import java.util.List;
import java.util.stream.Collectors;

public class PokemonMapper {

    public static Pokemon fromResponse(PokemonDetailResponse response) {
        List<String> types = response.getTypes().stream()
                .map(typeWrapper -> typeWrapper.getType().getTypeName())
                .collect(Collectors.toList());

        return new Pokemon(
                response.getName(),
                response.getWeight(),
                response.getHeight(),
                response.getId(),
                types,
                response.getSprites().getFrontDefault()
        );
    }
}
