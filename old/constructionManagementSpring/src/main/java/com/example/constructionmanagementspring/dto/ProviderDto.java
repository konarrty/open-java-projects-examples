package com.example.constructionmanagementspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderDto {

    private Long id;

    private String name;

    private String unp;

    private String legalAddress;

    private String phoneNumber;

    private UserDto user;

}
