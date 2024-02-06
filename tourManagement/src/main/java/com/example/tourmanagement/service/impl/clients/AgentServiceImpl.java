package com.example.tourmanagement.service.impl.clients;

import com.example.tourmanagement.dto.AgentDTO;
import com.example.tourmanagement.dto.registration.AgentRegistrationDTO;
import com.example.tourmanagement.dto.response.ReservationResponse;
import com.example.tourmanagement.enums.ERole;
import com.example.tourmanagement.mapper.clients.AgentMapper;
import com.example.tourmanagement.mapper.tour.ReservationMapper;
import com.example.tourmanagement.model.entity.Agent;
import com.example.tourmanagement.model.entity.Reservation;
import com.example.tourmanagement.repository.clients.AgentRepository;
import com.example.tourmanagement.service.clients.AgentService;
import com.example.tourmanagement.service.TourMarkService;
import com.example.tourmanagement.utils.ClientContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final TourMarkService markService;

    private final AgentMapper agentMapper;
    private final ReservationMapper reservationMapper;

    @Override
    public Iterable<AgentDTO> getAllAgents() {
        Iterable<Agent> agents = agentRepository.findAllByOperator(
                ClientContextUtils.getOperator()
        );

        return agentMapper.toDto(agents);
    }

    @Override
    public AgentDTO createAgent(AgentDTO dto) {
        Agent agent = agentMapper.toEntity(dto);
        agentRepository.save(agent);

        return agentMapper.toDto(agent);
    }

    @Override
    public AgentDTO registrationAgent(AgentRegistrationDTO dto) {

        Agent agent = agentMapper.toEntity(dto);
        agent.setOperator(ClientContextUtils.getOperator());
        agent.getUser().setRole(ERole.ROLE_AGENT);

        agentRepository.save(agent);
        return agentMapper.toDto(agent);
    }

    @Override
    public Iterable<ReservationResponse> getMyTours() {

        Long agentId = ClientContextUtils.getAgent().getId();
        Iterable<Reservation> reservations = agentRepository.findAgentReservations(agentId);

        return reservationMapper.toResponse(reservations);
    }

    @Override
    public AgentDTO editProfile(AgentDTO newAgent) {
        Agent agent = agentRepository
                .findById(ClientContextUtils.getAgent().getId())
                .orElseThrow(() ->
                        new NoSuchElementException("Агент не найден!"));

        agentMapper.partialUpdate(agent, newAgent);
        agentRepository.save(agent);

        return agentMapper.toDto(agent);
    }

    @Override
    public void deleteAgent(Long id) {
        if (!agentRepository.existsById(id))
            throw new NoSuchElementException("Агент не найден!");

        agentRepository.deleteById(id);
    }

    @Override
    public AgentDTO getMeAgent() {

        return agentMapper.toDto(
                ClientContextUtils.getAgent()
        );
    }
}
