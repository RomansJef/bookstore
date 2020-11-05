package com.bookstore.app.service;

import com.bookstore.app.requests.GetBookByIdRequest;
import com.bookstore.app.BookEntity;
import com.bookstore.app.BookstoreRepository;
import com.bookstore.app.response.GetBookByIdResponse;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Find Book by Id Service.
 */
@Log4j2
@Service
public class GetBookByIdService {

    private BookstoreRepository bookstoreRepository;

    public GetBookByIdService(BookstoreRepository bookstoreRepository) {
        this.bookstoreRepository = bookstoreRepository;
    }

    private GetBookByIdResponse response = new GetBookByIdResponse();

    public GetBookByIdResponse executeRequest(GetBookByIdRequest getBookByIdRequest) {
        Long id = getBookByIdRequest.getId();
        BookEntity bookEntityById = bookstoreRepository.getBookById(id).orElseThrow(
                () -> new RuntimeException("e51833ec-f38c-4a17-9fcf-004bb5d15c6b - Find by Id error"));

        response =
                GetBookByIdResponse.builder()
                        .id(bookEntityById.getId())
                        .name(bookEntityById.getName())
                        .type(bookEntityById.getType())
                        .price(bookEntityById.getPrice())
                        .author(bookEntityById.getAuthor())
                        .year(bookEntityById.getYear())
                        .build();
        return response;
    }
}
