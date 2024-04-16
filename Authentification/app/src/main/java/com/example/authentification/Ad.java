package com.example.authentification;

public class Ad {

    private String title;
    private String author;
    private String description;
    private String userEmail;

    // конструктор, геттеры и сеттеры

    public Ad() {
        // Пустой конструктор нужен для Firestore
    }

    public Ad(String title, String author, String description, String userEmail) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.userEmail = userEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
