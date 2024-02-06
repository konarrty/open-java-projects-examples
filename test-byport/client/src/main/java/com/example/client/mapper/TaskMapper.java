package com.example.client.mapper;

import dto.TaskByMonthDTO;
import dto.TaskDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Vector;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    public Vector<Vector<Object>> taskToVectorOfVectors(Collection<TaskDTO> tasks) {

        return tasks.stream()
                .map(this::taskToVector)
                .collect(Collectors.toCollection(Vector::new));
    }

    public Vector<Vector<Object>> taskByMonthToVectorOfVectors(Collection<TaskByMonthDTO> tasks) {

        return tasks.stream()
                .map(this::taskByMonthToVector)
                .collect(Collectors.toCollection(Vector::new));
    }

    public Vector<Object> taskToVector(TaskDTO task) {

        Vector<Object> vector = new Vector<>();
        vector.add(task.completed());
        vector.add(task.name());
        vector.add(task.startDate());
        vector.add(task.endDate());
        vector.add(task.completionDate());

        return vector;
    }

    public Vector<Object> taskByMonthToVector(TaskByMonthDTO task) {

        Vector<Object> vector = new Vector<>();
        vector.add(task.name());

        return vector;
    }
}
