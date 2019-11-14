package com.sda.project.controllers;

import com.sda.project.books.BookService;
import com.sda.project.entities.Book;
import com.sda.project.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class BookControler {

    @Autowired
    private BookService bookService;

    @GetMapping("/edit-book")
    public String showBookRegisterForm(Model model) {
        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books );
        return "books/edit-book";
    }

    @GetMapping("/addbook")
    public String showRegisterBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/book-register";
    }

    @PostMapping("/loadbook")
    public String addBook(@RequestParam("image") MultipartFile image, @Valid Book book,
                          BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            return "books/book-register";
        }
        byte[] byteImage = image.getBytes();
        book.setImmage(byteImage);
        bookService.addBook(book);
        model.addAttribute("books", bookService.getBooks());
        return "books/edit-book";
    }

    @GetMapping("/edit-book/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Book book = bookService.getById(id).orElseThrow(()
                -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "books/book-update";
    }

    @PostMapping("/book-update/{id}")
    public String updateBook(@RequestParam("image") MultipartFile image, @PathVariable("id") long id,
                             @Valid Book book, BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            book.setId(id);
            return "books/book-update";
        }

        byte[] byteImage = image.getBytes();
        book.setImmage(byteImage);
        bookService.addBook(book);
        model.addAttribute("books", bookService.getBooks());
        return "books/edit-book";
    }

    @GetMapping("/delete-book/{id}")
    public String deleteBook(@PathVariable("id") long id, Model model) {
        Book book = bookService.getById(id).orElseThrow(()
                -> new IllegalArgumentException("Invalid book Id:" + id));
        bookService.delete(id);
        model.addAttribute("users", bookService.getBooks());
        return "books/edit-book";
    }

    @GetMapping("/getImmageFromDB/{id}")
    public void getImmageFromDB(@PathVariable("id") Long id, HttpServletResponse response) {
        Optional<Book> existing = bookService.getById(id);
        if (existing.isPresent()) {
            try {
                if (existing.get().getImmage()!=null) {
                    response.getOutputStream().write(existing.get().getImmage());
                    response.getOutputStream().close();
                }else {
                    response.getOutputStream().write(Files.readAllBytes(Paths.get("src/main/resources/static/img/love-books.jpg")));
                    response.getOutputStream().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/religion")
    public String showReligionPage(Model model){
        List<Book> books = getBooksFromCategory(Category.RELIGION);
        model.addAttribute("religionBooks", books);
        return "books/religion";
    }

    @GetMapping("/literature")
    public String showLiteraturePage(Model model){
        List<Book> books = getBooksFromCategory(Category.LITERATURE);
        model.addAttribute("literatureBooks", books);
        return "books/literature";
    }

    @GetMapping("/health")
    public String showHealthPage(Model model){
        List<Book> books = getBooksFromCategory(Category.HEALTH);
        model.addAttribute("healthBooks", books);
        return "books/health";
    }

    @GetMapping("/psychology")
    public String showPsychologyPage(Model model){
        List<Book> books = getBooksFromCategory(Category.PSYCHOLOGY);
        model.addAttribute("psychologyBooks", books);
        return "books/psychology";
    }

    public List<Book> getBooksFromCategory(Category category){ //Java 7
        List<Book> books = bookService.getBooks();
        List<Book> bookByCategory = new ArrayList<>();
        for (Book book : books){
            if(category.equals(book.getCategory())){
                bookByCategory.add(book);
            }
        }
        return bookByCategory;
    }

    public List<Book> getBooksByCategoryJava8(Enum category){
       return  bookService.getBooks().stream().filter(b -> b.getCategory().equals(category)).collect(Collectors.toList());
    }
}
