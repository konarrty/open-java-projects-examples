package com.example.testbyport.repostory;

import com.example.testbyport.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByEmployeeId(Long employeeId);

}
