package com.example.books.controller;

import com.example.books.models.Book;
import com.example.books.models.User;
import com.example.books.repo.BookRepo;
import com.example.books.repo.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class BookController {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private UserRepo userRepo;
    //Get books
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }
    //TODO
    @GetMapping("/users/{userId}/books")
    public ResponseEntity<List<Book>> getAllBooksByUserId(@PathVariable(value = "userId") Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new RuntimeException("Not found User with id = " + userId);
        }
        List<Book> tags = bookRepo.findAllBooksByUsersId(userId);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/books/{bookId}/users")
    public ResponseEntity<List<User>> getAllUsersByBookId(@PathVariable(value = "bookId") Long bookId) {
        if (!bookRepo.existsById(bookId)) {
            throw new RuntimeException("Not found Book with id = " + bookId);
        }
        List<User> users = userRepo.findAllUsersByBooksId(bookId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    //Get one book
    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable("id") Book book) {
        return book;
    }
    //Add book

    @PostMapping("/users/{userId}/books")
    public ResponseEntity<Book> addBook(@PathVariable(value = "userId") Long userId,
                                      @RequestBody Book book) {
        Book oneBook = userRepo.findById(userId).map(u -> {
            long bookId = book.getId();

            // book exists
            if (bookId != 0L) {
                Book _book = bookRepo.findById(bookId)
                        .orElseThrow(() -> new RuntimeException("Not found Book with id = " + bookId));
                u.addBook(_book);
                userRepo.save(u);
                return _book;
            }

            // add and create new book
            u.addBook(book);
            return bookRepo.save(book);
        }).orElseThrow(() -> new RuntimeException("Not found User with id = " + userId));
        return new ResponseEntity<>(oneBook, HttpStatus.CREATED);
    }
    @PostMapping("/books")
    public Book createBook(@RequestBody Book book){

        return bookRepo.save(book);
    }
    //Update book
    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable("id") Book bookFromDB,
                           @RequestBody Book book){
        BeanUtils.copyProperties(book, bookFromDB,"id","name");
        return bookRepo.save(bookFromDB);
    }
    //Delete book
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable("id") Book book){
        bookRepo.delete(book);
    }
    @DeleteMapping("/users/{userId}/books/{bookId}")
    public void deleteBookFromUser(@PathVariable(value = "userId") Long userId,
                                                            @PathVariable(value = "bookId") Long bookId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Not found User with id = " + userId));

        user.removeBook(bookId);
        userRepo.save(user);
    }

}
