package org.example.jsonprocessing.services.impls;

import com.google.gson.Gson;
import org.example.jsonprocessing.dtos.CategorySeedDto;
import org.example.jsonprocessing.entities.Category;
import org.example.jsonprocessing.repositories.CategoryRepository;
import org.example.jsonprocessing.services.CategoryService;
import org.example.jsonprocessing.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;


@Service
public class CategoryServiceImpl implements CategoryService {


    private static final String CATEGORIES_PATH = "Json Processing/src/main/resources/jsons/categories.json";
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final Gson gson;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, Gson gson) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
    }


    @Override
    public void seedCategories() throws FileNotFoundException {

        if(!isImported()){
            CategorySeedDto[] categorySeedDtos = this.gson.fromJson
                    (new FileReader(CATEGORIES_PATH), CategorySeedDto[].class);

            for (CategorySeedDto categorySeedDto : categorySeedDtos) {
                if (!this.validatorUtil.isValid(categorySeedDto)) {
                    this.validatorUtil.validate(categorySeedDto)
                            .forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }

                Category category = this.modelMapper.map(categorySeedDto, Category.class);
                this.categoryRepository.saveAndFlush(category);
            }
        }

    }

    private boolean isImported() {
        return categoryRepository.count() > 0;
    }


}

