package org.example.jsonprocessing.services.impls;

import com.google.gson.Gson;
import org.example.jsonprocessing.dtos.CategoryExportDto;
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
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;


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

    @Override
    public void exportCategories() throws IOException {



        Set<CategoryExportDto> categoryExportDtos = this.categoryRepository.findAll().stream().map(category -> {
            CategoryExportDto categoryExportDto = new CategoryExportDto();
            categoryExportDto.setCategory(category.getName());
            categoryExportDto.setProductsCount(category.getProducts().size());

            double sum = category.getProducts().stream().mapToDouble(product -> product.getPrice().doubleValue()).sum();
            double average = sum / category.getProducts().size();
            if (sum == 0){
            average = 0;
            }


            categoryExportDto.setAveragePrice(BigDecimal.valueOf(average));
            categoryExportDto.setTotalRevenue(BigDecimal.valueOf(sum));
            return categoryExportDto;

        }).sorted(Comparator.comparingInt(CategoryExportDto::getProductsCount).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));





        String json = gson.toJson(categoryExportDtos);
        FileWriter fileWriter = new FileWriter("Json Processing/src/main/resources/jsons/categories-export.json");
        fileWriter.write(json);
        fileWriter.close();
    }

    @Override
    public boolean isImported() {
        return categoryRepository.count() > 0;
    }


}

