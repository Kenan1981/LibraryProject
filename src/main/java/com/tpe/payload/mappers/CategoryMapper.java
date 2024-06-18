package com.tpe.payload.mappers;

import com.tpe.entity.concretes.business.Category;
import com.tpe.payload.request.CategoryRequest;
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

    public Category mapCategoryRequestToCategory(CategoryRequest categoryRequest){

        return Category.builder()
                .name(categoryRequest.getCategoryName())
                .build();
    }

    public Category mapCategoryRequestToUpdatedCategory(Long categoryId,CategoryRequest categoryRequest){

        return Category.builder()
                .id(categoryId)
                .name(categoryRequest.getCategoryName())
                .build();
    }



}
