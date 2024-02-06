package com.example.testbyport.utils;

import com.example.testbyport.domain.Range;
import com.example.testbyport.entity.Task;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class TaskWithRangeFormatterUtils {
    public static <T> Collection<T> formatTaskWithRange(Integer size, Function<Integer, T> function) {

        return IntStream.range(0, size)
                .mapToObj(function::apply)
                .toList();
    }

    public static Iterable<String> formatTaskWithRangeForLogging(List<Task> tasks, List<Range> ranges) {

        return formatTaskWithRange(tasks.size(),
                (i) -> "Задача " + tasks.get(i).getName() + " с периодом " + ranges.get(i));
    }
}
