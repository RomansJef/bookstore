package com.bookstore.app.response;

import com.bookstore.app.BookEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Response getting books by type")
public class GetBooksByTypeResponse {

  @ApiModelProperty(notes = "List of books")
  private List<BookEntity> booksList;
}
