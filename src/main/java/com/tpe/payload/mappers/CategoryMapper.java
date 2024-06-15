package com.tpe.payload.mappers;

import com.tpe.entity.concretes.business.Category;
import com.tpe.payload.response.CategoryResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CategoryMapper {

    public CategoryResponse mapCategoryToCategoryResponse(Category category){

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }



}
