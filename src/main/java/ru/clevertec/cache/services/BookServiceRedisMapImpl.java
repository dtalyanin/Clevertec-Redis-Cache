package ru.clevertec.cache.services;

import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.clevertec.cache.dto.BookDto;
import ru.clevertec.cache.dto.CreateBookDto;
import ru.clevertec.cache.dto.UpdateBookDto;
import ru.clevertec.cache.external.service.LibraryService;
import ru.clevertec.cache.models.Book;
import ru.clevertec.cache.utils.BookMapper;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Profile("rmap")
@Primary
public class BookServiceRedisMapImpl implements BookService {

    private final LibraryService service;
    private final BookMapper mapper;
    private final RMapCache<Long, BookDto> redisMap;
    private final long ttl;
    private final long idle;

    @Autowired
    public BookServiceRedisMapImpl(LibraryService service,
                                   BookMapper mapper,
                                   RedissonClient redissonClient) {
        this.service = service;
        this.mapper = mapper;
        this.redisMap = redissonClient.getMapCache("books");
        this.ttl = 10 * 60 * 1000;
        this.idle = 5 * 60 * 1000;
    }

    @Override
    public List<BookDto> getAllBooks() {
        return mapper.convertAllToDtos(service.getAllBooks());
    }

    @Override
    public BookDto getBookById(long id) {
        BookDto dto = redisMap.get(id);
        if (dto == null) {
            System.out.println("get");
            Book book = service.getBookById(id);
            dto = mapper.convertToDto(book);
            redisMap.fastPut(id, dto, ttl, TimeUnit.MILLISECONDS, idle, TimeUnit.MILLISECONDS);
        }
        return dto;
    }

    @Override
    public BookDto addBook(@RequestBody CreateBookDto book) {
        System.out.println("add");
        Book createdBook = service.addBook(book);
        BookDto dto = mapper.convertToDto(createdBook);
        redisMap.fastPut(createdBook.getId(), dto, ttl, TimeUnit.MILLISECONDS, idle, TimeUnit.MILLISECONDS);
        return dto;
    }

    @Override
    public BookDto updateBook(long id, UpdateBookDto dto) {
        System.out.println("update");
        Book updatedBook = service.updateBook(id, dto);
        BookDto updatedDto = mapper.convertToDto(updatedBook);
        redisMap.fastPut(id, updatedDto, ttl, TimeUnit.MILLISECONDS, idle, TimeUnit.MILLISECONDS);
        return updatedDto;
    }

    @Override
    public void deleteBookById(long id) {
        System.out.println("delete");
        service.deleteBookById(id);
        redisMap.fastRemove(id);
    }
}