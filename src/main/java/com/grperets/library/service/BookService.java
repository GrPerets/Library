package com.grperets.library.service;

import com.grperets.library.model.Book;

import java.util.List;

public interface BookService {
    Book getById(Long id);
    void save(Book book);
    void delete(Book book);
    List<Book> getAll();
}
