package org.example;

public class Book {

    private String name;		//title
    private String author;		//author


    public Book() {};

    public Book(String name, String author) {
        this.name = name;
        this.author = author;

    }

    public String toString() {
        String text = "Book Name: " + name+"\n"+
                "Book Author: " + author+"\n";

        return text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    public String toString2() {
        String text = name+"<N/>"+author+"<N/>";
        return text;
    }

}
