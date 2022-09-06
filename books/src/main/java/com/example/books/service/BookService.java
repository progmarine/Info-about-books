package com.example.books.service;

import com.example.books.models.Book;
import com.example.books.models.User;
import com.example.books.repo.BookRepo;
import com.example.books.repo.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private UserRepo userRepo;

    public List<Book> getAll() {
        return bookRepo.findAll();
    }

    public List<Book> getAllByUserId(Long userId) {
        return bookRepo.findAllBooksByUsersId(userId);
    }

    public void checkIfExists(Long bookId) {
        if (!bookRepo.existsById(bookId)) {
            throw new RuntimeException("Not found Book with id = " + bookId);
        }
    }

    public Book getById(Long id) {
        return bookRepo.getById(id);
    }

    public Book checkBookAndAdd(Long userId, Book book) {
        return userRepo.findById(userId).map(u -> {
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
    }

    public Book create(Book book) {
        return bookRepo.saveAndFlush(book);
    }

    public Book update(Book book, Book bookFromDB) {
        BeanUtils.copyProperties(book, bookFromDB, "id", "name");
        return bookRepo.save(bookFromDB);
    }

    public void delete(Long id) {
        bookRepo.deleteById(id);
    }

    public void deleteFromUser(Long userId, Long bookId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Not found User with id = " + userId));

        user.removeBook(bookId);
        userRepo.save(user);
    }
}
