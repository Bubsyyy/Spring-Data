package org.example.jsonprocessing.services;


import java.io.FileNotFoundException;
import java.io.IOException;

public interface ProductService {
    void seedProducts() throws FileNotFoundException;

    void exportProductsInRange() throws IOException;

    boolean isImported();
}
