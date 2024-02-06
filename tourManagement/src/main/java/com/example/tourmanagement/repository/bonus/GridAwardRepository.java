package com.example.tourmanagement.repository.bonus;

import com.example.tourmanagement.model.entity.GridAward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GridAwardRepository extends JpaRepository<GridAward, Long> {

    @Query(value = "SELECT id from grid_awards where factor = (select MAX(g.factor) " +
                   "FROM grid_awards g WHERE g.volume <= :volume )", nativeQuery = true)
    Long getMaxFactorByVolume(double volume);

    Iterable<GridAward> findAllByTourOperatorId(Long operatorId);

    @Query(value = """
                select
                SUM(CASE WHEN g.factor > :factor THEN 1 ELSE 0 END) =
                SUM(CASE WHEN g.volume > :volume THEN 1 ELSE 0 END)
            FROM
                GridAward g
                where g.tourOperator.id = :operatorId
             """)
    boolean isValidGridAward(Double factor, Long volume, Long operatorId);

    @Query(value = """
                select
                SUM(CASE WHEN g.factor > :factor THEN 1 ELSE 0 END) =
                SUM(CASE WHEN g.volume > :volume THEN 1 ELSE 0 END)
            FROM
                GridAward g
                where g.tourOperator.id = :operatorId 
                and g.id != :id 
             """)
    boolean isValidPatchGridAward(Double factor, Long volume, Long operatorId, Long id);
}
