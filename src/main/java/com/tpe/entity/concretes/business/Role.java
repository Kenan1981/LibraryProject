package com.tpe.entity.concretes.business;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ElementCollection
    private List<String> permissions;

    public Role() {}

    public Role(String name, List<String> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permissions=" + permissions +
                '}';
    }

    public static Role createRole(Long id, String name) {
        List<String> permissions;
        switch (name.toLowerCase()) {
            case "admin":
                permissions = Arrays.asList("register", "login", "search_book", "access_book_details",
                        "update_profile", "view_borrowed_books", "add_book", "remove_book",
                        "update_book", "lend_book", "add_user", "update_user", "delete_user");
                break;
            case "employee":
                permissions = Arrays.asList("register", "login", "search_book", "access_book_details",
                        "update_profile", "view_borrowed_books", "lend_book", "add_user",
                        "update_user", "delete_user");
                break;
            case "member":
                permissions = Arrays.asList("register", "login", "search_book", "access_book_details",
                        "update_profile", "view_borrowed_books");
                break;
            default:
                throw new IllegalArgumentException("Invalid role name");
        }
        return new Role(name, permissions);
    }
}


