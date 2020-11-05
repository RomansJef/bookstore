package com.bookstore.app.response;

import com.bookstore.app.BookEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Response getting all books")
public class GetAllBooksResponse {

  @ApiModelProperty(notes = "List of books")
  private List<BookEntity> booksList;
}
