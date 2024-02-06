package com.example.tourmanagement.controller.tour.addons;

import com.example.tourmanagement.dto.request.SpecialityRequest;
import com.example.tourmanagement.dto.response.SpecialityResponse;
import com.example.tourmanagement.service.tour.addons.SpecialitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/specialities")
@RequiredArgsConstructor
public class SpecialityController {

    private final SpecialitiesService specialitiesService;


    @GetMapping
    public ResponseEntity<?> getAllSpecialities(@RequestParam(required = false) Long hotelId) {

        Iterable<SpecialityResponse> specialitiesList = specialitiesService.getAllSpecialities(hotelId);
        if (specialitiesList.iterator().hasNext())
            return ResponseEntity.ok(specialitiesList);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SpecialityResponse createSpecialities(@RequestBody SpecialityRequest specialityDTO) {

        return specialitiesService.createSpeciality(specialityDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteSpecialities(@PathVariable Long id) {

        specialitiesService.deleteSpeciality(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public SpecialityResponse patchSpecialities(@RequestBody SpecialityRequest specialityDTO, @PathVariable Long id) {

        return specialitiesService.patchSpecialities(specialityDTO, id);
    }

}
