package org.example.springdataautomapping.models.dtos;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GameDto {

    private String title;
    private String trailer;
    private String thumbnailUrl;
    private Double size;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;


    public GameDto(String title,String price,String size,String trailer,String thumbnailUrl,String description,String releaseDate) {
        this.title = title;
        this.price = new BigDecimal(price);
        this.size = Double.parseDouble(size);
        this.trailer = trailer;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.releaseDate = LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
