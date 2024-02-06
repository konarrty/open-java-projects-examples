package com.example.tourmanagement.service.clients;


import com.example.tourmanagement.dto.AgentDTO;
import com.example.tourmanagement.dto.registration.AgentRegistrationDTO;
import com.example.tourmanagement.dto.response.ReservationResponse;

public interface AgentService {
    Iterable<AgentDTO> getAllAgents();

    AgentDTO createAgent(AgentDTO agent);

    AgentDTO editProfile(AgentDTO newAgent);

    void deleteAgent(Long id);

    AgentDTO getMeAgent();

    AgentDTO registrationAgent(AgentRegistrationDTO agentDTO);

    Iterable<ReservationResponse> getMyTours();
}
