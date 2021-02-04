package com.grperets.library.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User extends BaseEntity{

    @Column(name = "username")
    @NotEmpty
    private String username;

    @Column(name = "first_name")
    @NotEmpty
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty
    private String lastName;

    @Column(name = "password")
    @NotEmpty
    private String password;

    @Column(name = "email")
    @Email
    @NotEmpty
    private String email;

    @Column(name = "phone")
    @NotEmpty
    private String phone;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    private List<Book> books;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

}
