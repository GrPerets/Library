package com.grperets.library.service.impl;

import com.grperets.library.model.Book;
import com.grperets.library.repository.BookRepository;
import com.grperets.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book getById(Long id) {
        return this.bookRepository.findById(id).orElse(null);
    }

    @Override
    public void create(Book book) {
        this.bookRepository.save(book);
    }

    @Override
    public void update(Book book) {
        this.bookRepository.save(book);
    }

    @Override
    public void delete(Book book) {
        this.bookRepository.delete(book);
    }

    @Override
    public List<Book> getAll() {
        return this.bookRepository.findAll();
    }
}
