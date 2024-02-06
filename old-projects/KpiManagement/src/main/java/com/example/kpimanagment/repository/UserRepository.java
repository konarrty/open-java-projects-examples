package com.example.kpimanagment.repository;

import com.example.kpimanagment.model.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<User> findWithLockingById(Long id);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

}
