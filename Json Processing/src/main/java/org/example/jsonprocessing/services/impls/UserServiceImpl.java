package org.example.jsonprocessing.services.impls;

import com.google.gson.Gson;
import org.example.jsonprocessing.dtos.SoldProductDto;
import org.example.jsonprocessing.dtos.UserSeedDto;
import org.example.jsonprocessing.dtos.UserSoldProductsDto;
import org.example.jsonprocessing.entities.User;
import org.example.jsonprocessing.repositories.UserRepository;
import org.example.jsonprocessing.services.UserService;
import org.example.jsonprocessing.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public void exportSuccessfullySoldProducts() throws IOException {
        Set<User> usersBySoldIsNotNull = this.userRepository.findUsersBySoldIsNotNull();
        usersBySoldIsNotNull.stream().filter(user -> user.getSold().size()>0);

        Set<UserSoldProductsDto> userSoldProductsDtos = usersBySoldIsNotNull.stream().map(user -> {
                    UserSoldProductsDto userSoldProductsDto = this.modelMapper.map(user, UserSoldProductsDto.class);
                    userSoldProductsDto.setSoldProducts(user.getSold().stream()
                            .map(product -> this.modelMapper.map(product, SoldProductDto.class)).collect(Collectors.toSet()));


                    return userSoldProductsDto;

                }
        ).collect(Collectors.toSet());


        String json = gson.toJson(userSoldProductsDtos);
        FileWriter fileWriter = new FileWriter("Json Processing/src/main/resources/jsons/user-sold-products.json");
        fileWriter.write(json);
        fileWriter.close();



    }

    private boolean isImported() {
        return userRepository.count() > 0;
    }
}
