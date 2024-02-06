package com.example.tourmanagement.service.impl;

import com.example.tourmanagement.dto.TourMarkRequest;
import com.example.tourmanagement.dto.response.TourMarkResponse;
import com.example.tourmanagement.mapper.TourMarkMapper;
import com.example.tourmanagement.model.entity.Client;
import com.example.tourmanagement.model.entity.TourDetails;
import com.example.tourmanagement.model.entity.TourMark;
import com.example.tourmanagement.repository.TourMarkRepository;
import com.example.tourmanagement.service.TourMarkService;
import com.example.tourmanagement.utils.ClientContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourMarkServiceImpl implements TourMarkService {

    private final TourMarkRepository markRepository;

    private final TourMarkMapper markMapper;

    @Override
    public Iterable<TourMarkResponse> getAllMarksByDetails(Long detailsId, Integer size) {

        Iterable<TourMark> marks;
        if (detailsId == null)
            marks = markRepository.findAll();
        else
            marks = markRepository.findAllByDetailsId(detailsId, PageRequest.ofSize(size));

        return markMapper.toResponse(marks);

    }

    @Override
    public TourMarkResponse createMark(TourMarkRequest markDTO) {

        Client client = ClientContextUtils.getClient();
        TourMark mark = markMapper.toEntity(markDTO);
        mark.setDetails(new TourDetails(mark.getDetails().getId()));
        mark.setClient(client);

        markRepository.save(mark);
        return markMapper.toResponse(mark);
    }

    @Override
    public TourMarkResponse patchMarks(TourMarkRequest newTourMark, Long markId) {
        TourMark tourMark = markRepository
                .findById(markId)
                .orElseThrow(() -> new NoSuchElementException("Оценка не найдена!"));

        markMapper.partialUpdate(tourMark, newTourMark);
        markRepository.save(tourMark);

        return markMapper.toResponse(tourMark);
    }

    @Override
    public void deleteMark(Long id) {

        markRepository.deleteById(id);
    }

    @Override
    public TourMark getMarkById(Long id) {
        return markRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Оценка не найденв!"));
    }

    @Override
    public Double calculateAverageMarkByDetailsId(Long detailsId) {
        return markRepository.calculateAverageMarkByDetailsId(detailsId);
    }

    @Override
    public TourMarkResponse getMyMarkByDetailsId(Long detailsId) {
        Long clientId = ClientContextUtils.getClient().getId();
        TourMark mark = markRepository.findAllByDetailsIdAndClientId(detailsId, clientId);

        return markMapper.toResponse(mark);
    }


}
