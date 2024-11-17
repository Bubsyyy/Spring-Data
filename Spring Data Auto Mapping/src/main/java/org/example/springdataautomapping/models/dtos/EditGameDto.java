package org.example.springdataautomapping.models.dtos;

import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EditGameDto {

    private Long id;
    private String title;
    private String trailer;
    private BigDecimal price;
    private Double size;
    private String imageThumbnail;
    private String description;
    private LocalDate releaseDate;


    public EditGameDto(String[] arguments) {

        for (String argument : arguments) {
            String[] parts = argument.split("=");

            switch (parts[0]) {
                case "title":
                    this.title = parts[1];
                    break;
                case "trailer":
                    this.trailer = parts[1];
                    break;
                case "price":
                    this.price = new BigDecimal(parts[1]);
                    break;
                case "size":
                    this.size = Double.parseDouble(parts[1]);
                    break;
                case "imageThumbnail":
                    this.imageThumbnail = parts[1];
                    break;
                case "description":
                    this.description = parts[1];
                    break;
                case "releaseDate":
                    this.releaseDate = LocalDate.parse(parts[1]);
                    break;
            }
        }



    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }


}
