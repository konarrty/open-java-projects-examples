package com.example.kpimanagment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class PrivilegeRead extends Privilege {

    public PrivilegeRead() {
        super();
    }
}