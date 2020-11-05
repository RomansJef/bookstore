package com.bookstore.app.controller;

import com.bookstore.app.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookstore.app.BookstoreController;
import com.bookstore.app.BookstoreRepository;
import com.bookstore.app.requests.UpdateBookRequest;
import com.bookstore.app.response.UpdateBookResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UpdateBookControllerTest {

  private BookstoreRepository bookstoreRepository = mock(BookstoreRepository.class);

  private NewBookService newBookService = mock(NewBookService.class);
  private GetAllBooksService getAllBooksService = mock(GetAllBooksService.class);
  private GetBooksByTypeService getBooksByTypeService = mock(GetBooksByTypeService.class);
  private GetBookByIdService getBookByIdService = mock(GetBookByIdService.class);
  private DeleteBookByIdService deleteBookByIdService = mock(DeleteBookByIdService.class);
  private UpdateBookService updateBookService = mock(UpdateBookService.class);

  private UpdateBookRequest updateBookRequest = mock(UpdateBookRequest.class);
  private UpdateBookResponse updateBookResponse = new UpdateBookResponse();
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
    updateBookRequest =
        UpdateBookRequest.builder()
                .id(1L)
                .price(20.1F)
                .build();

    // response
    updateBookResponse.setStatus("Book is updated");
  }

  @Test
  public void whenUpdateBookSuccess() throws Exception {
    when(updateBookService.executeRequest(updateBookRequest)).thenReturn(updateBookResponse);
    ObjectMapper mapper = new ObjectMapper();

    mockMvc
        .perform(
            patch("/updatebook")
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateBookRequest)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.status").exists())
        .andDo(print());
  }

  @Test
  public void whenUpdateBookBadRequest() throws Exception {
    mockMvc
        .perform(
            patch("/updatebook")
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("null"))
        .andExpect(status().is4xxClientError())
        .andDo(print());
  }
}
