package com.example.testbyport.entity;

import com.example.testbyport.domain.Range;
import com.example.testbyport.utils.MonthUtils;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.Objects;

@ToString
@Getter
@Setter
@Entity
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "completion_date", nullable = false)
    private LocalDate completionDate;

    @Column(name = "completed", nullable = false)
    private Boolean completed;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Employee employee;

    public Range extractRangeByMonthNumber(Integer monthNumber) {

        Integer start = MonthUtils.getStartRelativeToMonth(startDate, monthNumber);
        Integer end = MonthUtils.getEndRelativeToMonth(endDate, monthNumber);

        boolean isExist = start != null && end != null;

        return isExist ? new Range(start, end) : null;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Task task = (Task) o;
        return getId() != null && Objects.equals(getId(), task.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
