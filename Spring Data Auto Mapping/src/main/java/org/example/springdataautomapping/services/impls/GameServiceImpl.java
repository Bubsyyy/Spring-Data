package org.example.springdataautomapping.services.impls;

import org.example.springdataautomapping.models.dtos.GameDto;
import org.example.springdataautomapping.models.entities.Game;
import org.example.springdataautomapping.repositories.GameRepository;
import org.example.springdataautomapping.services.GameService;
import org.example.springdataautomapping.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {
    private final UserService userService;
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GameServiceImpl(UserService userService, GameRepository gameRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public String addGame(GameDto gameDto) {
        validateAdminUser();

        Game game = this.modelMapper.map(gameDto, Game.class);

        gameRepository.saveAndFlush(game);

        return String.format("Added %s", game.getTitle());

    }

    private void validateAdminUser() {
        if(!userService.isLoggedIn()){
            throw new IllegalArgumentException("You are not logged in");
        }

        if(!userService.getUser().isAdmin()){
            throw new IllegalArgumentException("You do not have permission to add game");
        }
    }
}
