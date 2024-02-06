package com.example.tourmanagement.service.impl.clients;

import com.example.tourmanagement.dto.ClientDTO;
import com.example.tourmanagement.dto.registration.ClientRegistrationDTO;
import com.example.tourmanagement.enums.ERole;
import com.example.tourmanagement.mapper.clients.ClientMapper;
import com.example.tourmanagement.model.entity.Client;
import com.example.tourmanagement.repository.clients.ClientRepository;
import com.example.tourmanagement.service.clients.ClientService;
import com.example.tourmanagement.utils.ClientContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public Iterable<ClientDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();

        return clientMapper.toDTO(clients);
    }

    @Override
    public ClientDTO createClient(ClientDTO dto) {

        Client client = clientMapper.toEntity(dto);
        clientRepository.save(client);

        return clientMapper.toDTO(client);
    }

    @Override
    public ClientDTO registrationClient(ClientRegistrationDTO clientDTO) {

        Client client = clientMapper.toEntity(clientDTO);
        client.getUser().setRole(ERole.ROLE_TOURIST);
        clientRepository.save(client);

        return clientMapper.toDTO(client);
    }

    @Override
    public ClientDTO editProfile(ClientDTO newClient) {
        Client client = clientRepository
                .findById(ClientContextUtils.getClient().getId())
                .orElseThrow(() ->
                        new NoSuchElementException("Клиент не найден!"));

        clientMapper.partialUpdate(client, newClient);
        clientRepository.save(client);

        return clientMapper.toDTO(client);
    }

    @Override
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id))
            throw new NoSuchElementException("Клиент не найден!");

        clientRepository.deleteById(id);
    }

    @Override
    public ClientDTO getMeClient() {

        return clientMapper.toDTO(
                ClientContextUtils.getClient()
        );
    }
}
