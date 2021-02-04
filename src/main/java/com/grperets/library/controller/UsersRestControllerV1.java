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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UsersRestControllerV1 {

    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public UsersRestControllerV1(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id){
        if( id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = this.userService.getById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDTO userDTO = UserDTO.fromUser(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult){
        if (userDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.userService.create(userDTO.toUser());
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);

    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult){
        if (userDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.userService.update(userDTO.toUser());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = this.userService.getById(id);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.userService.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<User> users = this.userService.getAll();
        if (users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<UserDTO> userDTOS = users.stream().map(user->UserDTO.fromUser(user)).collect(Collectors.toList());
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}/books", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> getUserBooks(@PathVariable("id") Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = this.userService.getById(id);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Book> books = user.getBooks();
        List<BookDTO> bookDTOS = books.stream().map(book -> BookDTO.fromBook(book)).collect(Collectors.toList());
        return new ResponseEntity<>(bookDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "{userId}/books/{bookId}/return", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> returnBook(@PathVariable("userId") Long userId, @PathVariable("bookId") Long bookId){
        if (userId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (bookId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = this.userService.getById(userId);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Book book = this.bookService.getById(bookId);
        if (book == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        book.setStatus(Status.AVAILABLE);
        book.setUser(null);
        this.bookService.update(book);


        List<BookDTO> bookDTOS = user.getBooks().stream().filter(y->!(y.getId().equals(bookId))).map(x->BookDTO.fromBook(x)).collect(Collectors.toList());

        return new ResponseEntity<>(bookDTOS, HttpStatus.OK);

    }

}
