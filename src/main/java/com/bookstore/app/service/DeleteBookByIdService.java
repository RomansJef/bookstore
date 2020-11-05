package com.bookstore.app.service;

import com.bookstore.app.BookstoreRepository;
import com.bookstore.app.requests.DeleteBookByIdRequest;
import com.bookstore.app.response.DeleteBookByIdResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/** Delete report by Id. */
@Log4j2
@Service
public class DeleteBookByIdService {

  private BookstoreRepository bookstoreRepository;

  public DeleteBookByIdService(BookstoreRepository bookstoreRepository) {
    this.bookstoreRepository = bookstoreRepository;
  }

  private DeleteBookByIdResponse response = new DeleteBookByIdResponse();

  public DeleteBookByIdResponse executeRequest(DeleteBookByIdRequest deleteBookByIdRequest) {
    Long id = deleteBookByIdRequest.getId();
    bookstoreRepository.deleteBook(id);
    response.setStatus("Report nr." + id + " deleted");
    return response;
  }
}
