package com.example.books.repo;

import com.example.books.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    List<Book> findAllBooksByUsersId(Long Id);
}
