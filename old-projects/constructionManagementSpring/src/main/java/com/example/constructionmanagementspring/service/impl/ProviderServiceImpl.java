package com.example.constructionmanagementspring.service.impl;

import com.example.constructionmanagementspring.exception.NoSuchEntityException;
import com.example.constructionmanagementspring.model.Provider;
import com.example.constructionmanagementspring.model.User;
import com.example.constructionmanagementspring.repository.ProviderRepository;
import com.example.constructionmanagementspring.service.ProviderService;
import com.example.constructionmanagementspring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;

    private final UserService userService;


    @Override
    public int getNewProvidersCount() {

        return providerRepository.findAll().size();
    }

    @Override
    public double getPercentNewProvidersForMonth() {

        return (double) providerRepository.findAll().stream()
                .filter(provider ->
                        provider.getUser().getCreateDate()
                                .isAfter(LocalDateTime.now().minusMonths(1L)))
                .count() / getNewProvidersCount();
    }

    @Override
    public Provider getMe(Principal principal) {
        User user = userService.findByUsername(principal.getName());

        return user.getProvider();
    }

    @Override
    public List<Provider> getAllProviders() {

        return providerRepository.findAll();
    }

    @Override
    public List<Provider> getAllProviders(int page) {

        return providerRepository.findAll(
                        PageRequest.of(page - 1, 5))
                .getContent();
    }

    @Override
    public Provider createProvider(Provider provider) {
        provider.getUser().setPhotoName("default_avatar.jpg");

        return providerRepository.save(provider);
    }

    @Override
    public Provider putProvider(Provider newProvider, Long providerId) {

        Provider provider = providerRepository
                .findById(providerId)
                .orElseThrow(() ->
                        new NoSuchEntityException("Поставщик не найден!"));

        newProvider.setId(provider.getId());

        return providerRepository.save(newProvider);
    }

    @Override
    public void deleteProvider(Long id) {
        if (!providerRepository.existsById(id))
            throw new NoSuchEntityException("Поставщик не найден!");

        providerRepository.deleteById(id);
    }


    @Override
    public Provider getProviderById(Long id) {

        return providerRepository
                .findById(id)
                .orElseThrow(() ->
                        new NoSuchEntityException("Поставщик не найден!"));
    }

}
