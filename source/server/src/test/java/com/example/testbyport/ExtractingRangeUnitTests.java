package com.example.testbyport;

import com.example.testbyport.domain.Range;
import com.example.testbyport.entity.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class ExtractingRangeUnitTests {

    @Test
    void extractingRangeByCorrectMonth() {

        Task task = new Task();
        task.setName("test1");
        task.setStartDate(LocalDate.of(2023, 1, 16));
        task.setEndDate(LocalDate.of(2023, 3, 1));

        Range range = task.extractRangeByMonthNumber(1);

        Assertions.assertNotNull(range);
        Assertions.assertEquals(range.getStart(), 17);
        Assertions.assertEquals(range.getEnd(), 31);
    }

    @Test
    void cannotExtractRangeByGreaterMonth() {

        Task task = new Task();
        task.setName("test2");
        task.setStartDate(LocalDate.of(2023, 5, 16));
        task.setEndDate(LocalDate.of(2023, 8, 25));

        Range range = task.extractRangeByMonthNumber(11);

        Assertions.assertNull(range);
    }
}
