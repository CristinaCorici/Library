package com.sda.project.controllers;

import com.sda.project.books.BookService;
import com.sda.project.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BookControler {

    @Autowired
    private BookService bookService;

    @GetMapping("/edit-book")
    public String showBookRegisterForm() {
        return "edit-book";
    }

    @GetMapping("/addbook")
    public String showRegisterBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-register";
    }

    @PostMapping("/loadbook")
    public String addBook(@Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "book-register";
        }

        bookService.addBook(book);
        model.addAttribute("books", bookService.getBooks());
        return "edit-book";
    }

    @GetMapping("/edit-book/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Book book = bookService.getById(id).orElseThrow(()
                -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "book-update";
    }

    @PostMapping("/book-update/{id}")
    public String updateBook(@PathVariable("id") long id, @Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setId(id);
            return "book-update";
        }

        bookService.addBook(book);
        model.addAttribute("books", bookService.getBooks());
        return "edit-book";
    }

    @GetMapping("/delete-book/{id}")
    public String deleteBook(@PathVariable("id") long id, Model model) {
        Book book = bookService.getById(id).orElseThrow(()
                -> new IllegalArgumentException("Invalid book Id:" + id));
        bookService.delete(id);
        model.addAttribute("users", bookService.getBooks());
        return "edit-book";
    }
}
