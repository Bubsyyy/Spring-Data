package org.example.springdataautomapping.services;

import org.example.springdataautomapping.models.dtos.EditGameDto;
import org.example.springdataautomapping.models.dtos.GameDto;

public interface GameService {
    String addGame(GameDto gameDto);

    String editGame(EditGameDto editGameDto) throws IllegalAccessException;

    String deleteGame(Long id);
}
