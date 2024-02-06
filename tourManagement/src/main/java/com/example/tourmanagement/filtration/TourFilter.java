package com.example.tourmanagement.filtration;

import com.example.tourmanagement.filtration.impl.TourFilterBetweenRequest;
import com.example.tourmanagement.filtration.impl.TourFilterEqualRequest;
import com.example.tourmanagement.filtration.impl.TourFilterRequest;
import com.example.tourmanagement.model.entity.Tour;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        defaultImpl = TourFilterRequest.class,
        property = "operation",
        visible = true)
@JsonSubTypes({
        @Type(value = TourFilterRequest.class),
        @Type(value = TourFilterEqualRequest.class, name = "equal"),
        @Type(value = TourFilterBetweenRequest.class, name = "between"),
})
public interface TourFilter extends Filter<Tour> {



}