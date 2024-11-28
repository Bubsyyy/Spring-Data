package org.example.jsonprocessing.services;


import java.io.FileNotFoundException;
import java.io.IOException;

public interface UserService {
    void seedUsers() throws FileNotFoundException;

    void exportSuccessfullySoldProducts() throws IOException;

    boolean isImported();
}
