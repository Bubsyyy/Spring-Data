package org.example.jsonprocessing.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

public class CategorySeedDto implements Serializable {

    @Expose
    @Length(min = 3, max = 20)
    private String name;

    public CategorySeedDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
