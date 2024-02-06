package com.example.constructionmanagementspring.service;

import com.example.constructionmanagementspring.dto.AssetsDto;
import com.example.constructionmanagementspring.model.Assets;
import com.example.constructionmanagementspring.model.Provider;

import java.util.List;

public interface AssetsService {
    List<Assets> getAllAssets();

    Assets getAssetsById(Long id);

    Assets createAssets(AssetsDto assets);

    Assets putAssets(AssetsDto assets, Long assetsId);

    void deleteAssets(Long id);

    Assets addProviderToAssets(Long assetsid, Provider provider);


}
