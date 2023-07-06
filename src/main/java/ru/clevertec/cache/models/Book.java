package ru.clevertec.cache.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Book {

    private long id;


    private String title;
    private String author;
}
