package com.example.books.service;

import com.example.books.models.User;
import com.example.books.repo.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User getById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found User with id = " + id));
    }

    public User create(User user) {
        user.setCreatedAt(LocalDateTime.now());
        return userRepo.save(user);
    }

    public User update(User user, User userFromDB) {
        BeanUtils.copyProperties(user, userFromDB, "id", "name", "email", "createdAt");
        return userRepo.save(userFromDB);
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    public List<User> findAllUsersByBookId(Long bookId) {
        return userRepo.findAllUsersByBooksId(bookId);
    }

}
