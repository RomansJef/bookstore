package com.bookstore.app.service;

import com.bookstore.app.requests.GetBookByIdRequest;
import com.bookstore.app.BookEntity;
import com.bookstore.app.BookstoreRepository;
import com.bookstore.app.response.GetBookByIdResponse;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GetBookByIdServiceTest {

    private BookstoreRepository bookstoreRepository = mock(BookstoreRepository.class);
    private GetBookByIdService getBookByIdService =
            new GetBookByIdService(bookstoreRepository);
    private GetBookByIdRequest getBookByIdRequest = new GetBookByIdRequest();
    private GetBookByIdResponse getBookByIdResponse = new GetBookByIdResponse();
    private BookEntity bookEntity = new BookEntity();

    @Before
    public void setUp() {

        // request
        getBookByIdRequest = GetBookByIdRequest.builder().id(1L).build();

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
        getBookByIdResponse =
                GetBookByIdResponse.builder()
                        .id(1L)
                        .name("WAR AND PEACE")
                        .type("Roman")
                        .price(10.1F)
                        .author("Lev Tolstoy")
                        .year(2019)
                        .build();
    }

    @Test
    public void whenGetBookByIdSuccess() {
        when(bookstoreRepository.getBookById(getBookByIdRequest.getId()))
                .thenReturn(Optional.of(bookEntity));
        GetBookByIdResponse actual = getBookByIdService.executeRequest(getBookByIdRequest);
        verify(bookstoreRepository, times(1)).getBookById(getBookByIdRequest.getId());
        assertThat(actual).isEqualTo(getBookByIdResponse);
    }

    @Test
    public void whenGetBookByIdError() {
        when(bookstoreRepository.getBookById(5L))
                .thenReturn(Optional.of(bookEntity));
        assertThrows(RuntimeException.class,
                () -> getBookByIdService.executeRequest(getBookByIdRequest));
    }
}
