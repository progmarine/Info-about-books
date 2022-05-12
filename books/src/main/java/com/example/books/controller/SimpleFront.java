// package com.example.books.controller;

// import com.example.books.models.Book;
// import com.example.books.models.User;
// import com.example.books.repo.BookRepo;
// import com.example.books.repo.UserRepo;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;

// @Controller
// public class SimpleFront {
//     @Autowired
//     BookRepo bookRepo;
//     @Autowired
//     UserRepo userRepo;
//     @GetMapping("/")
//     public String homePage(Model model){
//         Iterable<Book> books = bookRepo.findAll();
//         Iterable<User> users = userRepo.findAll();
//         model.addAttribute("books",books);
//         model.addAttribute("users",users);
//         return "home";
//     }
// }
