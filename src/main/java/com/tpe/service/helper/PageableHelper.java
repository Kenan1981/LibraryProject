package com.tpe.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PageableHelper {

    public Pageable getPageableWithProperties(int page, int size, String sort, String type)
    {
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());
        if(Objects.equals(type,"desc")){
            pageable = PageRequest.of(page,size, Sort.by(sort).descending());
        }
        return pageable ;
    }

    public Pageable getPageableWithProperties(String q, Long catId, Long authorId, Long publisherID, int page,
                                              int size, String sort, String type)
    {
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());
        if(Objects.equals(type,"desc")){
            pageable = PageRequest.of(page,size, Sort.by(sort).descending());
        }
        return pageable ;
    }

    public Pageable getPageableWithProperties(int page, int size)
    {
        return PageRequest.of(page,size,Sort.by("id").descending());
    }



    // BÜSRA
    public Pageable getPageableWithPropertiesASC(int page, int size, String sort, String type)
    {
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());
        if(Objects.equals(type,"asc")){
            pageable = PageRequest.of(page,size, Sort.by(sort).ascending());
        }
        return pageable ;
    }





}