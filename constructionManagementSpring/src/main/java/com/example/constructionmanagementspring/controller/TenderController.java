package com.example.constructionmanagementspring.controller;

import com.example.constructionmanagementspring.config.annotation.BodyToEntity;
import com.example.constructionmanagementspring.dto.TenderDto;
import com.example.constructionmanagementspring.model.Tender;
import com.example.constructionmanagementspring.service.TenderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/tenders")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class TenderController {

    private final TenderService tendersService;

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','PROVIDER')")
    @GetMapping({"/", ""})
    public ResponseEntity<?> getAllTenders(@RequestParam("page") int page) {

        List<Tender> tendersList = tendersService.getAllTenders(page);

        if (!tendersList.isEmpty())
            return ResponseEntity.ok(tendersList);
        else
            return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping({"/", ""})
    public Tender createTenders(@Valid @BodyToEntity(TenderDto.class) Tender tender) {
        Optional<Tender> anyTender = tendersService.getAllTenders().stream().filter(t ->
                t.getAssets().getId().equals(tender.getAssets().getId())
                        && !t.isClosed()).findFirst();

        if (anyTender.isPresent())
            throw new IllegalArgumentException("Тендер уже обьявлен!");

        return tendersService.createTender(tender);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Tender putTenders(@Valid @BodyToEntity(TenderDto.class) Tender tender, @PathVariable Long id) {

        return tendersService.putTender(tender, id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteTenders(@PathVariable Long id) {

        tendersService.deleteTender(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Tender getTendersById(@PathVariable Long id) {

        return tendersService.getTenderById(id);
    }


}
