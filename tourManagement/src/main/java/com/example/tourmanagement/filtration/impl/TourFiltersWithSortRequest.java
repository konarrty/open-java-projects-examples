package com.example.tourmanagement.filtration.impl;

import com.example.tourmanagement.filtration.TourFilter;
import com.example.tourmanagement.dto.sort.SingleSort;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourFiltersWithSortRequest {

    private List<TourFilter> filters;
    private SingleSort sort;

}
