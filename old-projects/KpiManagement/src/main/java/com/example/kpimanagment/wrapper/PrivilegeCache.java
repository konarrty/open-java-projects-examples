package com.example.kpimanagment.wrapper;

import com.example.kpimanagment.model.Privilege;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrivilegeCache {

    private Long id;

    private Privilege privilege;


}
