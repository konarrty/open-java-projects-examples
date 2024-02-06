package com.example.constructionmanagementspring.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id","user","assets"})
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString
@Table(name = "providers")
@Entity
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "unp")
    private String unp;

    @Column(name = "legal_address")
    private String legalAddress;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "provider")
    private List<Assets> assets;

}
