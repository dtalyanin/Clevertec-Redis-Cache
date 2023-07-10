package ru.clevertec.cache.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.clevertec.cache.dto.BookDto;
import ru.clevertec.cache.dto.CreateBookDto;
import ru.clevertec.cache.dto.UpdateBookDto;
import ru.clevertec.cache.external.service.LibraryService;
import ru.clevertec.cache.models.Book;
import ru.clevertec.cache.services.publushers.MessagePublisher;
import ru.clevertec.cache.utils.BookMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final LibraryService service;
    private final BookMapper mapper;
    private final MessagePublisher publisher;

    @Override
    public List<BookDto> getAllBooks() {
        return mapper.convertAllToDtos(service.getAllBooks());
    }

    @Override
    @Cacheable("books")
    public BookDto getBookById(long id) {
        publisher.publish("Operation GET");
        Book book = service.getBookById(id);
        return mapper.convertToDto(book);
    }

    @Override
    @CachePut(value = "books", key = "#result.id")
    public BookDto addBook(@RequestBody CreateBookDto book) {
        publisher.publish("Operation ADD");
        Book createdBook = service.addBook(book);
        return mapper.convertToDto(createdBook);
    }

    @Override
    @CachePut(value = "books", key = "#result.id")
    public BookDto updateBook(long id, UpdateBookDto dto) {
        publisher.publish("Operation UPDATE");
        Book updatedBook = service.updateBook(id, dto);
        return mapper.convertToDto(updatedBook);
    }

    @Override
    @CacheEvict(value = "books")
    public void deleteBookById(long id) {
        publisher.publish("Operation DELETE");
        service.deleteBookById(id);
    }
}
