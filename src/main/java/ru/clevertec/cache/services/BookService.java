package ru.clevertec.cache.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.cache.dto.BookDto;
import ru.clevertec.cache.dto.CreateBookDto;
import ru.clevertec.cache.dto.UpdateBookDto;
import ru.clevertec.cache.external.service.LibraryService;
import ru.clevertec.cache.models.Book;
import ru.clevertec.cache.utils.BookMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final LibraryService service;
    private final BookMapper mapper;

    @GetMapping
    public List<BookDto> getAllBooks() {
        return mapper.convertAllToDtos(service.getAllBooks());
    }

    @Cacheable("books")
    public BookDto getBookById(long id) {
        System.out.println("get");
        Book book = service.getBookById(id);
        return mapper.convertToDto(book);
    }

    @CachePut(value = "books", key = "#result.id")
    public BookDto addBook(@RequestBody CreateBookDto book) {
        System.out.println("add");
        Book createdBook = service.addBook(book);
        return mapper.convertToDto(createdBook);
    }

    @CachePut(value = "books", key = "#result.id")
    public BookDto updateBook(long id, UpdateBookDto dto) {
        System.out.println("update");
        Book updatedBook = service.updateBook(id, dto);
        return mapper.convertToDto(updatedBook);
    }

    @CacheEvict(value = "books")
    public void deleteBookById(long id) {
        System.out.println("delete");
        service.deleteBookById(id);
    }
}
