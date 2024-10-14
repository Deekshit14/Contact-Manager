package com.scm.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity (name = "user")
@Table (name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    private String userId;

    @Column (name = "user_name", nullable = false)
    private String name;

    @Column (unique = true, nullable = false)
    private String email;

    @Column (nullable = false)
    @Getter (value = AccessLevel.NONE)      // Disabling lombok in creating getter for password
    private String password;

    @Column (columnDefinition = "TEXT")
    private String about;

    @Column (columnDefinition = "TEXT")
    private String profilePic;   // the pic will be displayed using the link

//    @Column (unique = true, nullable = false)
    @Column (unique = true)
    private String phoneNumber;

    // Information on verification
    @Getter (value = AccessLevel.NONE)  // Disabling lombok in creating getter for enabled
    private boolean enabled = true;

    private boolean emailVerified = false;

    private boolean phoneVerified = false;

    // SELF, GOOGLE, GITHUB  SIGNUP
    @Enumerated (value = EnumType.STRING)
    private Providers provider = Providers.SELF;        // Use Set<Providers> to store multiple values like both SELF and GOOGLE

    private String providerUserId;


    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();


    @ElementCollection (fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Steps which are done below
        // List of roles[USER, ADMIN]
        // Collection of SimpleGrantedAuthority [roles{USER, ADMIN}]
        Collection<SimpleGrantedAuthority> roles = roleList
                                        .stream()
                                        .map(role -> new SimpleGrantedAuthority(role))
                                        .collect(Collectors.toList());

        return roles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public String getPassword() {
        return this.password;
    }

}
