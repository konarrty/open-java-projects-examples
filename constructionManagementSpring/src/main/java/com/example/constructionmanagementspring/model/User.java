package com.example.constructionmanagementspring.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Builder.Default
    @Column(name = "photo_name")
    private String photoName = "default_avatar.jpg";

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "user")
    private Provider provider;

    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Employee employee;

    @CreatedDate
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return roles.stream().map(r ->
                new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(Long id, String username, String password, String email, String photoName, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.photoName = photoName;
        this.roles = roles;
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
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
