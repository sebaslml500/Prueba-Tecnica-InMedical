package com.inmedical.JSCA.Repository;

import com.inmedical.JSCA.Entites.Restriccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RestriccionRepository extends JpaRepository<Restriccion, Long> {
    @Query("select r From Restriccion  r where  r.numberFinal = :lastDigit")
    List<Restriccion> findByLastDigit(int lastDigit);
}
