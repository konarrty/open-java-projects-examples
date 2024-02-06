package com.example.tourmanagement.service.clients;

import com.example.tourmanagement.dto.ClientDTO;
import com.example.tourmanagement.dto.registration.ClientRegistrationDTO;

public interface ClientService {
    Iterable<ClientDTO> getAllClients();

    ClientDTO createClient(ClientDTO client);

    ClientDTO editProfile(ClientDTO newClient);

    void deleteClient(Long id);

    ClientDTO getMeClient();

    ClientDTO registrationClient(ClientRegistrationDTO clientDTO);

//    Client getClientById(Long id);
//

//    ClientDTO getClientByUsername(String username);
}
