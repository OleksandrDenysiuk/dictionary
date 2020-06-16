package com.portfolio.dictionary.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "User")
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user")
    private Set<Result> results = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,
                mappedBy = "user")
    private Set<Category> categories;

    public Set<Role> getRoles() {
        if(roles == null){
            this.roles = new HashSet<>();
        }
        return roles;
    }

    public Set<Category> getCategories() {
        if(categories == null){
            this.categories = new HashSet<>();
        }
        return categories;
    }

}
