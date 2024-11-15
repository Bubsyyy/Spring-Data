package org.example.springdataautomapping.services;

import org.example.springdataautomapping.models.dtos.GameDto;

public interface GameService {
    String addGame(GameDto gameDto);
}
