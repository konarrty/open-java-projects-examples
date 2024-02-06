package com.example.tourmanagement.service.impl.tour.addons;

import com.example.tourmanagement.dto.request.SpecialityRequest;
import com.example.tourmanagement.dto.response.SpecialityResponse;
import com.example.tourmanagement.mapper.tour.addons.SpecialitiesMapper;
import com.example.tourmanagement.model.entity.Speciality;
import com.example.tourmanagement.repository.tour.addons.SpecialityRepository;
import com.example.tourmanagement.service.tour.addons.SpecialitiesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecialitiesServiceImpl implements SpecialitiesService {

    private final SpecialityRepository specialityRepository;
    private final SpecialitiesMapper specialityMapper;

    @Override
    public Iterable<SpecialityResponse> getAllSpecialities(Long hotelId) {

        return specialityMapper.toDTO(
                hotelId == null
                        ? specialityRepository.findAll()
                        : specialityRepository.findAllByHotelId(hotelId)
        );
    }

    @Override
    public SpecialityResponse createSpeciality(SpecialityRequest specialityDTO) {
        Speciality speciality = specialityMapper.toEntity(specialityDTO);
        specialityRepository.save(speciality);

        return specialityMapper.toDTO(speciality);
    }


    @Override
    public SpecialityResponse patchSpecialities(SpecialityRequest newSpeciality, Long id) {
        Speciality newSpecialityEntity = specialityMapper.toEntity(newSpeciality);

        Speciality speciality = specialityRepository
                .findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("План поездки не найден!"));

        specialityMapper.partialUpdate(speciality, newSpecialityEntity);
        specialityRepository.save(speciality);

        return specialityMapper.toDTO(speciality);
    }

    @Override
    public void deleteSpeciality(Long id) {
        if (!specialityRepository.existsById(id))
            throw new NoSuchElementException("Тур не найден!");

        specialityRepository.deleteById(id);
    }

    @Override
    public Speciality getSpecialityById(Long id) {

        return specialityRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Тур не найден!"));
    }

}
