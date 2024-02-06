package com.example.testbyport.controller;

import com.example.testbyport.enums.Month;
import com.example.testbyport.mapper.MonthMapper;
import dto.MonthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/months")
@RequiredArgsConstructor
public class MonthController {

    private final MonthMapper monthMapper;

    @GetMapping
    public Iterable<MonthDTO> getAllMonths() {

        return monthMapper.toDto(Month.asList());
    }
}
