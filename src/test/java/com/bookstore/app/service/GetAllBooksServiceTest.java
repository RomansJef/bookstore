package com.bookstore.app.service;

import com.bookstore.app.BookEntity;
import com.bookstore.app.BookstoreRepository;
import com.bookstore.app.response.GetAllBooksResponse;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

public class GetAllBooksServiceTest {
  private BookstoreRepository bookstoreRepository = mock(BookstoreRepository.class);
  private GetAllBooksService getAllBooksService =
      new GetAllBooksService(bookstoreRepository);
  private GetAllBooksResponse getAllBooksResponse = new GetAllBooksResponse();
  private BookEntity bookEntity = new BookEntity();

  @Before
  public void setUp() {

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
  public void whenGetAllBooksSuccess() {
    List<BookEntity> bookEntityList = new ArrayList<>();
    bookEntityList.add(bookEntity);

    when(bookstoreRepository.retrieveBooks()).thenReturn(bookEntityList);
    GetAllBooksResponse actual = getAllBooksService.executeRequest();
    verify(bookstoreRepository, times(1)).retrieveBooks();
    Assertions.assertThat(actual.getBooksList()).isEqualTo(bookEntityList);
  }
}
