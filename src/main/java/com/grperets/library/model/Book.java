package com.grperets.library.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "books")
@Data
public class Book extends BaseEntity{

    @Column(name = "bookname")
    @NotEmpty
    private String bookname;

    @Column(name = "author")
    @NotEmpty
    private String author;

    @Column(name = "publisher")
    @NotEmpty
    private String publisher;

    @Column(name = "year_publishing")
    @NotNull
    private int yearPublishing;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
