package com.bookstore.app.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Request getting book by ID")
public class GetBookByIdRequest {

    @ApiModelProperty(notes = "Id")
    @NotNull
    private Long id;
}
