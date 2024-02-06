package com.example.constructionmanagementspring.dto;

import lombok.Data;

@Data
public class TenderNoteDto {
    private Long id;

    private ProviderDto provider;

    private TenderDto tender;
}
