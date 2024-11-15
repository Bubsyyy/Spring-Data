package org.example.springdataautomapping.services;

import org.example.springdataautomapping.models.dtos.LoginUserDto;
import org.example.springdataautomapping.models.dtos.RegisterUserDto;
import org.example.springdataautomapping.models.entities.User;

public interface UserService {

    String registerUser(RegisterUserDto registerUserDto);

    String loginUser(LoginUserDto loginUserDto );

}
