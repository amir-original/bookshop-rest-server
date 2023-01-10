package domain;

import java.util.List;

public class BookBuilder {
    private String id;
    private String title;
    private String description;
    private BookCategory category;
    private List<Author> authors;
    private float price;
    private String link;

    private String imagePath;

    public BookBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public BookBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public BookBuilder setCategory(BookCategory category) {
        this.category = category;
        return this;
    }

    public BookBuilder setAuthors(List<Author> authors) {
        this.authors = authors;
        return this;
    }

    public BookBuilder setPrice(float price) {
        this.price = price;
        return this;
    }

    public BookBuilder setLink(String link) {
        this.link = link;
        return this;
    }

    public BookBuilder setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public Book createBook() {
        return new Book(id, title, description, category, authors, price, link, imagePath);
    }
}