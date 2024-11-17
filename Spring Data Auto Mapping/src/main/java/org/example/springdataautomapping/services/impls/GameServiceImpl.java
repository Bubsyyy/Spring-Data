package org.example.springdataautomapping.services.impls;

import org.example.springdataautomapping.models.dtos.EditGameDto;
import org.example.springdataautomapping.models.dtos.GameDto;
import org.example.springdataautomapping.models.entities.Game;
import org.example.springdataautomapping.repositories.GameRepository;
import org.example.springdataautomapping.services.GameService;
import org.example.springdataautomapping.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

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

    @Override
    public String editGame(EditGameDto editGameDto) throws IllegalAccessException {
        validateAdminUser();


        Game gameToEdit = this.gameRepository.findById(editGameDto.getId()).orElseThrow(() -> new IllegalArgumentException("Game with the given ID does not exist."));

        for (Field field : editGameDto.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(editGameDto) != null) {
                switch (field.getName()) {
                    case "title" : gameToEdit.setTitle((String) field.get(editGameDto));
                    break;
                    case "trailer" : gameToEdit.setTrailer((String) field.get(editGameDto));
                    break;
                    case "thumbnailUrl" : gameToEdit.setImageThumbnail((String) field.get(editGameDto));
                    break;
                    case "size" : gameToEdit.setSize((Double) field.get(editGameDto));
                    break;
                    case "price" : gameToEdit.setPrice((BigDecimal) field.get(editGameDto));
                    break;
                    case "description" : gameToEdit.setDescription((String) field.get(editGameDto));
                    break;
                    case "releaseDate" : gameToEdit.setReleaseDate((LocalDate) field.get(editGameDto));
                    break;
                }
            }
        }

        this.gameRepository.saveAndFlush(gameToEdit);

        return String.format("Edited %s", gameToEdit.getTitle());
    }

    @Override
    public String deleteGame(Long id) {
        validateAdminUser();
        Game gameToDelete = this.gameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Game with the given ID does not exist."));
        this.gameRepository.delete(gameToDelete);


        return String.format("Deleted %s", gameToDelete.getTitle());
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
