package com.example.cloudwrite.model.security;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class Authority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String role;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;
}
