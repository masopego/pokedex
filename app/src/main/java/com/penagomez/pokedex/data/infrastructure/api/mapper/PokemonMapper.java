package com.penagomez.pokedex.data.infrastructure.api.mapper;

import com.penagomez.pokedex.data.dto.Pokemon;
import com.penagomez.pokedex.data.infrastructure.api.responses.PokemonDetailResponse;
import java.util.List;
import java.util.stream.Collectors;

public class PokemonMapper {

    public static Pokemon fromResponse(PokemonDetailResponse response) {
        List<String> types = response.getTypes().stream()
                .map(typeWrapper -> PokemonTypeMapper.getTypeName(typeWrapper.getType().getTypeName()))
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
