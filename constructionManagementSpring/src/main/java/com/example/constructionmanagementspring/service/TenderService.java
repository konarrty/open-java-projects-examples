package com.example.constructionmanagementspring.service;

import com.example.constructionmanagementspring.model.Tender;

import java.util.List;

public interface TenderService {
    List<Tender> getAllTenders(int page);

    List<Tender> getAllTenders();


    Tender getTenderById(Long id);

    Tender createTender(Tender tender);

    Tender putTender(Tender tender, Long tenderNotesId);

    void deleteTender(Long id);

}
