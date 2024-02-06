package com.example.constructionmanagementspring.service;

import com.example.constructionmanagementspring.model.TenderNote;

import java.security.Principal;
import java.util.List;

public interface TenderNoteService {
    List<TenderNote> getAllPageableTenderNotes(int page);

    List<TenderNote> getAllTenderNotes();

    TenderNote getTenderNoteById(Long id);

    TenderNote createTenderNote(TenderNote tenderNote);

    TenderNote submitNote(TenderNote tenderNote, Principal principal);

    TenderNote putTenderNote(TenderNote newTenderNote, Long tenderNoteId);

    void deleteTenderNote(Long id);

    TenderNote acceptNote(Long tenderNoteId);

    List<TenderNote> getMyTenderNotes(Principal principal);
}
