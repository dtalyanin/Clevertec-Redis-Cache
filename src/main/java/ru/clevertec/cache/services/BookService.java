package ru.clevertec.cache.services;

import org.springframework.web.bind.annotation.RequestBody;
import ru.clevertec.cache.dto.BookDto;
import ru.clevertec.cache.dto.CreateBookDto;
import ru.clevertec.cache.dto.UpdateBookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();
    BookDto getBookById(long id);
    BookDto addBook(@RequestBody CreateBookDto book);
    BookDto updateBook(long id, UpdateBookDto dto);
    void deleteBookById(long id);
}
