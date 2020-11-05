package com.bookstore.app.service;

import com.bookstore.app.BookEntity;
import com.bookstore.app.BookstoreRepository;
import com.bookstore.app.requests.NewBookRequest;
import com.bookstore.app.response.NewBookResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service to store new Book.
 */
@Log4j2
@Service
public class NewBookService {

    private BookstoreRepository bookstoreRepository;

    public NewBookService(BookstoreRepository bookstoreRepository) {
        this.bookstoreRepository = bookstoreRepository;
    }

    private NewBookResponse print_status = new NewBookResponse();

    public NewBookResponse executeRequest(List<NewBookRequest> fuelReportRequestList) {

        List<String> statusList = new ArrayList<>();
        List<BookEntity> bookEntities = bookstoreRepository.retrieveBooks();
        for (NewBookRequest newBookRequest : fuelReportRequestList) {
            if (validateBook(bookEntities, newBookRequest)) {
                BookEntity newBookEntity =
                        BookEntity.builder()
                                .id(newBookRequest.getId())
                                .name(newBookRequest.getName())
                                .type(newBookRequest.getType())
                                .price(newBookRequest.getPrice())
                                .author(newBookRequest.getAuthor())
                                .year(newBookRequest.getYear())
                                .build();
                bookstoreRepository.saveBook(newBookEntity);
                statusList.add("New book " + newBookRequest.getName() + " saved into DB");
            }
        }

        print_status.setSaveStatus(statusList);
        return print_status;
    }

    public boolean validateBook(List<BookEntity> bookEntities, NewBookRequest newBookRequest) {
        for (BookEntity bookEntity : bookEntities) {
            if (newBookRequest.getName().equals(bookEntity.getName())) return false;

        }
        return true;
    }
}