package com.bookstore.app.service;

import com.bookstore.app.BookstoreRepository;
import com.bookstore.app.response.UpdateBookResponse;
import com.bookstore.app.requests.UpdateBookRequest;
import com.bookstore.app.BookEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/** Update Book Service. */
@Log4j2
@Service
public class UpdateBookService {

  private BookstoreRepository bookstoreRepository;

  public UpdateBookService(BookstoreRepository bookstoreRepository) {
    this.bookstoreRepository = bookstoreRepository;
  }

  private UpdateBookResponse response = new UpdateBookResponse();

  public UpdateBookResponse executeRequest(UpdateBookRequest updateBookRequest) {
    Long id = updateBookRequest.getId();
    Optional<BookEntity> bookEntityById = bookstoreRepository.getBookById(id);

    if (!Objects.isNull(updateBookRequest.getPrice())) {
      bookEntityById.get().setPrice(updateBookRequest.getPrice());
    }

    bookstoreRepository.saveBook(bookEntityById.get());
    response.setStatus("Book nr." + id + " updated");
    return response;
  }
}
