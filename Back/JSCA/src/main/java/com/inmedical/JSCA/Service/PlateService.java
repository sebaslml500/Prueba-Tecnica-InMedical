package com.inmedical.JSCA.Service;

import com.inmedical.JSCA.Entites.DTO.DayPlateDTO;
import com.inmedical.JSCA.Entites.DTO.PlateDTO;
import com.inmedical.JSCA.Entites.DayPlate;
import com.inmedical.JSCA.Entites.DayRestriccion;
import com.inmedical.JSCA.Entites.Plate;
import com.inmedical.JSCA.Entites.Restriccion;
import com.inmedical.JSCA.Repository.PlateRepository;
import com.inmedical.JSCA.Repository.RestriccionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class PlateService implements IPlateService {
    @Autowired
    private PlateRepository plateRepository;

    @Autowired
    RestriccionRepository restriccionRepository;
    @Override
    public List<Plate> listPlate() {
        return plateRepository.findAll();
    }

    public Plate getPlateByNumber(String number) {
        Plate plate = plateRepository.getNumberPlate(number);
        String numberPlate = plate.getNumber();
        if(numberPlate != null){
            return plate;
        }else {
            return null;
        }
    }


    @Transactional
    public Plate createPlate(PlateDTO plateDTO){
        if (!isValidPlateNumber(plateDTO.getNumber())) {
            throw new IllegalArgumentException("Número de placa inválido. Debe tener 3 letras seguidas de 3 números.");
        }
        Plate  plate = new Plate();
        plate.setNumber(plateDTO.getNumber());
        Set<DayPlate> dayPlates = new HashSet<>();
        for(DayPlateDTO dayPlateDTO : plateDTO.getDays()){
            DayPlate dayPlate = new DayPlate();
            dayPlate.setHourStart(dayPlateDTO.getHourStart());
            dayPlate.setHourFinal(dayPlateDTO.getHourFinal());
            dayPlate.setDateDay(dayPlateDTO.getDateDay());
            dayPlates.add(dayPlate);
        }
        plate.setDayPlates(dayPlates);
        return plateRepository.save(plate);
    }


    private boolean isValidPlateNumber(String plateNumber) {
        return plateNumber != null && plateNumber.matches("[A-Za-z]{3}[0-9]{3}");
    }




    @Override
    public void savePlate(Plate plate) {
        this.plateRepository.save(plate);

    }

    public boolean plateExists(String plateNumber) {
        Plate plate = plateRepository.getNumberPlate(plateNumber);
        if(plate != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void deletePlate(String plateNumber) {
        Plate plate = this.plateRepository.getNumberPlate(plateNumber);
        if (plate != null) {
            this.plateRepository.delete(plate);
        } else {
            throw new EntityNotFoundException("No se encontró una placa con el número: " + plateNumber);
        }

    }
    public Map<LocalDate, String> canCirculate(String plateNumber) {
        if (!plateExists(plateNumber)) {
            throw new EntityNotFoundException("Placa no encontrada con número: " + plateNumber);
        }

        Plate plate = getPlateByNumber(plateNumber);

        int lastDigit = Character.getNumericValue(plateNumber.charAt(plateNumber.length() - 1));
        List<Restriccion> restricciones = restriccionRepository.findAll();

        Map<LocalDate, String> circulationStatus = new HashMap<>();

        for (DayPlate dayPlate : plate.getDayPlates()) {
            LocalDate date = dayPlate.getDateDay();
            LocalTime startTime = dayPlate.getHourStart();
            LocalTime endTime = dayPlate.getHourFinal();

            boolean canCirculate = true;

            for (Restriccion restriccion : restricciones) {
                if (restriccion.getNumberFinal() == lastDigit) {
                    for (DayRestriccion dayRestriccion : restriccion.getDayRestriccions()) {
                        if (dayRestriccion.getDateDayRestriccion().equals(date)) {
                            LocalTime restriccionStart = dayRestriccion.getHourStart();
                            LocalTime restriccionEnd = dayRestriccion.getHourFinal();

                            if (!(endTime.isBefore(restriccionStart) || startTime.isAfter(restriccionEnd))) {
                                canCirculate = false;
                                break;
                            }
                        }
                    }
                    if (!canCirculate) break;
                }
            }

            circulationStatus.put(date, canCirculate ?
                    "Puede circular el " + date :
                    "No puede circular el " + date);
        }

        return circulationStatus;
    }

    @Transactional
    public Plate addDayPlateToPlate(String plateNumber, DayPlateDTO dayPlateDTO) {
        Plate plate = plateRepository.getNumberPlate(plateNumber);
        if (plate == null) {
            throw new EntityNotFoundException("No se encontró una placa con el número: " + plateNumber);
        }

        DayPlate newDayPlate = new DayPlate();
        newDayPlate.setHourStart(dayPlateDTO.getHourStart());
        newDayPlate.setHourFinal(dayPlateDTO.getHourFinal());
        newDayPlate.setDateDay(dayPlateDTO.getDateDay());

        plate.getDayPlates().add(newDayPlate);
        return plateRepository.save(plate);
    }
}
