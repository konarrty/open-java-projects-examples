package com.example.constructionmanagementspring.controller;

import com.example.constructionmanagementspring.config.annotation.BodyToEntity;
import com.example.constructionmanagementspring.dto.AccountEquipmentDto;
import com.example.constructionmanagementspring.model.AccountEquipment;
import com.example.constructionmanagementspring.service.AccountEquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
@RestController
@RequestMapping("api/accounts")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class AccountEquipmentController {

    private final AccountEquipmentService accountEquipmentService;

    @GetMapping({"/", ""})
    public ResponseEntity<?> getAllAccountEquipments(@RequestParam("page") int page) {

        List<AccountEquipment> accountAccountEquipmentsList = accountEquipmentService.getAllAccountEquipments(page);

        if (!accountAccountEquipmentsList.isEmpty())
            return ResponseEntity.ok(accountAccountEquipmentsList);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping({"/", ""})
    public ResponseEntity<?> createAccountEquipment(@Valid
                                                    @BodyToEntity(AccountEquipmentDto.class)
                                                            AccountEquipment accountEquipment) {
        System.err.println(accountEquipment);
        AccountEquipment account = accountEquipmentService
                .createAccountEquipment(accountEquipment);

        if (account == null)
            return ResponseEntity
                    .badRequest()
                    .body("Это оборудоввание уже вверено рабочему, сперва его необходимо сдать");
        else
            return ResponseEntity
                    .created(URI.create("api/accounts/" + account.getId()))
                    .body(account);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/relinquishment")
    public AccountEquipment handOverAccountEquipment(@Valid @BodyToEntity(AccountEquipmentDto.class) AccountEquipment accountEquipment) {

        return accountEquipmentService
                .handOverEquipment(accountEquipment);
    }


    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{accountEquipmentId}")
    public AccountEquipment putAccountEquipments(@PathVariable Long accountEquipmentId,
                                                 @Valid @BodyToEntity(AccountEquipmentDto.class)
                                                         AccountEquipment newAccountEquipment) {

        return accountEquipmentService.putAccountEquipment(newAccountEquipment, accountEquipmentId);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{accountEquipmentId}")
    public void deleteAccountEquipments(@PathVariable Long accountEquipmentId) {

        accountEquipmentService.deleteAccountEquipment(accountEquipmentId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{accountEquipmentId}")
    public AccountEquipment getAccountEquipmentsById(@PathVariable Long accountEquipmentId) {

        return accountEquipmentService.getAccountEquipmentById(accountEquipmentId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/account")
    public AccountEquipment entrustToWorker(
            @Valid @BodyToEntity(AccountEquipmentDto.class)
                    AccountEquipment accountEquipment) {

        return accountEquipmentService
                .createAccountEquipment(accountEquipment);

    }

}
