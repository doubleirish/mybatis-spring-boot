package com.example.model;

/**
 * Created by Conor on 8/22/2016.
 */
public class Book {
  private Integer id;
  private String isbn;
  private String title;
  private String description;
  private String author;
  private String genre;
  private double price;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Book{");
    sb.append("id=").append(id);
    sb.append(", isbn='").append(isbn).append('\'');
    sb.append(", title='").append(title).append('\'');
    sb.append(", description='").append(description).append('\'');
    sb.append(", author='").append(author).append('\'');
    sb.append(", genre='").append(genre).append('\'');
    sb.append(", price=").append(price);
    sb.append('}');
    return sb.toString();
  }
}
