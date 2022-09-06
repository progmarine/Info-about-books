package com.example.books.controller;

import com.example.books.models.User;
import com.example.books.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class UserController {


    @Autowired
    private UserService userService;

    //Get users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    //Get one user
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User user = userService.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //Add user
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.create(user);
    }

    //Update user
    @PutMapping("/users/update")
    public User updateUser(@RequestBody User userFromDB,
                           @RequestBody User user) {
        return userService.update(user, userFromDB);
    }

    //Delete user
    @DeleteMapping("/users/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

}
