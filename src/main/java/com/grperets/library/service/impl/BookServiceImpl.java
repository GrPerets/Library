package com.grperets.library.service.impl;

import com.grperets.library.model.Book;
import com.grperets.library.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Override
    public Book getById(Long id) {
        return null;
    }

    @Override
    public void save(Book book) {

    }

    @Override
    public void delete(Book book) {

    }

    @Override
    public List<Book> getAll() {
        return null;
    }
}
