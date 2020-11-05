package com.bookstore.app.service;

import com.bookstore.app.BookEntity;
import com.bookstore.app.BookstoreRepository;
import com.bookstore.app.response.UpdateBookResponse;
import com.bookstore.app.requests.UpdateBookRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UpdateBookServiceTest {

  private BookstoreRepository bookstoreRepository = mock(BookstoreRepository.class);
  private UpdateBookService updateBookService = new UpdateBookService(bookstoreRepository);
  private UpdateBookRequest updateBookRequest = new UpdateBookRequest();
  private UpdateBookResponse updateBookResponse = new UpdateBookResponse();
  private BookEntity bookEntity = new BookEntity();

  @Before
  public void setUp() {

    // request
    updateBookRequest =
        UpdateBookRequest.builder()
                .id(1L)
                .price(20.1F)
                .build();

    // response
    updateBookResponse = UpdateBookResponse.builder().status("Book nr.1 updated").build();

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
  }

  @Test
  public void whenUpdateBookSuccess() {
    when(bookstoreRepository.getBookById(updateBookRequest.getId()))
        .thenReturn(Optional.of(bookEntity));
    UpdateBookResponse actual = updateBookService.executeRequest(updateBookRequest);
    verify(bookstoreRepository, times(1)).saveBook(bookEntity);
    assertThat(actual).isEqualTo(updateBookResponse);
  }
}
