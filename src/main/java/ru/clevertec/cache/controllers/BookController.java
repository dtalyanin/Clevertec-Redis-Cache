package ru.clevertec.cache.controllers;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.cache.dto.BookDto;
import ru.clevertec.cache.dto.EditBookDto;
import ru.clevertec.cache.models.Book;
import ru.clevertec.cache.services.BookService;

import java.util.List;

import static ru.clevertec.cache.utils.ErrorMessage.MIN_ID_MESSAGE;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Validated
public class BookController {

    private final BookService service;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(service.getAllBooks());
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id) {
        return ResponseEntity.ok(service.getBookById(id));
    }

    @PostMapping
    public void addBook(@RequestBody Book book) {

    }

    @PutMapping("{id}")
    public void editBook(@PathVariable long id, @RequestBody EditBookDto dto) {

    }

    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable long id) {
        service.deleteBook(id);
    }
}
