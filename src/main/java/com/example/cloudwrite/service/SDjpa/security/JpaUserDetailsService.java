package com.example.cloudwrite.service.SDjpa.security;


import com.example.cloudwrite.JPARepository.UserRepo;
import com.example.cloudwrite.model.security.User;
import com.example.cloudwrite.model.security.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Profile("SDjpa")
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    // returns a Spring Security User (as opposed to the custom model.security.User) transferring the custom properties
    // to the Spring Security User properties
    // loadUserByUsername is treated as one transaction with convertToSpringAuthorities() (otherwise convertToSpringAuthorities()
    // wouldn't find authorities and the user cannot login), particularly when this class substitutes
    // SecurityConfiguration's configure() (note, any WebMvcTests are likely to fail when swapping services, since
    // JPA tests are not part of WebMvcTests)
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = new User();
        try {
            user = userRepo.findByUsername(username).orElse(null);
        } catch (UsernameNotFoundException exception){
            System.out.println("User name, " + username + ", not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getEnabled(), user.getAccountNonExpired(), user.getCredentialsNonExpired(),
                user.getAccountNonLocked(), convertToSpringAuthorities(user.getAuthorities()));
    }

    // returns a Spring security authorities Set from the custom User's authorities set
    // (see return value of loadUserByUsername())
    private Collection<? extends GrantedAuthority> convertToSpringAuthorities(Set<Authority> authorities) {
        if (authorities != null && authorities.size() > 0){
            return authorities.stream()
                    .map(Authority::getRole)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }
}
