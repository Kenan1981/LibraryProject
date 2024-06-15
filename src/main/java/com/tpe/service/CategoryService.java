package com.tpe.service;

import com.tpe.entity.concretes.business.Category;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.payload.mappers.CategoryMapper;
import com.tpe.payload.messages.ErrorMessages;
import com.tpe.payload.response.CategoryResponse;
import com.tpe.payload.response.ResponseMessage;
import com.tpe.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Page<CategoryResponse> getAll(int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());
        if(Objects.equals(type, "desc")){
            pageable = PageRequest.of(page,size, Sort.by(sort).descending());
        }
        return categoryRepository.findAll(pageable).map(categoryMapper::mapCategoryToCategoryResponse);
    }


    public CategoryResponse getById(Long id) {
        Category category = isCategoryExist(id);
        return categoryMapper.mapCategoryToCategoryResponse(category);
    }

    private Category isCategoryExist(Long id){
        return categoryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_CATEGORY_MESSAGE,id)));
    }
}
