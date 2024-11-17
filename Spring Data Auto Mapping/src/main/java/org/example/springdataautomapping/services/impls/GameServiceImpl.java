package org.example.springdataautomapping.services.impls;

import org.example.springdataautomapping.models.dtos.EditGameDto;
import org.example.springdataautomapping.models.dtos.GameDetailDto;
import org.example.springdataautomapping.models.dtos.GameDto;
import org.example.springdataautomapping.models.entities.Game;
import org.example.springdataautomapping.models.entities.User;
import org.example.springdataautomapping.repositories.GameRepository;
import org.example.springdataautomapping.services.GameService;
import org.example.springdataautomapping.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Override
    public String showAllGames() {
        List<Game> games = this.gameRepository.findAll();

        StringBuilder sb = new StringBuilder();
        games.stream().forEach(game -> {
            sb.append(String.format("%s - %.2f\n", game.getTitle(), game.getPrice()));
        });

        return sb.toString();

    }

    @Override
    public String showGameDetails(String title) {
        Optional<Game> gameByTitle = this.gameRepository.findByTitle(title);

        if (gameByTitle.isPresent()) {

            Game game = gameByTitle.get();
            GameDetailDto gameDetailDto = modelMapper.map(game, GameDetailDto.class);

            return String.format("%s  %.2f %s %s\n", gameDetailDto.getTitle(), gameDetailDto.getPrice(), gameDetailDto.getDescription(),gameDetailDto.getReleaseDate());
        }

        throw new IllegalArgumentException("The game does not exist.");


    }

    @Override
    public String showOwnedGames() {
        if(!this.userService.isLoggedIn()){
            throw new IllegalArgumentException("You are not logged in.");
        }

        User loggedUser = this.userService.getUser();
        StringBuilder sb = new StringBuilder();
        loggedUser.getGames().stream().forEach(game -> {
            sb.append(game.getTitle());
            sb.append(System.lineSeparator());

        });
        return sb.toString();

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
