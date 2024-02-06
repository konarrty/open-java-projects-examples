package com.example.tourmanagement.controller.clients;

import com.example.tourmanagement.dto.ClientDTO;
import com.example.tourmanagement.dto.registration.ClientRegistrationDTO;
import com.example.tourmanagement.service.clients.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientsService;

    @GetMapping
    public ResponseEntity<?> getAllClients(@RequestParam(required = false, defaultValue = "0") Long detailsId) {

        Iterable<ClientDTO> clientsList = clientsService.getAllClients();
        if (clientsList.iterator().hasNext())
            return ResponseEntity.ok(clientsList);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("registration")
    public ClientDTO createClients(@RequestBody ClientDTO clientDTO) {

        return clientsService.createClient(clientDTO);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ClientDTO registrationClient(@RequestBody ClientRegistrationDTO clientDTO) {

        return clientsService.registrationClient(clientDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteClients(@PathVariable Long id) {

        clientsService.deleteClient(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public ClientDTO editProfile(@RequestBody ClientDTO clientDTO) {

        return clientsService.editProfile(clientDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    public ClientDTO getMeClient() {

        return clientsService.getMeClient();
    }


}
