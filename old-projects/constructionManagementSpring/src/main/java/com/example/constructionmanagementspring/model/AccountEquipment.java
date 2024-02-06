package com.example.constructionmanagementspring.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "id")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "account_equipment")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class AccountEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "create_date_time", nullable = false, updatable = false)
    private LocalDateTime createDateTime;

    @Column(name = "last_modified_date")
    private LocalDateTime lastActualModifiedDate;

    @Builder.Default
    @Column(name = "is_actual")
    private boolean isActual = true;

    @Column(name = "qr_name")
    private String qrName;

    @NonNull
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Equipment equipment;

    @NonNull
    @ManyToOne
    private Employee employee;
}
