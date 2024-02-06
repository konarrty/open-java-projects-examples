package com.example.tourmanagement.controller.clients;

import com.example.tourmanagement.dto.AgentDTO;
import com.example.tourmanagement.dto.registration.AgentRegistrationDTO;
import com.example.tourmanagement.dto.response.ReservationResponse;
import com.example.tourmanagement.service.clients.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/agents")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentsService;

    @GetMapping
    public ResponseEntity<?> getAllAgents() {

        Iterable<AgentDTO> agentsList = agentsService.getAllAgents();
        if (agentsList.iterator().hasNext())
            return ResponseEntity.ok(agentsList);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AgentDTO createAgents(@RequestBody AgentDTO agentDTO) {

        return agentsService.createAgent(agentDTO);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registration")
    public AgentDTO registrationAgent(@RequestBody AgentRegistrationDTO agentDTO) {

        return agentsService.registrationAgent(agentDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteAgents(@PathVariable Long id) {

        agentsService.deleteAgent(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public AgentDTO patchAgents(@RequestBody AgentDTO agentDTO) {

        return agentsService.editProfile(agentDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    public AgentDTO getMeAgent() {

        return agentsService.getMeAgent();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/my")
    public Iterable<ReservationResponse> getMyTours() {

        return agentsService.getMyTours();
    }

}
