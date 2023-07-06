package ru.clevertec.cache.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateBookDto {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
}