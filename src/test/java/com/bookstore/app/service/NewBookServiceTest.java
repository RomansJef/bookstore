package com.bookstore.app.service;

import com.bookstore.app.BookEntity;
import com.bookstore.app.BookstoreRepository;
import com.bookstore.app.requests.NewBookRequest;
import com.bookstore.app.response.NewBookResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class NewBookServiceTest {

    private BookstoreRepository bookstoreRepository = mock(BookstoreRepository.class);
    private NewBookService newBookService =
            new NewBookService(bookstoreRepository);
    private NewBookRequest newBookRequest = new NewBookRequest();
    private NewBookResponse newBookResponse = new NewBookResponse();
    private List<String> statusList = new ArrayList<>();
    private BookEntity bookEntity = new BookEntity();
    private BookEntity bookEntity1 = new BookEntity();

    @Before
    public void setUp() {

        // request
        newBookRequest =
                NewBookRequest.builder()
                        .id(1L)
                        .name("WAR AND PEACE")
                        .type("Roman")
                        .price(10.1F)
                        .author("Lev Tolstoy")
                        .year(2019)
                        .build();

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

        bookEntity1 =
                BookEntity.builder()
                        .id(1L)
                        .name("WAR")
                        .type("Roman")
                        .price(10.1F)
                        .author("Lev Tolstoy")
                        .year(2019)
                        .build();

        // response
        statusList.add("New book WAR AND PEACE saved into DB");
        newBookResponse = NewBookResponse.builder().saveStatus(statusList).build();
    }

    @Test
    public void whenSaveNewBookSuccess() {
        List<NewBookRequest> newBookRequests = new ArrayList<>();
        newBookRequests.add(newBookRequest);
        List<BookEntity> bookEntityList = new ArrayList<>();
        bookEntityList.add(bookEntity1);

        when(bookstoreRepository.retrieveBooks()).thenReturn(bookEntityList);
        NewBookResponse actual = newBookService.executeRequest(newBookRequests);
        verify(bookstoreRepository, times(1)).saveBook(bookEntity);
        assertThat(actual).isEqualTo(newBookResponse);
    }
}
