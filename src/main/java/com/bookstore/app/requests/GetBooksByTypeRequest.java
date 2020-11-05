package com.bookstore.app.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Request getting books by type")
public class GetBooksByTypeRequest {

    @ApiModelProperty(notes = "Type")
    @NotBlank
    private String type;
}
