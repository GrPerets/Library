package com.grperets.library.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "books")
@Data
public class Book extends BaseEntity{

    @Column(name = "bookname")
    private String bookname;

    @Column(name = "author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "year_publishing")
    private String yearPublishing;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "books_user",
            joinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    //@JoinColumn(name = "user_id")
    private User user;
}
