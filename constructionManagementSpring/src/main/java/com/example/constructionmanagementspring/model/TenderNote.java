package com.example.constructionmanagementspring.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Table(name = "tender_note")
@Entity
public class TenderNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Tender tender;

    @ManyToOne
    private Provider provider;
}
