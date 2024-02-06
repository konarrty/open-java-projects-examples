package com.example.constructionmanagementspring.controller;

import com.example.constructionmanagementspring.config.annotation.BodyToEntity;
import com.example.constructionmanagementspring.dto.TenderNoteDto;
import com.example.constructionmanagementspring.model.Tender;
import com.example.constructionmanagementspring.model.TenderNote;
import com.example.constructionmanagementspring.service.TenderNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/notes")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class TenderNoteController {

    private final TenderNoteService tenderNotesService;

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','PROVIDER')")
    @GetMapping({"/", ""})
    public ResponseEntity<?> getAllTenderNotes(@RequestParam("page") int page) {

        List<TenderNote> tenderNotesList = tenderNotesService.getAllPageableTenderNotes(page);

        if (!tenderNotesList.isEmpty())
            return ResponseEntity.ok(tenderNotesList);
        else
            return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('PROVIDER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/my")
    public List<TenderNote> getMyTenderNotes(Principal principal) {

       return tenderNotesService.getMyTenderNotes(principal);
    }

    @PreAuthorize("hasAnyRole('PROVIDER')")
    @PostMapping({"/", ""})
    public ResponseEntity<?> createTenderNotes(@Valid @BodyToEntity(TenderNoteDto.class) TenderNote tenderNotes, Principal principal) {
        TenderNote tenderNote = tenderNotesService.submitNote(tenderNotes, principal);

        if (tenderNote != null)
            return ResponseEntity.ok(tenderNote);
        else
            return ResponseEntity.notFound().build();

    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public TenderNote putTenderNotes(@Valid @BodyToEntity(TenderNoteDto.class) TenderNote tenderNote, @PathVariable Long id) {

        return tenderNotesService.putTenderNote(tenderNote, id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteTenderNotes(@PathVariable Long id) {

        tenderNotesService.deleteTenderNote(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TenderNote getTenderNotesById(@PathVariable Long id) {

        return tenderNotesService.getTenderNoteById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PostMapping("/{id}/contract")
    public ResponseEntity<?> acceptNote(@PathVariable Long id) {
        TenderNote tenderNote = tenderNotesService.acceptNote(id);

        if (tenderNote != null)
            return ResponseEntity.ok(tenderNote);
        else
            return ResponseEntity
                    .badRequest()
                    .body("Заявка уже одобрена");
    }

}
