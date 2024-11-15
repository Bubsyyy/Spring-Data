package org.example.springdataautomapping.controller;


import org.example.springdataautomapping.models.dtos.LoginUserDto;
import org.example.springdataautomapping.models.dtos.RegisterUserDto;
import org.example.springdataautomapping.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

    private final UserService userService;


    @Autowired
    public CommandLineRunner(UserService userService) {
        this.userService = userService;
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

            }



            System.out.println(command);


            input = reader.readLine();
        }

    }
}
