package org.example.jsonprocessing.dtos;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;

public class CategoryExportDto implements Serializable {

    @Expose
    private String category;

    @Expose
    private int productsCount;

    @Expose
    private BigDecimal averagePrice;

    @Expose
    private BigDecimal totalRevenue;

    public CategoryExportDto() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(int productsCount) {
        this.productsCount = productsCount;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
