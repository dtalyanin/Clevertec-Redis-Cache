package ru.clevertec.cache.external.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.clevertec.cache.exceptions.LibraryException;
import ru.clevertec.cache.models.responses.ErrorResponse;

@Component
@RequiredArgsConstructor
public class LibraryErrorDecorator implements ErrorDecoder {

    private final ObjectMapper mapper;

    @SneakyThrows
    @Override
    public Exception decode(String methodKey, Response response) {
        ErrorResponse errorResponse = mapper.readValue(response.body().asInputStream(), ErrorResponse.class);
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        return new LibraryException(errorResponse.errorMessage(), responseStatus);
    }
}