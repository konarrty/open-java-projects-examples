package com.example.kpimanagment.model;

import com.example.kpimanagment.enums.ERole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "employee", "privileges"})
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "invitation_сode")
    private String invitationCode;

    @NotNull
    @Enumerated
    @Column(name = "role")
    private ERole role;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Privilege> privileges;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "user")
    private Employee employee;

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(String username, String password, String invitationCode, @NotNull ERole role) {
        this.username = username;
        this.password = password;
        this.invitationCode = invitationCode;
        this.role = role;
    }

    //    public void addPrivileges(Privilege privilege) {
//
//        privileges.add(privilege);
//        privilege.setUser(this);
//    }
//
//    public void removePrivileges(Privilege privilege) {
//
//        privileges.remove(privilege);
//        privilege.setUser(null);
//    }
}
