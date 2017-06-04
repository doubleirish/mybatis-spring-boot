package com.example.model;

public class Book {
  private Integer id;
  private String isbn;
  private String title;
  private String description;
  private String authorFirstName;
  private String authorLastName;
  private String genre;
  private double price;

  // setters , getters and toString()
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

  public String getAuthorFirstName() {
    return authorFirstName;
  }
  public void setAuthorFirstName(String authorFirstName) {
    this.authorFirstName = authorFirstName;
  }

  public String getAuthorLastName() {
    return authorLastName;
  }
  public void setAuthorLastName(String authorLastName) {
    this.authorLastName = authorLastName;
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
    return "Book{" +
        "id=" + id +
        ", isbn='" + isbn + '\'' +
        ", title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", authorFirstName='" + authorFirstName + '\'' +
        ", authorLastName='" + authorLastName + '\'' +
        ", genre='" + genre + '\'' +
        ", price=" + price +
        '}';
  }
}
