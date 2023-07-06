package ru.clevertec.cache.external.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.cache.dto.CreateBookDto;
import ru.clevertec.cache.dto.UpdateBookDto;
import ru.clevertec.cache.models.Book;

import java.util.List;

@FeignClient(value = "books")
public interface LibraryService {

    @GetMapping("/books")
    List<Book> getAllBooks();
    @GetMapping("/books/{id}")
    Book getBookById(@PathVariable long id);
    @PostMapping("/books")
    Book addBook(CreateBookDto book);
    @PutMapping("books/{id}")
    Book updateBook(@PathVariable long id, UpdateBookDto book);
    @DeleteMapping("/books/{id}")
    void deleteBookById(@PathVariable long id);
}
