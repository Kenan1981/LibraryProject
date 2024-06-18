package com.tpe.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CategoryRequest {


    @NotNull(message = "Name must not be empty")
    @Size(min = 2, max = 80, message = "Name '${validatedValue}' must be between {min} and {max} chars")
    private String categoryName;


}
