package com.example.constructionmanagementspring.repository;

import com.example.constructionmanagementspring.model.CashOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CashOrderRepository extends JpaRepository<CashOrder, Long> {
    Iterable<CashOrder> findAllByDateTimeAfterAndDateTimeBefore(LocalDateTime after, LocalDateTime before);

}
