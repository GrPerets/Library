package com.grperets.library.service.impl;

import com.grperets.library.model.Book;
import com.grperets.library.model.Status;
import com.grperets.library.repository.BookRepository;
import com.grperets.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book getById(Long id) {
        log.info("IN BookServiceImpl getById id: {}", id);
        return this.bookRepository.findById(id).orElse(null);
    }

    @Override
    public void create(Book book) {
        book.setStatus(Status.AVAILABLE);
        book.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        log.info("IN BookServiceImpl create book: {}", book);
        this.bookRepository.save(book);
    }

    @Override
    public void update(Book book) {
        Book existingBook = this.bookRepository.findById(book.getId()).orElse(new Book());
        existingBook.setBookname(book.getBookname());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPublisher(book.getPublisher());
        existingBook.setYearPublishing(book.getYearPublishing());

        existingBook.setUpdated(Timestamp.valueOf(LocalDateTime.now()));

        log.info("IN BookServiceImpl update book: {}", book);
        this.bookRepository.save(existingBook);
    }

    @Override
    public void delete(Book book) {
        log.info("IN BookServiceImpl delete book: {}", book);
        this.bookRepository.delete(book);
    }

    @Override
    public List<Book> getAll() {
        log.info("IN BookServiceImpl getAll");
        return this.bookRepository.findAll();
    }
}
