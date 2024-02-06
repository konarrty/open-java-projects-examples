package com.example.tourmanagement.repository.clients.operator;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GrowthStatisticRepository {

    @Query(value = """
              select (sum(td.price) OVER ( ) -
                    sum(td.price) OVER ( partition by extract(month from t.start_date_time) )) /
                   sum(td.price) OVER ( partition by extract(month from t.start_date_time) )
                       * 100
            from reservations r
                     join public.tours t on t.id = r.tour_id
                     join public.tour_details td on td.id = t.details_id
                     join public.tour_operators o on o.id = td.operator_id
            where o.id = :id
              and extract(month from t.start_date_time) between :month - 1 and :month
              and extract(year from t.start_date_time) = :year
            limit 1 """,
            nativeQuery = true)
    Double growthSumSalesByOperator(Long id, Integer year, Integer month);

    @Query(value = """
              select(count(r) OVER ( ) -
                      count(r) OVER ( partition by extract(month from t.start_date_time) )) /
                     count(r) OVER ( partition by extract(month from t.start_date_time) )
                         * 100
               from reservations r
                       join public.tours t on t.id = r.tour_id
                       join public.tour_details td on td.id = t.details_id
                       join public.tour_operators o on o.id = td.operator_id
            where o.id = :id
                and extract(month from t.start_date_time) between :month - 1 and :month
                and extract(year from t.start_date_time) = :year
              limit 1
                       """, nativeQuery = true)
    Long growthCountSuccessTripsByOperator(Long id, Integer year, Integer month);

}
