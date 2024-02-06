package com.example.constructionmanagementspring.dto;

import lombok.Data;

@Data
public class MaterialDto {

    private Long id;

    private String type;

    private AssetsDto assets;

}
