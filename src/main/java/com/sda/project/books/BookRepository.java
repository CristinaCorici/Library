package com.sda.project.books;

import com.sda.project.entities.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface BookRepository extends CrudRepository<Book, Long> {
    Optional<Book> findByBookName(String bookName);
    List<Book> findByAuthor(String author);

    List<Book> findAll();
}
