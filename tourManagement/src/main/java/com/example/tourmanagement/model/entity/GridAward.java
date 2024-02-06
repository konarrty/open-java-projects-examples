package com.example.tourmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "grid_awards",
        uniqueConstraints = @UniqueConstraint(columnNames = {"volume", "factor", "tour_operator_id"}))
@AllArgsConstructor
@NoArgsConstructor
public class GridAward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long volume;

    private Double factor;

    @ManyToOne
    private TourOperator tourOperator;

    @OneToMany(mappedBy = "gridAward", cascade = CascadeType.REMOVE)
    private List<Bonus> bonuses;

    public GridAward(Long gridId) {
        this.id = gridId;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        GridAward gridAward = (GridAward) o;
        return getId() != null && Objects.equals(getId(), gridAward.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
