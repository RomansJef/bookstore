package com.bookstore.app.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Response getting book by Id")
public class GetBookByIdResponse {

    @ApiModelProperty(notes = "Id")
    Long id;
    @ApiModelProperty(notes = "Name")
    String name;
    @ApiModelProperty(notes = "Type")
    String type;
    @ApiModelProperty(notes = "Price")
    Float price;
    @ApiModelProperty(notes = "Volume")
    String author;
    @ApiModelProperty(notes = "Date")
    Integer year;
}
