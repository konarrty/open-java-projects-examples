package com.example.tourmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.tourmanagement.repository.tour.TourRepository.SQL_CALCULATE_PRICE_FORMULA;

@Getter
@Setter
@Entity
@Table(name = "tours")
@ToString(exclude = {"reservations"})
@AllArgsConstructor
@NoArgsConstructor
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Column(nullable = false)
    private Integer capacity;

    @Formula(SQL_CALCULATE_PRICE_FORMULA)
    private Double price;

    @Column(name = "last_time_tour")
    private Boolean lastTimeTour = false;

    @ManyToOne
    private TourDetails details;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tour", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    public Tour(Long id) {
        this.id = id;
    }

    public Tour(Boolean lastTimeTour) {
        this.lastTimeTour = lastTimeTour;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Tour tour = (Tour) o;
        return getId() != null && Objects.equals(getId(), tour.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
