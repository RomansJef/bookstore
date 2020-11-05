package com.bookstore.app.controller;

import com.bookstore.app.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookstore.app.BookEntity;
import com.bookstore.app.BookstoreController;
import com.bookstore.app.BookstoreRepository;
import com.bookstore.app.requests.GetBooksByTypeRequest;
import com.bookstore.app.response.GetBooksByTypeResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class GetBooksByTypeControllerTest {
    private BookstoreRepository bookstoreRepository = mock(BookstoreRepository.class);

    private NewBookService newBookService = mock(NewBookService.class);
    private GetAllBooksService getAllBooksService = mock(GetAllBooksService.class);
    private GetBooksByTypeService getBooksByTypeService = mock(GetBooksByTypeService.class);
    private GetBookByIdService getBookByIdService = mock(GetBookByIdService.class);
    private DeleteBookByIdService deleteBookByIdService = mock(DeleteBookByIdService.class);
    private UpdateBookService updateBookService = mock(UpdateBookService.class);

    private GetBooksByTypeResponse getBooksByTypeResponse = new GetBooksByTypeResponse();
    private BookstoreController bookstoreController =
            new BookstoreController(
                    newBookService,
                    getAllBooksService,
                    getBooksByTypeService,
                    getBookByIdService,
                    deleteBookByIdService,
                    updateBookService);
    private BookEntity bookEntity = new BookEntity();
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new Exception(), bookstoreController).build();

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
        List<BookEntity> bookEntityList = new ArrayList<>();
        bookEntityList.add(bookEntity);
        getBooksByTypeResponse.setBooksList(bookEntityList);
    }

    @Test
    public void whenGetBooksByTypeSuccess() throws Exception {
        GetBooksByTypeRequest getBooksByTypeRequest =
                GetBooksByTypeRequest.builder().type("Roman").build();
        // when
        when(getBooksByTypeService.executeRequest(getBooksByTypeRequest))
                .thenReturn(getBooksByTypeResponse);
        ObjectMapper mapper = new ObjectMapper();

        // then
        mockMvc
                .perform(
                        get("/booksbytype")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(getBooksByTypeRequest)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.booksList").exists())
                .andExpect(jsonPath("$.booksList[0].id").exists())
                .andExpect(jsonPath("$.booksList[0].type").exists())
                .andDo(print());
    }

    @Test
    public void whenGetBooksByTypeEmptyRequest() throws Exception {
        GetBooksByTypeRequest getBooksByTypeRequestEmpty =
                GetBooksByTypeRequest.builder().type("").build();
        ObjectMapper mapper = new ObjectMapper();

        mockMvc
                .perform(
                        get("/booksbytype")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(getBooksByTypeRequestEmpty)))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}
