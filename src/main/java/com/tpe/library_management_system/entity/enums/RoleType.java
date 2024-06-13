package com.tpe.library_management_system.entity.enums;

import lombok.Getter;

@Getter
public enum RoleType
{
    ADMIN("Admin"),
    STAFF("Staff"),
    MEMBER("Member");

    private final String name;

    RoleType(String name) {
        this.name = name;
    }

}
