package ru.clevertec.cache.utils;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.cache.dto.BookDto;
import ru.clevertec.cache.models.Book;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface BookMapper {

    BookDto convertToDto(Book book);
    List<BookDto> convertAllToDtos(List<Book> books);
}
