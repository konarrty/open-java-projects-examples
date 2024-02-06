package com.example.constructionmanagementspring.model;

import com.example.constructionmanagementspring.enums.EquipmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Table(name = "equipments")
@Entity
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "qr_image")
    private String qrImage;

    @OneToOne(cascade = CascadeType.ALL)
    private Assets assets;


    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL)
    private List<AccountEquipment> accountEquipments = new ArrayList<>();
}
