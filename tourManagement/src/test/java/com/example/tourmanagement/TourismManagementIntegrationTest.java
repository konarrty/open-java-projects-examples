package com.example.tourmanagement;

import com.example.tourmanagement.enums.ReservationStatus;
import com.example.tourmanagement.model.entity.TourMark;
import com.example.tourmanagement.repository.TourMarkRepository;
import com.example.tourmanagement.repository.clients.operator.TourOperatorRepository;
import com.example.tourmanagement.repository.hotel.HotelRepository;
import com.example.tourmanagement.repository.regression.ReservationRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;


@DataJpaTest
@Sql(scripts = {"/sql/add-test-data.sql"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
public class TourismManagementIntegrationTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    public TourOperatorRepository operatorRepository;

    @Autowired
    private TourMarkRepository markRepository;

    @Test
    public void countSuccessTripsByOperator() {

        Long operatorId = 1L;

        Long amount = reservationRepository.findAll()
                .stream()
                .filter(r -> r.getTour().getDetails().getOperator().getId().equals(operatorId))
                .count();

        Assertions.assertEquals(amount,
                operatorRepository.countSuccessTripsByOperator(1L));
    }

    @Test
    public void countHotelsByOperator() {

        Long operatorId = 1L;

        Long amount = hotelRepository.findAll()
                .stream()
                .filter(h -> h.getOperator().getId().equals(operatorId))
                .count();

        Assertions.assertEquals(amount,
                operatorRepository.countHotelsByOperator(operatorId));
    }

    @Test
    public void sumSalesByOperator() {

        Long operatorId = 1L;

        Double sales = reservationRepository.findAll().stream()
                .filter(r -> r.getStatus().equals(ReservationStatus.PAID))
                .mapToDouble(t -> t.getTour().getDetails().getPrice())
                .sum();

        Assertions.assertEquals(sales,
                operatorRepository.sumSalesByOperator(operatorId));
    }

    @Test
    public void averageMarkByOperator() {

        Long operatorId = 1L;

        double average = markRepository.findAll()
                .stream()
                .filter(m -> m.getDetails().getOperator().getId().equals(operatorId))
                .mapToDouble(TourMark::getValue)
                .average()
                .orElse(0);

        Assertions.assertEquals(average,
                operatorRepository.averageMarkByOperator(operatorId));
    }
}