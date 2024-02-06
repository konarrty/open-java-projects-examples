package com.example.moviecp.repository;

import com.example.moviecp.model.Check;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckRepository extends JpaRepository<Check, Long> {
}
