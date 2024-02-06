package com.example.tourmanagement.repository.excursion;

import com.example.tourmanagement.model.entity.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcursionRepository extends JpaRepository<Excursion, Long> {

    Iterable<Excursion> findAllByDetailsId(Long detailsId);

}
