package ru.clevertec.cache.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.data.redis.support.collections.RedisMap;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.clevertec.cache.dto.BookDto;
import ru.clevertec.cache.dto.CreateBookDto;
import ru.clevertec.cache.dto.UpdateBookDto;
import ru.clevertec.cache.external.service.LibraryService;
import ru.clevertec.cache.models.Book;
import ru.clevertec.cache.utils.BookMapper;

import java.util.List;

@Service
@Profile("map")
@Primary
public class BookServiceRedisMapImpl implements BookService {

    private final LibraryService service;
    private final BookMapper mapper;
    private final RedisMap<Long, BookDto> redisMap;

    @Autowired
    public BookServiceRedisMapImpl(LibraryService service,
                                   BookMapper mapper,
                                   RedisTemplate<String, Object> redisTemplate) {
        this.service = service;
        this.mapper = mapper;
        this.redisMap = new DefaultRedisMap<>("books", redisTemplate);
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
            redisMap.put(id, dto);
        }
        return dto;
    }

    @Override
    public BookDto addBook(@RequestBody CreateBookDto book) {
        System.out.println("add");
        Book createdBook = service.addBook(book);
        BookDto dto = mapper.convertToDto(createdBook);
        redisMap.put(createdBook.getId(), dto);
        return dto;
    }

    @Override
    public BookDto updateBook(long id, UpdateBookDto dto) {
        System.out.println("update");
        Book updatedBook = service.updateBook(id, dto);
        BookDto updatedDto = mapper.convertToDto(updatedBook);
        redisMap.put(id, updatedDto);
        return updatedDto;
    }

    @Override
    public void deleteBookById(long id) {
        System.out.println("delete");
        service.deleteBookById(id);
        redisMap.remove(id);
    }
}