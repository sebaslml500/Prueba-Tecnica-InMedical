package com.inmedical.JSCA.Repository;

import com.inmedical.JSCA.Entites.Plate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlateRepository extends JpaRepository<Plate, Long> {
    @Query("select p from Plate p where p.number = :number")
    Plate getNumberPlate(@Param("number") String plateNumber);
}
