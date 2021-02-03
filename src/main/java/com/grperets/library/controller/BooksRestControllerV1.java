package com.grperets.library.controller;

import com.grperets.library.dto.BookDTO;
import com.grperets.library.dto.UserDTO;
import com.grperets.library.model.Book;
import com.grperets.library.model.Status;
import com.grperets.library.model.User;
import com.grperets.library.service.BookService;
import com.grperets.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/books")
public class BooksRestControllerV1 {

    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public BooksRestControllerV1(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> getBook(@PathVariable("id") Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Book book = this.bookService.getById(id);
        if (book == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookDTO bookDTO = BookDTO.fromBook(book);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO){
        if (bookDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        bookDTO.setStatus(Status.AVAILABLE);
        this.bookService.create(bookDTO.toBook());
        return new ResponseEntity<>(bookDTO, HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") Long id, @RequestBody BookDTO bookDTO){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Book book = this.bookService.getById(id);
        if (book == null){
            this.bookService.create(bookDTO.toBook());
            return new ResponseEntity<>(bookDTO, HttpStatus.CREATED);
        }
        bookDTO.setId(id);
        this.bookService.update(bookDTO.toBook());
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> deleteBook(@PathVariable("id") Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Book book = this.bookService.getById(id);
        if (book == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.bookService.delete(book);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> getAllBooks(){
        List<Book> books = this.bookService.getAll();
        if (books.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //TODO
        List<BookDTO> bookDTOS = new ArrayList<>();
        for (Book book: books){
            bookDTOS.add(BookDTO.fromBook(book));
        }
        return new ResponseEntity<>(bookDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}/take", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> takeBook(@PathVariable("id") Long id, @RequestBody UserDTO userDTO){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Book book = this.bookService.getById(id);
        if (book == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (book.getStatus().equals(Status.NOT_AVAILABLE)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //TODO

        User user = this.userService.getById(userDTO.getId());

        book.setUser(user);
        book.setStatus(Status.NOT_AVAILABLE);
        this.bookService.update(book);
        BookDTO bookDTO = BookDTO.fromBook(book);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}/return", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> returnBook(@PathVariable("id") Long id, @RequestBody UserDTO userDTO){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Book book = this.bookService.getById(id);
        if (book == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (book.getStatus().equals(Status.AVAILABLE)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        //TODO

        User user = this.userService.getById(userDTO.getId());
        user.getBooks().remove(book);
        this.userService.update(user);


        book.setStatus(Status.AVAILABLE);
        this.bookService.update(book);
        BookDTO bookDTO = BookDTO.fromBook(book);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }
}
