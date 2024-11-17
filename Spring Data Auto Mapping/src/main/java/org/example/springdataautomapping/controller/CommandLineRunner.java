package org.example.springdataautomapping.controller;


import org.example.springdataautomapping.models.dtos.EditGameDto;
import org.example.springdataautomapping.models.dtos.GameDto;
import org.example.springdataautomapping.models.dtos.LoginUserDto;
import org.example.springdataautomapping.models.dtos.RegisterUserDto;
import org.example.springdataautomapping.services.GameService;
import org.example.springdataautomapping.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

    private final UserService userService;
    private final GameService gameService;


    @Autowired
    public CommandLineRunner(UserService userService , GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();

        while (!"exit".equals(input)) {
            String [] tokens = input.split("\\|");
            String command = "";

            switch (tokens[0]){
                case "RegisterUser":
                    command = this.userService.registerUser(new RegisterUserDto(tokens[1],tokens[2],tokens[3],tokens[4]));
                    break;

                case "LoginUser":
                    command = this.userService.loginUser(new LoginUserDto(tokens[1],tokens[2]));
                    break;


                case "Logout":
                    command = this.userService.logoutUser();
                    break;

                case "AddGame":
                    command = this.gameService.addGame(new GameDto(tokens[1],tokens[2],tokens[3],tokens[4],tokens[5],tokens[6],tokens[7]));
                    break;

                case "EditGame":
                    String[] arguments = Arrays.stream(tokens)
                            .skip(2) // Skip the first element
                            .toArray((String[]::new));

                    EditGameDto editGameDto = new EditGameDto(arguments);
                    editGameDto.setId(Long.parseLong(tokens[1]));

                    command = this.gameService.editGame(editGameDto);
                    break;

                case "DeleteGame":
                    command = this.gameService.deleteGame(Long.parseLong(tokens[1]));
                    break;




            }



            System.out.println(command);


            input = reader.readLine();
        }

    }
}
