package com.example.constructionmanagementspring.dto;

import lombok.Data;

@Data
public class TenderDto {
    private Long id;

    private boolean isClosed;

    private AssetsDto assets;
}
