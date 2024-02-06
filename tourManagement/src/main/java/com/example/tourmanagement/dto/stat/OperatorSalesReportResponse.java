package com.example.tourmanagement.dto.stat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperatorSalesReportResponse {

    private List<Double> volume = new ArrayList<>();
    private List<String> name = new ArrayList<>();

    public OperatorSalesReportResponse(int capacity) {
        volume = new ArrayList<>(capacity);
        name = new ArrayList<>(capacity);
    }

    public void add(double volume, String name) {

        this.volume.add(volume);
        this.name.add(name);
    }
}
