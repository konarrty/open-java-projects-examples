package com.example.constructionmanagementspring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "tenderNotes", "assets"})
@Table(name = "tenders")
@Entity
public class Tender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Assets assets;

    @Column(name = "is_closed")
    private boolean isClosed;

    @JsonIgnore
    @OneToMany(mappedBy = "tender", cascade = CascadeType.ALL)
    private List<TenderNote> tenderNotes;



}
