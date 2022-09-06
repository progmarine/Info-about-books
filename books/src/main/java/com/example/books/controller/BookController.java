package com.example.books.controller;

import com.example.books.models.Book;
import com.example.books.models.User;
import com.example.books.service.BookService;
import com.example.books.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    //Get books
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/users/{userId}/books")
    public ResponseEntity<List<Book>> getAllBooksByUserId(@PathVariable(value = "userId") Long id) {
        Long userId = userService.getById(id).getId();
        List<Book> tags = bookService.getAllByUserId(userId);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/books/{bookId}/users")
    public ResponseEntity<List<User>> getAllUsersByBookId(@PathVariable(value = "bookId") Long bookId) {
        bookService.checkIfExists(bookId);
        List<User> users = userService.findAllUsersByBookId(bookId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //Get one book
    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable("id") Long id) {
        return bookService.getById(id);
    }

    //Add book
    @PostMapping("/users/{userId}/books")
    public ResponseEntity<Book> addBook(@PathVariable(value = "userId") Long userId,
                                        @RequestBody Book book) {
        Book oneBook = bookService.checkBookAndAdd(userId, book);
        return new ResponseEntity<>(oneBook, HttpStatus.CREATED);
    }

    @PostMapping("/books")
    public Book createBook(@RequestBody Book book) {
        return bookService.create(book);
    }

    //Update book
    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable("id") Book bookFromDB,
                           @RequestBody Book book) {
        return bookService.update(book, bookFromDB);
    }

    //Delete book
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.delete(id);
    }

    @DeleteMapping("/users/{userId}/books/{bookId}")
    public void deleteBookFromUser(@PathVariable(value = "userId") Long userId,
                                   @PathVariable(value = "bookId") Long bookId) {
        bookService.deleteFromUser(userId, bookId);
    }

}
