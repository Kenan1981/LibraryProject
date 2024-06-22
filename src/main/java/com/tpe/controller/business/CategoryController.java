package com.tpe.controller.business;

import com.tpe.payload.request.CategoryRequest;
import com.tpe.payload.response.CategoryResponse;
import com.tpe.payload.response.ResponseMessage;
import com.tpe.service.business.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping // http://localhost:8080/categories + GET
    public Page<CategoryResponse> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ){
        return categoryService.getAll(page,size,sort,type);
    }


    @GetMapping("/{id}") // http://localhost:8080/categories/1 + GET
    public CategoryResponse getById(@PathVariable Long id){
        return categoryService.getById(id);

    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')") // http://localhost:8080/categories + POST
    public ResponseMessage<CategoryResponse> save(@RequestBody @Valid
                                                  CategoryRequest categoryRequest){
        return categoryService.saveCategory(categoryRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')") // http://localhost:8080/categories/1 + PUT
    public ResponseMessage<CategoryResponse> update(@PathVariable Long id,
                                                    @RequestBody @Valid CategoryRequest categoryRequest){
        return categoryService.updateCategory(id, categoryRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')") // http://localhost:8080/categories/1 + DELETE
    public ResponseMessage<?> deleteById(@PathVariable Long id){
        return categoryService.deleteById(id);
    }








}
