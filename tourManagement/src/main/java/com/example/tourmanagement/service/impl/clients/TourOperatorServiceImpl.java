package com.example.tourmanagement.service.impl.clients;

import com.example.tourmanagement.dto.TourOperatorDTO;
import com.example.tourmanagement.dto.registration.OperatorRegistrationDTO;
import com.example.tourmanagement.dto.stat.OperatorSalesReportResponse;
import com.example.tourmanagement.enums.ERole;
import com.example.tourmanagement.mapper.clients.TourOperatorMapper;
import com.example.tourmanagement.model.entity.TourOperator;
import com.example.tourmanagement.repository.clients.operator.TourOperatorRepository;
import com.example.tourmanagement.service.clients.TourOperatorService;
import com.example.tourmanagement.utils.ClientContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TourOperatorServiceImpl implements TourOperatorService {

    private final TourOperatorRepository tourOperatorRepository;

    private final TourOperatorMapper operatorMapper;

    @Override
    public Iterable<TourOperatorDTO> getAllTourOperators() {
        List<TourOperator> operators = tourOperatorRepository.findAll();

        return operatorMapper.toDTO(operators);
    }

    @Override
    public TourOperatorDTO createTourOperator(TourOperatorDTO tourOperator) {
        TourOperator operator = operatorMapper.toEntity(tourOperator);
        tourOperatorRepository.save(operator);

        return operatorMapper.toDTO(operator);
    }

    @Override
    public TourOperatorDTO registerOperator(OperatorRegistrationDTO operatorDto) {

        TourOperator operator = operatorMapper.toEntity(operatorDto);
        operator.getUser().setRole(ERole.ROLE_TOUR_OPERATOR);
        tourOperatorRepository.save(operator);

        return operatorMapper.toDTO(operator);
    }

    @Override
    public TourOperatorDTO getMeOperator() {
        return operatorMapper.toDTO(
                ClientContextUtils.getOperator()
        );
    }


    @Override
    public TourOperatorDTO editProfile(TourOperatorDTO newTourOperator) {
        TourOperator operator = tourOperatorRepository
                .findById(ClientContextUtils.getOperator().getId())
                .orElseThrow(() ->
                        new NoSuchElementException("Оператор не найден!"));

        operatorMapper.partialUpdate(operator, newTourOperator);
        tourOperatorRepository.save(operator);

        return operatorMapper.toDTO(operator);
    }

    @Override
    public void deleteTourOperator(Long id) {
        if (!tourOperatorRepository.existsById(id))
            throw new NoSuchElementException("Оператор не найден!");

        tourOperatorRepository.deleteById(id);
    }

    @Override
    public TourOperator getTourOperatorById(Long id) {

        return tourOperatorRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Оператор не найден!"));
    }

    @Override
    public OperatorSalesReportResponse generateSalesReportByTop5Tours() {

        var result = tourOperatorRepository.generateSalesReportByTop5Tours(
                ClientContextUtils.getOperator().getId()
        );
        var response = new OperatorSalesReportResponse();
        result.forEach(d -> response.add(d.getVolume(), d.getName().getName()));

        return response;
    }

    @Override
    public Double sumSalesByOperator() {
        Long clientId = ClientContextUtils.getOperator().getId();

        return tourOperatorRepository.sumSalesByOperator(clientId);
    }

    @Override
    public Long countSuccessTripsByOperator() {
        Long clientId = ClientContextUtils.getOperator().getId();

        return tourOperatorRepository.countSuccessTripsByOperator(clientId);
    }

    @Override
    public Long countHotelsByOperator() {
        Long clientId = ClientContextUtils.getOperator().getId();

        return tourOperatorRepository.countHotelsByOperator(clientId);
    }

    @Override
    public Double averageMarkByOperator() {
        Long clientId = ClientContextUtils.getOperator().getId();

        return tourOperatorRepository.averageMarkByOperator(clientId);
    }


}
