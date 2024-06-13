package com.tpe.entity.enums;

public enum RoleType {

    ADMIN("Admin"),
    MEMBER("Member"),
    EMPLOYEE("Employee");


    public final String name;

    RoleType(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
