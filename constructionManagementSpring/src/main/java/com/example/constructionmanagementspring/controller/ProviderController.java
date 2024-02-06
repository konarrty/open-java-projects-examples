package com.example.constructionmanagementspring.controller;

import com.example.constructionmanagementspring.config.annotation.BodyToEntity;
import com.example.constructionmanagementspring.dto.ProviderDto;
import com.example.constructionmanagementspring.model.Provider;
import com.example.constructionmanagementspring.service.ProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/providers")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providersService;

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @GetMapping({"/", ""})
    public ResponseEntity<?> getAllProviders(@RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        List<Provider> providersList = providersService.getAllProviders(page);

        if (!providersList.isEmpty())
            return ResponseEntity.ok(providersList);
        else
            return ResponseEntity.notFound().build();
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/news")
    public double getNewProvidersForMonth() {

        return providersService.getNewProvidersCount();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/news-percent")
    public double getPercentNewProvidersForMonth() {

        return providersService.getPercentNewProvidersForMonth();
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping({"/", ""})
    public Provider createProviders(@Valid @BodyToEntity(ProviderDto.class) Provider provider) {

        return providersService.createProvider(provider);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Provider putProviders(@Valid @BodyToEntity(ProviderDto.class) Provider provider, @PathVariable Long id) {
        System.err.println(provider);

        return providersService.putProvider(provider, id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteProviders(@PathVariable Long id) {

        providersService.deleteProvider(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Provider getProvidersById(@PathVariable Long id) {

        return providersService.getProviderById(id);
    }
    @PreAuthorize("hasAnyRole('PROVIDER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    public Provider getMe(Principal principal) {

        return providersService.getMe(principal);
    }


}
