package com.example.books.controller;

import com.example.books.models.User;
import com.example.books.repo.BookRepo;
import com.example.books.repo.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("")
public class UserController {

    private final UserRepo userRepo;
    @Autowired
    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    //Get users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    //Get one user
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found User with id = " + id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    //Add user
    @PostMapping("/users")
    public User createUser(@RequestBody User user){
        user.setCreatedAt(LocalDateTime.now());
        return userRepo.save(user);
    }
    //Update user
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable("id") User userFromDB,
                                 @RequestBody User user){
        BeanUtils.copyProperties(user, userFromDB,"id","name","email","createdAt");
        return userRepo.save(userFromDB);
    }
    //Delete user
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") User user){
        userRepo.delete(user);
    }

}
