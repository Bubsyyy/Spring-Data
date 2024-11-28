package org.example.jsonprocessing.services.impls;

import com.google.gson.Gson;
import org.example.jsonprocessing.dtos.ProductInRangeDto;
import org.example.jsonprocessing.dtos.ProductSeedDto;
import org.example.jsonprocessing.dtos.UserSeedDto;
import org.example.jsonprocessing.entities.Category;
import org.example.jsonprocessing.entities.Product;
import org.example.jsonprocessing.entities.User;
import org.example.jsonprocessing.repositories.CategoryRepository;
import org.example.jsonprocessing.repositories.ProductRepository;
import org.example.jsonprocessing.repositories.UserRepository;
import org.example.jsonprocessing.services.ProductService;
import org.example.jsonprocessing.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String PRODUCTS_PATH = "Json Processing/src/main/resources/jsons/products.json";
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final Gson gson;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, Gson gson, UserRepository userRepository,CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void seedProducts() throws FileNotFoundException {

        if(!isImported()){
            ProductSeedDto[] productSeedDtos = this.gson.fromJson
                    (new FileReader(PRODUCTS_PATH), ProductSeedDto[].class);

            for (ProductSeedDto productSeedDto : productSeedDtos) {
                if (!this.validatorUtil.isValid(productSeedDto)) {
                    this.validatorUtil.validate(productSeedDto)
                            .forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }

                Product product = this.modelMapper.map(productSeedDto, Product.class);
                product.setBuyer(getRandomUser(true));
                product.setSeller(getRandomUser(false));
                product.setCategories(getRandomCategories());
                this.productRepository.saveAndFlush(product);
            }
        }

    }

    @Override
    public void exportProductsInRange() throws IOException {
        Set<Product> allProductsInRange = this.productRepository.findAllByPriceBetweenAndBuyerIsNull(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));
        Set<ProductInRangeDto> productsInRangeDtos = allProductsInRange.stream().map(p -> {
            ProductInRangeDto dto = this.modelMapper.map(p, ProductInRangeDto.class);
            dto.setSeller(p.getSeller().getFirstName() + " " + p.getSeller().getLastName());
            return dto;
        }).collect(Collectors.toSet());

        String json = gson.toJson(productsInRangeDtos);
        FileWriter fileWriter = new FileWriter("Json Processing/src/main/resources/jsons/products-in-range.json");
        fileWriter.write(json);
        fileWriter.close();
    }

    @Override
    public boolean isImported() {
        return productRepository.count() > 0;
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();

        int randomCount = ThreadLocalRandom.current().nextInt(1,4);
        for (int i = 0; i < randomCount ; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, this.categoryRepository.count());
            categories.add(this.categoryRepository.findById(randomId).get());
        }

        return categories;
    }

    private User getRandomUser(boolean isBuyer) {
        long randomId = ThreadLocalRandom.current().nextLong(1, this.userRepository.count());

        return isBuyer && randomId % 4 == 0 ? null : this.userRepository.findById(randomId).get();
    }
}
