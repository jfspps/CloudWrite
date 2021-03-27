package com.example.cloudwrite.model.security;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public boolean isNew() {
        return this.id == null;
    }

    @NotNull
    @Size(min = 1, max = 255)
    private String username;

    @NotNull
    @Size(min = 8, max = 255)
    private String password;

    //Singular (Lombok) builds a singular Set with one Authority in authorities
    @Singular
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")})
    private Set<Authority> authorities;

    //adding other Spring's UserDetails interface properties
    @Builder.Default
    private Boolean enabled = true;

    @Builder.Default
    private Boolean accountNonExpired = true;

    @Builder.Default
    private Boolean accountNonLocked = true;

    @Builder.Default
    private Boolean credentialsNonExpired = true;
}