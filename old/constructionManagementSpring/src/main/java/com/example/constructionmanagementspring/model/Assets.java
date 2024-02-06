package com.example.constructionmanagementspring.model;

import com.example.constructionmanagementspring.enums.EquipmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@EntityListeners(AuditingEntityListener.class)
@Table(name = "assets")
@Entity
public class Assets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "coast")
    private double coast;

    @Column(name = "image_name")
    private String imageName;

    @Enumerated
    @Builder.Default
    @Column(name = "assets_status")
    private EquipmentStatus equipmentStatus = EquipmentStatus.PENDING;

    @CreatedDate
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToOne
    private Unit unit;

    @ManyToOne
    private Provider provider;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assets")
    private List<CashOrder> cashOrders;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assets")
    private List<Tender> tenders;
}
