package com.example.constructionmanagementspring.service.impl;

import com.example.constructionmanagementspring.dto.TenderDto;
import com.example.constructionmanagementspring.exception.NoSuchEntityException;
import com.example.constructionmanagementspring.model.Assets;
import com.example.constructionmanagementspring.model.Equipment;
import com.example.constructionmanagementspring.model.Tender;
import com.example.constructionmanagementspring.repository.TenderRepository;
import com.example.constructionmanagementspring.service.AssetsService;
import com.example.constructionmanagementspring.service.EquipmentService;
import com.example.constructionmanagementspring.service.TenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {

    private final TenderRepository tenderRepository;

    @Override
    public List<Tender> getAllTenders(int page) {

        return tenderRepository.findAll(
                        PageRequest.of(page - 1, 5))
                .getContent();
    }

    @Override
    public List<Tender> getAllTenders() {

        return tenderRepository.findAll();
    }

    @Override
    public Tender createTender(Tender tender) {

        return tenderRepository.save(tender);
    }

    @Override
    public Tender putTender(Tender newTender, Long tenderId) {
        Tender tender = tenderRepository
                .findById(tenderId)
                .orElseThrow(() ->
                        new NoSuchEntityException("Тендер не найден!"));

        newTender.setId(tender.getId());

        return tenderRepository.save(newTender);
    }

    @Override
    public void deleteTender(Long id) {
        if (!tenderRepository.existsById(id))
            throw new NoSuchEntityException("Тендер не найден!");

        tenderRepository.deleteById(id);
    }

    @Override
    public Tender getTenderById(Long id) {
        return tenderRepository
                .findById(id)
                .orElseThrow(() ->
                        new NoSuchEntityException("Тендер не найден!"));
    }

}
