package com.example.constructionmanagementspring.service.impl;

import com.example.constructionmanagementspring.exception.NoSuchEntityException;
import com.example.constructionmanagementspring.model.*;
import com.example.constructionmanagementspring.repository.TenderNoteRepository;
import com.example.constructionmanagementspring.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenderNoteServiceImpl implements TenderNoteService {

    private final TenderNoteRepository tenderNoteRepository;

    private final ProviderService providerService;
    private final TenderService tenderService;
    private final AssetsService assetsService;
    private final UserService userService;

    @Override
    public List<TenderNote> getAllPageableTenderNotes(int page) {

        return tenderNoteRepository.findAll(
                        PageRequest.of(page - 1, 5))
                .getContent();
    }

    @Override
    public List<TenderNote> getAllTenderNotes() {

        return tenderNoteRepository.findAll();
    }


    @Override
    public TenderNote createTenderNote(TenderNote tenderNote) {

        return tenderNoteRepository.save(tenderNote);
    }

    @Override
    public TenderNote submitNote(TenderNote tenderNote, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Provider provider = user.getProvider();

        if (getAllTenderNotes().stream().anyMatch(tender ->
                tender.getTender().getId().equals(tenderNote.getTender().getId())
                        && tender.getProvider().getId().equals(provider.getId())))
            return null;

        tenderNote.setProvider(provider);
        System.err.println(user);

        return tenderNoteRepository.save(tenderNote);
    }

    @Override
    public TenderNote putTenderNote(TenderNote newTenderNote, Long tenderNoteId) {

        TenderNote tenderNote = tenderNoteRepository
                .findById(tenderNoteId)
                .orElseThrow(() ->
                        new NoSuchEntityException("Заявка на тендер не найдена!"));

        newTenderNote.setId(tenderNote.getId());

        return tenderNoteRepository.save(newTenderNote);
    }

    @Override
    public void deleteTenderNote(Long id) {
        if (!tenderNoteRepository.existsById(id))
            throw new NoSuchEntityException("Заявка на тендер не найдена!");

        tenderNoteRepository.deleteById(id);
    }

    @Override
    public TenderNote getTenderNoteById(Long id) {

        return tenderNoteRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Заявка на тендер не найдена!"));
    }

    @Override
    public TenderNote acceptNote(Long tenderNoteId) {

        TenderNote tenderNote = tenderNoteRepository
                .findById(tenderNoteId)
                .orElseThrow(() ->
                        new NoSuchEntityException("Заявка на тендер не найдена!"));

        if (tenderNote.getTender().isClosed())
            return null;

        Assets assets = assetsService.addProviderToAssets(tenderNote.getTender().getAssets().getId(), tenderNote.getProvider());

        tenderNote.getTender().setAssets(assets);
        tenderNote.getTender().setClosed(true);

        return tenderNoteRepository.save(tenderNote);

    }

    @Override
    public List<TenderNote> getMyTenderNotes(Principal principal) {
        User user = userService.findByUsername(principal.getName());

        return getAllTenderNotes().stream()
                .filter(tenderNote -> tenderNote.getProvider().getId().equals(user.getProvider().getId()))
                .collect(Collectors.toList());
    }
}
