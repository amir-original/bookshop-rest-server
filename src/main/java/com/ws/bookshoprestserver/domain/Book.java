package com.ws.bookshoprestserver.domain;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

public class Book extends HyperMedia implements Serializable {
    @Size(min = 8,max = 8)
    private  String id;
    @Size(min = 5)
    private  String title;
    @Size(min = 5)
    private  String description;
    @NotNull
    private  BookCategory category;
    @NotNull
    private  List<Author> authors;
    @DecimalMin("00.0")
    private  float price;
    private String link;

    private String imagePath;

    public Book(String id, String title, String description, BookCategory category, List<Author> authors, float price, String link,String imagePath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.authors = authors;
        this.price = price;
        this.link = link;
        this.imagePath = imagePath;
    }

    public Book() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BookCategory getCategory() {
        return category;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public float getPrice() {
        return price;
    }

    public String getLink() {
        return link;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", authors=" + authors +
                ", price=" + price +
                ", link='" + link + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
