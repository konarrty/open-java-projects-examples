package com.example.constructionmanagementspring.utils;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class ConstantUtils {

    private final LocalDateTime minDate = LocalDateTime.MIN;
    private final LocalDateTime maxDate = LocalDateTime.now();


}
