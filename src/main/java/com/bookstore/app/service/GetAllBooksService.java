package com.bookstore.app.service;

import com.bookstore.app.BookEntity;
import com.bookstore.app.BookstoreRepository;
import com.bookstore.app.response.GetAllBooksResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Return all books stored in DB.
 */
@Log4j2
@Service
public class GetAllBooksService {

    private BookstoreRepository bookstoreRepository;

    public GetAllBooksService(BookstoreRepository bookstoreRepository) {
        this.bookstoreRepository = bookstoreRepository;
    }

    private GetAllBooksResponse response = new GetAllBooksResponse();

    public GetAllBooksResponse executeRequest() {
        List<BookEntity> bookEntities = bookstoreRepository.retrieveBooks();
        return response.builder().booksList(bookEntities).build();
    }
}
