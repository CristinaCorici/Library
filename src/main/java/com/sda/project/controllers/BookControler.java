package com.sda.project.controllers;

import com.sda.project.books.BookService;
import com.sda.project.entities.Book;
import com.sda.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

public class BookControler {

    @Autowired
    private BookService bookService;

    @PostMapping("/addbook")
    public String addBook(@Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "book-register";
        }

        bookService.addBook(book);
        model.addAttribute("books", bookService.getBooks());
        return "edit-book";
    }
}
