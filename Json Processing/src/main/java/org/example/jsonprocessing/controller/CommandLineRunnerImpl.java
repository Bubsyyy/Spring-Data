package org.example.jsonprocessing.controller;


import org.example.jsonprocessing.services.CategoryService;
import org.example.jsonprocessing.services.ProductService;
import org.example.jsonprocessing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;




    @Autowired
    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService, ProductService productService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;

    }


    @Override
    public void run(String... args) throws Exception {
        importCategories();
        importUsers();
        importProducts();
        this.productService.exportProductsInRange();
        this.userService.exportSuccessfullySoldProducts();
    }

    private void importProducts() throws FileNotFoundException {

        this.productService.seedProducts();
    }

    private void importUsers() throws FileNotFoundException {

        this.userService.seedUsers();
    }


    private void importCategories() throws FileNotFoundException {

        this.categoryService.seedCategories();

    }
}
