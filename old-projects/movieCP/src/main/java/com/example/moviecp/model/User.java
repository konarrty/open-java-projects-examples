package com.example.moviecp.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@JsonIgnoreProperties(value = {"enabled","accountNonExpired","accountNonLocked","credentialsNonExpired"})
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.MERGE)
    private Balance balance;

    @JsonIgnore
    @OneToMany(mappedBy = "viewer", cascade = CascadeType.ALL)
    private Set<MovieReview> movieReviews;

    @Transient
    @JsonIgnore
    @OneToOne(mappedBy = "viewer", cascade = CascadeType.ALL)
    private Check check;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return roles.stream().map(r ->
                new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Balance balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
