package ru.clevertec.cache.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.cache.dto.BookDto;
import ru.clevertec.cache.dto.EditBookDto;
import ru.clevertec.cache.exceptions.ErrorCode;
import ru.clevertec.cache.exceptions.NotFoundException;
import ru.clevertec.cache.external.BookServiceExt;
import ru.clevertec.cache.models.Book;
import ru.clevertec.cache.utils.BookMapper;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.cache.utils.ErrorMessage.BOOK_WITH_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookServiceExt serviceExt;
    private final BookMapper mapper;

    @GetMapping
    public List<BookDto> getAllBooks() {
        return mapper.convertAllToDtos(serviceExt.getAllBooks());
    }

    @Cacheable("books")
    public BookDto getBookById( long id) {
        System.out.println(1);
//        Book book = getBookByIdOrElseThrowException(id);
        Book book1 = new Book();
        book1.setId(id);
        book1.setTitle("aaaaaa");
        book1.setAuthor("bbbbbb");
        return mapper.convertToDto(book1);
    }

    @CachePut(value = "books", key = "#result.id")
    public void addBook(@RequestBody Book book) {

    }

    @CachePut(value = "books", key = "#result.id")
    public void editBook(long id, EditBookDto dto) {

    }

    @CacheEvict(value = "books")
    public void deleteBook(long id) {

    }

    private Book getBookByIdOrElseThrowException(long id) {
        Optional<Book> book = Optional.of(serviceExt.getBookById(id));
        if (book.isEmpty()) {
            throw new NotFoundException(BOOK_WITH_ID_NOT_FOUND, id, ErrorCode.NOT_FOUND);
        }
        return book.get();
    }
}
