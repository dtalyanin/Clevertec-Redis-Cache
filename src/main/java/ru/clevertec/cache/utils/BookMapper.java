package ru.clevertec.cache.utils;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import ru.clevertec.cache.dto.BookDto;
import ru.clevertec.cache.models.Book;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface BookMapper {

    public BookDto convertToDto(Book book);
    public List<BookDto> convertAllToDtos(List<Book> books);
}
