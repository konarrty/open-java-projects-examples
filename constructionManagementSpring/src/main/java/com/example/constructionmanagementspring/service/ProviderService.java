package com.example.constructionmanagementspring.service;

import com.example.constructionmanagementspring.model.Provider;

import java.security.Principal;
import java.util.List;

public interface ProviderService {
    List<Provider> getAllProviders();

    List<Provider> getAllProviders(int page);

    Provider getProviderById(Long id);

    Provider createProvider(Provider providers);

    Provider putProvider(Provider providers, Long providersId);

    int getNewProvidersCount();

    void deleteProvider(Long id);

    double getPercentNewProvidersForMonth();

    Provider getMe(Principal principal);
}
