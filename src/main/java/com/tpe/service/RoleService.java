package com.tpe.service;


import com.tpe.entity.concretes.business.Role;
import com.tpe.entity.enums.RoleType;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.payload.messages.ErrorMessages;
import com.tpe.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRole(RoleType roleType){
        return roleRepository.findByEnumRoleEquals(roleType).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessages.ROLE_NOT_FOUND));
    }

    public List<Role> getAllUserRole(){
        return roleRepository.findAll();
    }
}