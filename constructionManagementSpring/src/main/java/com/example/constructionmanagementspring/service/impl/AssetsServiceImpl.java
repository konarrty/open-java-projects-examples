package com.example.constructionmanagementspring.service.impl;

import com.example.constructionmanagementspring.dto.AssetsDto;
import com.example.constructionmanagementspring.exception.NoSuchEntityException;
import com.example.constructionmanagementspring.model.*;
import com.example.constructionmanagementspring.repository.AssetsRepository;
import com.example.constructionmanagementspring.service.AssetsService;
import com.example.constructionmanagementspring.service.ProviderService;
import com.example.constructionmanagementspring.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetsServiceImpl implements AssetsService {

    private final AssetsRepository assetsRepository;

    private final UnitService unitService;
    private final ProviderService providerService;

    @Override
    public List<Assets> getAllAssets() {

        return assetsRepository.findAll();
    }

    @Override
    public Assets createAssets(AssetsDto assets) {

        return assetsRepository.save(dtoToEntity(
                assets));
    }

    @Override
    public Assets putAssets(AssetsDto newAssets, Long assetsId) {

        Assets assets = assetsRepository
                .findById(assetsId)
                .orElseThrow(() ->
                        new NoSuchEntityException("Актив не найден!"));

        newAssets.setId(assets.getId());

        return assetsRepository.save(
                dtoToEntity(newAssets));
    }

    @Override
    public void deleteAssets(Long id) {
        if (!assetsRepository.existsById(id))
            throw new NoSuchEntityException("Актив не найден!");

        assetsRepository.deleteById(id);
    }

    @Override
    public Assets getAssetsById(Long id) {

        return assetsRepository
                .findById(id)
                .orElseThrow(() ->
                        new NoSuchEntityException("Актив не найден!"));
    }

    @Override
    public Assets addProviderToAssets(Long assetsid, Provider provider) {
        Assets assets = assetsRepository.findById(assetsid)
                .orElseThrow(() ->
                        new NoSuchEntityException("Актив не найден!"));

        assets.setProvider(provider);

        return assetsRepository.save(assets);
    }

    public Assets dtoToEntity(AssetsDto assetsDto) {

        Unit unit = unitService.getUnitById(assetsDto.getUnit().getId());
        Provider provider = providerService.getProviderById(assetsDto.getProvider().getId());

        return Assets.builder()
                .id(assetsDto.getId())
                .name(assetsDto.getName())
                .provider(provider)
                .unit(unit)
                .build();
    }
}
