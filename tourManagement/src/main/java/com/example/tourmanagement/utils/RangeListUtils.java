package com.example.tourmanagement.utils;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class RangeListUtils {
    public static List<Integer> fromRange(int start, int end) {

        return range(start, end).boxed().collect(toList());
    }
}
