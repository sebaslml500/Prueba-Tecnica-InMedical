package com.inmedical.JSCA.Service;

import com.inmedical.JSCA.Entites.DTO.DayRestriccionDTO;
import com.inmedical.JSCA.Entites.DTO.RestriccionDTO;
import com.inmedical.JSCA.Entites.DayRestriccion;
import com.inmedical.JSCA.Entites.Restriccion;
import com.inmedical.JSCA.Repository.RestriccionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RestriccionService implements  IRestriccionService{

    @Autowired
    private RestriccionRepository restriccionRepository;
    @Transactional
    public Restriccion createRestriccion(RestriccionDTO restriccionDTO) {
        Restriccion restriccion = new Restriccion();
        Set<DayRestriccion> dayRestriccions = new HashSet<>();
        restriccion.setNumberFinal(restriccionDTO.getNumberFinal());
        for (DayRestriccionDTO dayRestriccionDTO : restriccionDTO.getDateDay()) {
            DayRestriccion dayRestriccion = new DayRestriccion();
            dayRestriccion.setHourStart(dayRestriccionDTO.getHourStart());
            dayRestriccion.setHourFinal(dayRestriccionDTO.getHourFinal());
            dayRestriccion.setDateDayRestriccion(dayRestriccionDTO.getDateDay());
            dayRestriccions.add(dayRestriccion);
        }
        restriccion.setDayRestriccions(dayRestriccions);
        return restriccionRepository.save(restriccion);
    }

    @Override
    public List<Restriccion> listRestriccion() {
        return this.restriccionRepository.findAll();
    }

    @Override
    public void saveRestriccion(Restriccion restriccion) {
        this.restriccionRepository.save(restriccion);
    }

    @Override
    public void deleteRestriccion(Long restriccionId) {
        Optional<Restriccion> restriccion = this.restriccionRepository.findById(restriccionId);
        if (restriccion.isPresent()) {
            this.restriccionRepository.delete(restriccion.get());
        } else {
            throw new EntityNotFoundException("No se encontró una restricción con el ID: " + restriccionId);
        }
    }
}
