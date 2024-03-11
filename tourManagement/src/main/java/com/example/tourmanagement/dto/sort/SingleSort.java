package com.example.tourmanagement.dto.sort;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import static org.springframework.data.domain.Sort.Direction;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SingleSort {

    private String property;
    private Direction direction;

    public Sort toSpringSort() {

        return property != null && direction != null ? Sort.by(direction, property) : Sort.unsorted();
    }
}
