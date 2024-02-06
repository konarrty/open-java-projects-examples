package com.example.constructionmanagementspring.dto.exceptionDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiException {
    private String message;
    private String debugMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> exceptions;

    public ApiException(String message, String debugMessage) {
        this.message = message;
        this.debugMessage = debugMessage;
    }
}