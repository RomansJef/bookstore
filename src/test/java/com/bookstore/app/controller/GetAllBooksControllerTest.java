package com.bookstore.app.controller;

import com.bookstore.app.BookEntity;
import com.bookstore.app.BookstoreController;
import com.bookstore.app.BookstoreRepository;
import com.bookstore.app.response.GetAllBooksResponse;
import com.bookstore.app.service.*;
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
public class GetAllBooksControllerTest {

    private BookstoreRepository bookstoreRepository = mock(BookstoreRepository.class);

    private NewBookService newBookService = mock(NewBookService.class);
    private GetAllBooksService getAllBooksService = mock(GetAllBooksService.class);
    private GetBooksByTypeService getBooksByTypeService = mock(GetBooksByTypeService.class);
    private GetBookByIdService getBookByIdService = mock(GetBookByIdService.class);
    private DeleteBookByIdService deleteBookByIdService = mock(DeleteBookByIdService.class);
    private UpdateBookService updateBookService = mock(UpdateBookService.class);

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
    }

    @Test
    public void whenGetAllBooksSuccess() throws Exception {
        List<BookEntity> bookEntityList = new ArrayList<>();
        bookEntityList.add(bookEntity);
        GetAllBooksResponse allBooksResponse =
                GetAllBooksResponse.builder().booksList(bookEntityList).build();

        when(getAllBooksService.executeRequest()).thenReturn(allBooksResponse);

        // then
        mockMvc
                .perform(
                        get("/")
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.booksList").exists())
                .andExpect(jsonPath("$.booksList[0].id").exists())
                .andExpect(jsonPath("$.booksList[0].type").exists())
                .andDo(print());
    }
}
