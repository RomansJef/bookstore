package com.bookstore.app.controller;

import com.bookstore.app.BookstoreRepository;
import com.bookstore.app.requests.DeleteBookByIdRequest;
import com.bookstore.app.response.DeleteBookByIdResponse;
import com.bookstore.app.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookstore.app.BookstoreController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class DeleteBookControllerTest {

    private BookstoreRepository bookstoreRepository = mock(BookstoreRepository.class);

    private NewBookService newBookService = mock(NewBookService.class);
    private GetAllBooksService getAllBooksService = mock(GetAllBooksService.class);
    private GetBooksByTypeService getBooksByTypeService = mock(GetBooksByTypeService.class);
    private GetBookByIdService getBookByIdService = mock(GetBookByIdService.class);
    private DeleteBookByIdService deleteBookByIdService = mock(DeleteBookByIdService.class);
    private UpdateBookService updateBookService = mock(UpdateBookService.class);

    private DeleteBookByIdRequest deleteBookRequest = mock(DeleteBookByIdRequest.class);
    private DeleteBookByIdResponse deleteBookResponse = new DeleteBookByIdResponse();
    private BookstoreController bookstoreController =
            new BookstoreController(
                    newBookService,
                    getAllBooksService,
                    getBooksByTypeService,
                    getBookByIdService,
                    deleteBookByIdService,
                    updateBookService);
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new Exception(), bookstoreController).build();

        // request
        deleteBookRequest =
                DeleteBookByIdRequest.builder()
                        .id(1L)
                        .build();

        // response
        deleteBookResponse.setStatus("Book is deleted");
    }

    @Test
    public void whenDeleteBookSuccess() throws Exception {
        when(deleteBookByIdService.executeRequest(deleteBookRequest)).thenReturn(deleteBookResponse);
        ObjectMapper mapper = new ObjectMapper();

        mockMvc
                .perform(
                        post("/deletebook")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(deleteBookRequest)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.status").exists())
                .andDo(print());
    }

    @Test
    public void whenDeleteBookBadRequest() throws Exception {
        mockMvc
                .perform(
                        post("/deletebook")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("null"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}
