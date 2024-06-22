package com.tpe.service.business;

import com.tpe.entity.concretes.business.Category;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.payload.mappers.CategoryMapper;
import com.tpe.payload.messages.ErrorMessages;
import com.tpe.payload.messages.SuccessMessages;
import com.tpe.payload.request.CategoryRequest;
import com.tpe.payload.response.CategoryResponse;
import com.tpe.payload.response.ResponseMessage;
import com.tpe.repository.BookRepository;
import com.tpe.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final BookRepository bookRepository;

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

    //yrd method
    private Category isCategoryExist(Long id){
        return categoryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_CATEGORY_MESSAGE,id)));
    }

    public ResponseMessage<CategoryResponse> saveCategory(CategoryRequest categoryRequest) {

        isCategoryExistByCategoryName(categoryRequest.getCategoryName());

        Category savedCategory = categoryRepository.save(categoryMapper.mapCategoryRequestToCategory(categoryRequest));

        return ResponseMessage.<CategoryResponse>builder()
                .object(categoryMapper.mapCategoryToCategoryResponse(savedCategory))
                .message(SuccessMessages.CATEGORY_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    //yrd method
    private boolean isCategoryExistByCategoryName(String categoryName){
        boolean lessonExist =  categoryRepository.existsByNameIgnoreCase(categoryName);

        if(lessonExist){
            throw new ConflictException(String.format(ErrorMessages.CATEGORY_ALREADY_EXIST_WITH_CATEGORY_NAME, categoryName));
        }else return false;
    }

    public ResponseMessage<CategoryResponse> updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        Category category = isCategoryExist(categoryId);
        if(
                !(category.getName().equals(categoryRequest.getCategoryName())) &&
                        (categoryRepository.existsByNameIgnoreCase(categoryRequest.getCategoryName()))
        ){
            throw new ConflictException(String.format(ErrorMessages.CATEGORY_ALREADY_EXIST_WITH_CATEGORY_NAME
                    , categoryRequest.getCategoryName()));
        }
        Category updatedCategory = categoryMapper.mapCategoryRequestToUpdatedCategory(categoryId,categoryRequest);
        Category savedCategory = categoryRepository.save(updatedCategory);

        return ResponseMessage.<CategoryResponse>builder()
                .object(categoryMapper.mapCategoryToCategoryResponse(savedCategory))
                .message(SuccessMessages.CATEGORY_UPDATE)
                .httpStatus(HttpStatus.OK)
                .build();
    }


    public ResponseMessage<?> deleteById(Long categoryId) {
        Category category = isCategoryExist(categoryId);
        boolean hasBooks = bookRepository.existsByCategory_Id(categoryId);

        if (hasBooks){
            throw new RuntimeException(String.format(ErrorMessages.CATEGORY_HAS_BOOKS));
        }

        categoryRepository.deleteById(categoryId);

        return ResponseMessage.builder()
                .object(categoryMapper.mapCategoryToCategoryResponse(category))
                .message(SuccessMessages.CATEGORY_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

}
