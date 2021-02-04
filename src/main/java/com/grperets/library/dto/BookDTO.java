package com.grperets.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.grperets.library.model.Book;
import com.grperets.library.model.Status;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDTO {
    private Long id;
    private String bookname;
    private String author;
    private String publisher;
    private int yearPublishing;
    //private Status status;

    public Book toBook(){
        Book book = new Book();
        book.setId(this.id);
        book.setBookname(this.bookname);
        book.setAuthor(this.author);
        book.setPublisher(this.publisher);
        book.setYearPublishing(this.yearPublishing);
        //book.setStatus(this.status);
        return book;
    }

    public static BookDTO fromBook(Book book){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setBookname(book.getBookname());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setPublisher(book.getPublisher());
        bookDTO.setYearPublishing(book.getYearPublishing());
        //bookDTO.setStatus(book.getStatus());
        return bookDTO;
    }
}
