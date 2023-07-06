package ru.clevertec.cache.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.clevertec.cache.models.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@FeignClient(value = "books")
public interface BookServiceExt {


    @GetMapping("/books")
    List<Book> getAllBooks();
    @GetMapping("/books/{id}")
    Book getBookById(@PathVariable long id);
//
//    public void addBook(Book book);
//
//    public void deleteBook(long id);
}
