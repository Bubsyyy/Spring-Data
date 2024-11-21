package org.example.jsonprocessing.services.impls;

import com.google.gson.Gson;
import org.example.jsonprocessing.dtos.UserSeedDto;
import org.example.jsonprocessing.entities.User;
import org.example.jsonprocessing.repositories.UserRepository;
import org.example.jsonprocessing.services.UserService;
import org.example.jsonprocessing.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;

@Service
public class UserServiceImpl implements UserService {

    private static final String USER_PATH = "Json Processing/src/main/resources/jsons/users.json";
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final Gson gson;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, Gson gson) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
    }




    @Override
    public void seedUsers() throws FileNotFoundException {

        if(!isImported()){
            UserSeedDto[] userSeedDtos = this.gson.fromJson
                    (new FileReader(USER_PATH), UserSeedDto[].class);

            for (UserSeedDto userSeedDto : userSeedDtos) {
                if (!this.validatorUtil.isValid(userSeedDto)) {
                    this.validatorUtil.validate(userSeedDto)
                            .forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }

                User user = this.modelMapper.map(userSeedDto, User.class);
                this.userRepository.saveAndFlush(user);
            }
        }

    }

    private boolean isImported() {
        return userRepository.count() > 0;
    }
}
