package com.sda.project.books;

import com.sda.project.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository=bookRepository;
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }

    public void delete(Long id)throws EmptyResultDataAccessException {
        bookRepository.deleteById(id);
    }
}
