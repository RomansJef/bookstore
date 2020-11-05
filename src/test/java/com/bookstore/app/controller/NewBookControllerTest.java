package com.bookstore.app.controller;

import com.bookstore.app.BookEntity;
import com.bookstore.app.BookstoreRepository;
import com.bookstore.app.requests.NewBookRequest;
import com.bookstore.app.response.NewBookResponse;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class NewBookControllerTest {
  private BookstoreRepository bookstoreRepository = mock(BookstoreRepository.class);

  private NewBookService newBookService = mock(NewBookService.class);
  private GetAllBooksService getAllBooksService = mock(GetAllBooksService.class);
  private GetBooksByTypeService getBooksByTypeService = mock(GetBooksByTypeService.class);
  private GetBookByIdService getBookByIdService = mock(GetBookByIdService.class);
  private DeleteBookByIdService deleteBookByIdService = mock(DeleteBookByIdService.class);
  private UpdateBookService updateBookService = mock(UpdateBookService.class);

  private NewBookRequest newBookRequest = mock(NewBookRequest.class);
  private NewBookResponse newBookResponse = new NewBookResponse();
  private List<String> statusList = new ArrayList<>();
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

    // response
    statusList.add("Book is saved");
    newBookResponse.setSaveStatus(statusList);
  }

  @Test
  public void whenSaveNewBookSuccess() throws Exception {
    List<NewBookRequest> bookRequestList = new ArrayList<>();
    bookRequestList.add(newBookRequest);
    when(newBookService.executeRequest(bookRequestList)).thenReturn(newBookResponse);
    ObjectMapper mapper = new ObjectMapper();

    mockMvc
        .perform(
            post("/newbook")
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookRequestList)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.saveStatus").exists())
        .andDo(print());
  }

  @Test
  public void whenSaveBookBadRequest() throws Exception {
    mockMvc
        .perform(
            post("/newbook")
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("null"))
        .andExpect(status().is4xxClientError())
        .andDo(print());
  }
}
