package com.bookstore.app.controller;

import com.bookstore.app.requests.GetBookByIdRequest;
import com.bookstore.app.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookstore.app.BookEntity;
import com.bookstore.app.BookstoreController;
import com.bookstore.app.BookstoreRepository;
import com.bookstore.app.response.GetBookByIdResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class GetBookByIdControllerTest {

    private BookstoreRepository bookstoreRepository = mock(BookstoreRepository.class);

    private NewBookService newBookService = mock(NewBookService.class);
    private GetAllBooksService getAllBooksService = mock(GetAllBooksService.class);
    private GetBooksByTypeService getBooksByTypeService = mock(GetBooksByTypeService.class);
    private GetBookByIdService getBookByIdService = mock(GetBookByIdService.class);
    private DeleteBookByIdService deleteBookByIdService = mock(DeleteBookByIdService.class);
    private UpdateBookService updateBookService = mock(UpdateBookService.class);

    private GetBookByIdResponse getBookByIdResponse = new GetBookByIdResponse();
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
    public void whenGetBookByIdSuccess() throws Exception {
        GetBookByIdRequest getBookByIdRequest = GetBookByIdRequest.builder().id(1L).build();

        // when
        when(getBookByIdService.executeRequest(getBookByIdRequest))
                .thenReturn(getBookByIdResponse);
        ObjectMapper mapper = new ObjectMapper();

        // then
        mockMvc
                .perform(
                        get("/bookbyid")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(getBookByIdRequest)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.type").exists())
                .andDo(print());
    }

    @Test
    public void whenGetBookByIdNullRequest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        mockMvc
                .perform(
                        get("/bookbyid")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(null)))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}
