package com.bookstore.app.service;

import com.bookstore.app.BookEntity;
import com.bookstore.app.BookstoreRepository;
import com.bookstore.app.requests.GetBooksByTypeRequest;
import com.bookstore.app.response.GetBooksByTypeResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetBooksByTypeServiceTest {

    private BookstoreRepository bookstoreRepository = mock(BookstoreRepository.class);
    private GetBooksByTypeService getBooksByTypeService =
            new GetBooksByTypeService(bookstoreRepository);
    private GetBooksByTypeRequest getBooksByTypeRequest = new GetBooksByTypeRequest();
    private GetBooksByTypeResponse getBooksByTypeResponse = new GetBooksByTypeResponse();
    private BookEntity bookEntity = new BookEntity();
    private List<BookEntity> getBooksByTypeResponseList = new ArrayList<>();

    @Before
    public void setUp() {

        // request
        getBooksByTypeRequest = GetBooksByTypeRequest.builder().type("Roman").build();

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
        getBooksByTypeResponseList.add(bookEntity);
        getBooksByTypeResponse =
                GetBooksByTypeResponse.builder().booksList(getBooksByTypeResponseList).build();
    }

    @Test
    public void whenDeleteNewReportSuccess() {
        when(bookstoreRepository.getBooksByType(getBooksByTypeRequest.getType()))
                .thenReturn(Optional.of(getBooksByTypeResponseList));
        GetBooksByTypeResponse actual =
                getBooksByTypeService.executeRequest(getBooksByTypeRequest);
        verify(bookstoreRepository, times(1)).getBooksByType(getBooksByTypeRequest.getType());
        assertThat(actual.getBooksList()).isEqualTo(getBooksByTypeResponseList);
    }

    @Test
    public void whenGetBooksByTypeError() {
        when(bookstoreRepository.getBooksByType("Crime"))
                .thenReturn(Optional.of(getBooksByTypeResponseList));
        assertThrows(RuntimeException.class,
                () -> getBooksByTypeService.executeRequest(getBooksByTypeRequest));
    }
}
