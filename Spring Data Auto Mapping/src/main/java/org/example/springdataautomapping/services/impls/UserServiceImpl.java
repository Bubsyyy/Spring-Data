package org.example.springdataautomapping.services.impls;

import jakarta.validation.ConstraintViolation;
import org.example.springdataautomapping.models.dtos.LoginUserDto;
import org.example.springdataautomapping.models.dtos.RegisterUserDto;
import org.example.springdataautomapping.models.entities.User;
import org.example.springdataautomapping.repositories.UserRepository;
import org.example.springdataautomapping.services.UserService;
import org.example.springdataautomapping.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private User user;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,ModelMapper modelMapper,ValidatorUtil validatorUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String registerUser(RegisterUserDto registerUserDto) {

        if(!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            return "The passwords do not match";
        }

        if(!validatorUtil.isValid(registerUserDto)){
            return validatorUtil.validate(registerUserDto).stream().
                    map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
        }

        if(this.userRepository.findByEmail(registerUserDto.getEmail()).isPresent()){

            return "The email address is already in use";

        }




        User user = this.modelMapper.map(registerUserDto, User.class);

        if(userRepository.count()==0){
            user.setAdmin(true);
        }
        this.userRepository.saveAndFlush(user);


        return  String.format("User %s registered successfully", user.getFullName());
    }

    @Override
    public String loginUser(LoginUserDto loginUserDto) {

        if(isLoggedIn()){
            return "Already logged in";
        }

        Optional<User> userToLogIn = userRepository.findByEmailAndPassword(loginUserDto.getEmail(),loginUserDto.getPassword());

        if(userToLogIn.isEmpty()){
            return "The user does not exist";
        }
        User user = userToLogIn.get();
        this.user = user;


        return String.format("Successfully logged in %s", user.getFullName());
    }

    @Override
    public String logoutUser() {
        if(!isLoggedIn()){
            return "The user is not logged in";
        }

        User loggedInUser = this.getUser();

        String output = String.format("User %s successfully logged out", loggedInUser.getFullName());

        this.user = null;
        return output;
    }


    @Override
    public boolean isLoggedIn(){
        return this.user != null ;
    }

    @Override
    public User getUser() {
        return this.user;
    }


}
