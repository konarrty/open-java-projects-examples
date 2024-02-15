package com.example.tourmanagement.model.entity;

import com.example.tourmanagement.enums.NutritionType;
import com.example.tourmanagement.enums.RestType;
import com.example.tourmanagement.model.entity.base.IdHolder;
import com.example.tourmanagement.model.entity.base.ImagesSetter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.tourmanagement.repository.tour.TourDetailsRepository.SQL_CALCULATE_RATING_FORMULA;

@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "tour_details")
@AllArgsConstructor
@NoArgsConstructor
public class TourDetails implements IdHolder, ImagesSetter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private Double price;

    @Enumerated(value = EnumType.ORDINAL)
    private NutritionType nutritionType;

    @Enumerated(value = EnumType.ORDINAL)
    private RestType restType;

    @ManyToOne
    private TourOperator operator;

    @ManyToOne
    private Hotel hotel;

    @ToString.Exclude
    @OneToMany(mappedBy = "details", cascade = CascadeType.ALL)
    private List<Plan> plans = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "details", cascade = CascadeType.ALL)
    private List<Tour> tours = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "details", cascade = CascadeType.ALL)
    private List<Excursion> excursions = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "details", cascade = CascadeType.ALL)
    private List<TourMark> marks = new ArrayList<>();

    @Formula(SQL_CALCULATE_RATING_FORMULA)
    private Double rating;

    @Transient
    private List<String> images;

    public TourDetails(Long id) {
        this.id = id;
    }

    public TourDetails(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TourDetails(String name) {
        this.name = name;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        TourDetails details = (TourDetails) o;
        return getId() != null && Objects.equals(getId(), details.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
