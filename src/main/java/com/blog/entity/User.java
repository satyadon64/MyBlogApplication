package com.blog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;
@Entity
@Data
@Table(name="users",uniqueConstraints = @UniqueConstraint(columnNames = {"username","email"}))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String username;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="user_role", joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="roles_is",referencedColumnName = "id"))
    Set<Role> roles;
}
