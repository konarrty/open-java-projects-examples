package com.example.tourmanagement.service.tour.addons;

import com.example.tourmanagement.dto.request.SpecialityRequest;
import com.example.tourmanagement.dto.response.SpecialityResponse;
import com.example.tourmanagement.model.entity.Speciality;

public interface SpecialitiesService {
    Iterable<SpecialityResponse> getAllSpecialities(Long hotelId);

    SpecialityResponse createSpeciality(SpecialityRequest specialityDTO);

    SpecialityResponse patchSpecialities(SpecialityRequest newSpeciality, Long id);

    void deleteSpeciality(Long id);

    Speciality getSpecialityById(Long id);

}
