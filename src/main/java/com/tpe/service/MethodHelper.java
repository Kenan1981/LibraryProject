package com.tpe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@RequiredArgsConstructor
public class MethodHelper {

    public Pageable getPageableWithProperties(int page, int size, String sort, String type)
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


}