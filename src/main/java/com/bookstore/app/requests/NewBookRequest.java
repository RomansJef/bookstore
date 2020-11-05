package com.bookstore.app.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Request saving new book")
public class NewBookRequest {
  @ApiModelProperty(notes = "Name of the Book")
  @NotNull
  private String name;

  @ApiModelProperty(notes = "Book Type")
  @NotBlank
  private String type;

  @ApiModelProperty(notes = "Price")
  @NotNull
  private Float price;

  @ApiModelProperty(notes = "Author")
  private String author;

  @ApiModelProperty(notes = "Year")
  @NotBlank
  private Integer year;

  private Long id;
}
