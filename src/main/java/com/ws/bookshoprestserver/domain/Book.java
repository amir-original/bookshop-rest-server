package com.ws.bookshoprestserver.domain;

import java.util.List;

public class Book {
    private  String id;
    private  String title;
    private  String description;
    private  BookCategory category;
    private  List<Author> authors;
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
