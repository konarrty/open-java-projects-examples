package com.example.constructionmanagementspring.model;

import com.example.constructionmanagementspring.enums.OrderType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@EqualsAndHashCode(exclude = "id")
@EntityListeners(AuditingEntityListener.class)
@Table(name = "cash_order")
@Entity
public class CashOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private OrderType type;

    @CreatedDate
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "contract")
    private String contract;

    @Column(name = "ttn_number")
    private String ttnNumber;

    @ManyToOne
    private Assets assets;

}
