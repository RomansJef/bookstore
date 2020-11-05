package com.bookstore.app.service;

import com.bookstore.app.BookEntity;
import com.bookstore.app.BookstoreRepository;
import com.bookstore.app.requests.DeleteBookByIdRequest;
import com.bookstore.app.response.DeleteBookByIdResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DeleteBookByIdServiceTest {

  private BookstoreRepository bookstoreRepository = mock(BookstoreRepository.class);
  private DeleteBookByIdService deleteBookByIdService =
      new DeleteBookByIdService(bookstoreRepository);
  private DeleteBookByIdRequest deleteBookByIdRequest = new DeleteBookByIdRequest();
  private DeleteBookByIdResponse deleteBookByIdResponse = new DeleteBookByIdResponse();
  private BookEntity bookEntity = new BookEntity();

  @Before
  public void setUp() {

    // request
    deleteBookByIdRequest = DeleteBookByIdRequest.builder().id(1L).build();

    // entity
    bookEntity =
        BookEntity.builder()
                .id(1L)
                .name("WAR AND PEACE")
                .type("Roman")
                .price(10.1F)
                .author("Lev Tolstoy")
                .year(2019)
                .build();

    // response
    deleteBookByIdResponse =
        DeleteBookByIdResponse.builder().status("Report nr.1 deleted").build();
  }

  @Test
  public void whenDeleteBookSuccess() {
    when(bookstoreRepository.getBookById(deleteBookByIdRequest.getId()))
        .thenReturn(Optional.of(bookEntity));
    DeleteBookByIdResponse actual =
        deleteBookByIdService.executeRequest(deleteBookByIdRequest);
    verify(bookstoreRepository, times(1)).deleteBook(bookEntity.getId());
    assertThat(actual).isEqualTo(deleteBookByIdResponse);
  }
}
