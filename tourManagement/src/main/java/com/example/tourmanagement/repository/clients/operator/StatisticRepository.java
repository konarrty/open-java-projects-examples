package com.example.tourmanagement.repository.clients.operator;

import com.example.tourmanagement.dto.stat.OperatorSalesReportDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface StatisticRepository {

    @Query(value = """
                    SELECT new com.example.tourmanagement.dto.stat.OperatorSalesReportDTO
            ( sum (r.tour.details.price), r.tour.details.restType )
                    from Reservation r 
             where r.tour.details.operator.id =:id and r.tour.details.restType != null
             group by r.tour.details.restType
             order by sum (r.tour.details.price) desc 
             limit 5
                            """)
    List<OperatorSalesReportDTO> generateSalesReportByTop5Tours(Long id);

    @Query(value = """
            select sum(r.tour.details.price) 
             from Reservation r 
             where r.tour.details.operator.id =:id and r.status = 2
                     """)
    Double sumSalesByOperator(Long id);


    @Query(value = """
            select count (distinct r.tour) 
             from Reservation r 
             where r.tour.details.operator.id =:id  
                     """)
    Long countSuccessTripsByOperator(Long id);

    @Query(value = """
            select count (h) 
             from Hotel h 
             where h.operator.id =:id  
                     """)
    Long countHotelsByOperator(Long id);

    @Query(value = """
            select coalesce(avg(m.value), 0)
             from TourMark m 
             where m.details.operator.id =:id  
                     """)
    Double averageMarkByOperator(Long id);

}
