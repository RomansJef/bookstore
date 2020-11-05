package com.bookstore.app.service;

import com.bookstore.app.BookEntity;
import com.bookstore.app.BookstoreRepository;
import com.bookstore.app.requests.GetBooksByTypeRequest;
import com.bookstore.app.response.GetBooksByTypeResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/** Return Books stored in DB by Type Service. */
@Log4j2
@Service
public class GetBooksByTypeService {

  private BookstoreRepository bookstoreRepository;

  public GetBooksByTypeService(BookstoreRepository bookstoreRepository) {
    this.bookstoreRepository = bookstoreRepository;
  }

  private GetBooksByTypeResponse response = new GetBooksByTypeResponse();

  public GetBooksByTypeResponse executeRequest(GetBooksByTypeRequest getBooksByTypeRequest) {
    String type = getBooksByTypeRequest.getType();
    List<BookEntity> listByType = bookstoreRepository.getBooksByType(type).orElseThrow(
            () -> new RuntimeException("580425d2-b7db-4b56-b244-5fec900a8741 - Find by Type error"));
    return response.builder().booksList(listByType).build();
  }
}
